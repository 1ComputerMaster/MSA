package com.payment.money.application.port.in;

import com.payment.money.domain.MoneyChangingRequest;

public interface IncreaseMoneyRequestUsecase {
    /**
     * 충전 요청을 처리하는 메소드
     *
     * @param request 충전 요청 정보
     */
    MoneyChangingRequest increaseMoney(IncreaseMoneyChangingCommand request);
    MoneyChangingRequest increaseMoneyAsync(IncreaseMoneyChangingCommand request);

    void increaseMoneyRequestByEvent(IncreaseMoneyChangingCommand command);
}
