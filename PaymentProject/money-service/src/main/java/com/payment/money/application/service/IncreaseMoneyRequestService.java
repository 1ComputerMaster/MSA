package com.payment.money.application.service;

import com.payment.common.CountDownLatchManager;
import com.payment.common.RechargingMoneyTask;
import com.payment.common.SubTask;
import com.payment.money.adapter.axon.command.MemberMoneyCreatedCommand;
import com.payment.money.adapter.out.persistence.MemberMoneyJpaEntity;
import com.payment.money.adapter.out.persistence.MoneyChangingRequestJpaEntity;
import com.payment.money.adapter.out.persistence.MoneyChangingRequestMapper;
import com.payment.money.application.port.in.*;
import com.payment.money.application.port.out.GetMembershipPort;
import com.payment.money.application.port.out.IncreaseMoneyPort;
import com.payment.money.application.port.out.MembershipStatus;
import com.payment.money.application.port.out.SendRecharingMoneyTaskPort;
import com.payment.money.domain.MemberMoney;
import com.payment.money.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Transactional
@RequiredArgsConstructor
@Service
public class IncreaseMoneyRequestService implements IncreaseMoneyRequestUsecase, CreateMemberMoneyUseCase {
    private final CountDownLatchManager countDownLatchManager;
    private final SendRecharingMoneyTaskPort sendRecharingMoneyTaskPort;
    private final MoneyChangingRequestMapper moneyChangingRequestMapper;
    private final IncreaseMoneyPort increaseMoneyPort;
    private final GetMembershipPort membershipPort;
    private final CommandGateway commandGateway;
    private final CreateMemberMoneyPort createMemberMoneyPort;
    @Override
    public MoneyChangingRequest increaseMoney(IncreaseMoneyChangingCommand command) {
        
        // 머니의 충전. 증액 과정

        //1. 고객의 정보가 정상인지 확인 (멤버 서비스)
        MembershipStatus membershipStatus = membershipPort.getMembership(command.getTargetMembershipId());

        //2. 고객에 연동된 계좌가 있는지, 그리고 그것이 정상적인지 확인, 고객의 연동된 계좌의 잔액이 충분한지도 확인 (뱅킹)
        if(!membershipStatus.isValid()) {
            throw new IllegalArgumentException("Invalid membership ID: " + command.getTargetMembershipId());
        }
        //3. 법인 계좌 상태도 정상인지 확인 (뱅킹)
        
        //4. 증액을 위한 "기록". 요청 상태로  MoneyChangingRequest 생성 (MoneyChangingRequest)

        // 5. 펌뱅킹을 수행하고 (고객 연동 계좌 -> 페이 법인 계좌) (뱅킹)

        // 6. 펌뱅킹이 성공하면, MoneyChangingRequest의 상태를 완료로 변경
        // 성공시의 멤버의 MemberMoney 값 증액이 필요하다.

        // 6-1. 펌뱅킹이 실패하면, MoneyChangingRequest의 상태를 실패로 변경

        MemberMoneyJpaEntity memberMoneyJpaEntity = increaseMoneyPort.increaseMoney(
                new MemberMoney.MembershipId(command.getTargetMembershipId()),
                command.getAmount()
        );

        if(!Objects.isNull(memberMoneyJpaEntity)){
            MoneyChangingRequestJpaEntity moneyChangingRequestJpaEntity = increaseMoneyPort.createMoneyChangingRequest(
                    new MoneyChangingRequest.TargetMembershipId(command.getTargetMembershipId()),
                    new MoneyChangingRequest.MoneyChangingType(0), // 증액 타입은 0로 고정
                    new MoneyChangingRequest.ChangingMoneyAmount(command.getAmount()),
                    new MoneyChangingRequest.MoneyChangingStatus(1), // 성공 상태로 고정
                    new MoneyChangingRequest.Uuid(UUID.randomUUID())
            );
            return  moneyChangingRequestMapper.mapToDomainEntity(moneyChangingRequestJpaEntity);
        }
        //TODO 실패 시 나중에 RequestEntity를 상태값을 적절히 처리하는 로직 필요
        return null;
    }

    @Override
    public MoneyChangingRequest increaseMoneyAsync(IncreaseMoneyChangingCommand request) {
        // 1. SubTask, Task -> 각 서비스에 특정 membershipId에 대한 작업을 요청 Validation임 대부분
        SubTask validMemberTask = SubTask.builder()
                .subTaskName("validMemberTask : " + ":맴버십 유효성 검사")
                .membershipID(request.getTargetMembershipId())
                .taskType("membership")
                .status("ready")
                .build();

        // Banking Sub task
        // Amount Firm Banking is ok
        SubTask validBankingAccountTask = SubTask.builder()
                .subTaskName("validBankingAccountTask : " + ":맴버십 유효성 검사")
                .membershipID(request.getTargetMembershipId())
                .taskType("banking")
                .status("ready")
                .build();


        List<SubTask> subTaskList = new ArrayList<>();
        subTaskList.add(validMemberTask);
        subTaskList.add(validBankingAccountTask);

        RechargingMoneyTask task = RechargingMoneyTask.builder()
                .taskID(UUID.randomUUID().toString())
                .taskName("Increas Money Task / increaseMoneyAsync")
                .subTaskList(subTaskList)
                .membershipID(request.getTargetMembershipId())
                .moneyAmount(request.getAmount())
                .toBankName("페이 법인 계좌") // TODO: 페이 법인 계좌는 (Enum)상수로 관리
                .build();

        // 2. Kafka Cluster Produce
        // Task Produce
        sendRecharingMoneyTaskPort.sendRechargingMoneyTask(task);
        countDownLatchManager.addCountDownLatch(task.getTaskID());

        // 3. Wait
        // TODO Redission 사용하여도 좋을 듯
        try {
            countDownLatchManager.getCountDownLatch(task.getTaskID())
                    .await(); // CountDownLatch를 사용하여 Task가 완료될 때까지 대기
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 3-1. task-consumer
        // 등록된 sub-task, status 모두 ok -> task 결과를 Produce

        // 4. Task Result Consume
        String result = countDownLatchManager.getDataForKey(task.getTaskID());
        // 5. Consume is Ok, then business logic is same as increaseMoney
        if("success".equals(result)){
            MemberMoneyJpaEntity memberMoneyJpaEntity = increaseMoneyPort.increaseMoney(
                    new MemberMoney.MembershipId(request.getTargetMembershipId()),
                    request.getAmount()
            );

            if(!Objects.isNull(memberMoneyJpaEntity)){
                MoneyChangingRequestJpaEntity moneyChangingRequestJpaEntity = increaseMoneyPort.createMoneyChangingRequest(
                        new MoneyChangingRequest.TargetMembershipId(request.getTargetMembershipId()),
                        new MoneyChangingRequest.MoneyChangingType(0), // 증액 타입은 0로 고정
                        new MoneyChangingRequest.ChangingMoneyAmount(request.getAmount()),
                        new MoneyChangingRequest.MoneyChangingStatus(1), // 성공 상태로 고정
                        new MoneyChangingRequest.Uuid(UUID.randomUUID())
                );
                return  moneyChangingRequestMapper.mapToDomainEntity(moneyChangingRequestJpaEntity);
            }
        }
        return null;
    }

    @Override
    public void increaseMoneyRequestByEvent(IncreaseMoneyChangingCommand command) {

    }

    @Override
    public void createMemberMoney(CreateMemberMoneyCommand command) {
        //맴버에 Axon Framework에 의존하는 Command를 만듦
        // @CommandHandler로 전달하면 Event Queue에서 관리되는 Axon 객체 값이 됨
        // 헨들링하는 생성자는 다시 Event를 만들고 실제 이벤ㄴ트를 소싱함
        // 그 이벤트로 부터 맴버 어그리게이트를 만듦
        MemberMoneyCreatedCommand axonCommand = new MemberMoneyCreatedCommand(command.getMembershipId());
        commandGateway.send(axonCommand)
            //이벤트 소싱이 끝날 때 까지 기다린 후 콜백 체인으로 처리
            .whenComplete((result, exception) -> {
                if (exception != null) {
                    // Handle exception
                    System.err.println("Command failed: " + exception.getMessage());
                } else {
                    // Handle success
                    System.out.println("Command succeeded: " + result);
                    createMemberMoneyPort.createMemberMoney(
                            new MemberMoney.MembershipId(command.getMembershipId()),
                            new MemberMoney.MoneyAggregateIdentifier(result.toString())
                    );
                }
            });
    }
}
