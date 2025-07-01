package com.payment.money.application.port.out;

import com.payment.common.RechargingMoneyTask;

public interface SendRecharingMoneyTaskPort {
    void sendRechargingMoneyTask(RechargingMoneyTask rechargingMoneyTask);
}
