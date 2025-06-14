package com.payment.banking.application.port.in;

import com.payment.banking.domain.FirmbankingRequest;

public interface RequestFirmbankingUsecase {
    /**
     * 기업 뱅킹 요청을 처리하는 메소드
     *
     * @param requestFirmbankingCommand 기업 뱅킹 요청에 대한 커맨드 객체
     * @return 처리된 기업 뱅킹 요청 결과
     */
    FirmbankingRequest requestFirmbanking(FirmbankingRequestCommand requestFirmbankingCommand);
}
