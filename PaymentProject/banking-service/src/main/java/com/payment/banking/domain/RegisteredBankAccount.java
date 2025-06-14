package com.payment.banking.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisteredBankAccount {
    @Getter
    private final String registeredBankId;
    @Getter
    private final String membershipId;
    @Getter
    private final String bankName;
    @Getter
    private final String bankAccountNumber;
    @Getter
    private final boolean linkedStatusIsValid;


    public static RegisteredBankAccount generateRegisteredBankAccount(
            RegisteredBankAccount.RegisteredBankAccountId registeredBankId, MembershipId membershipId
    , BankName bankName, BankAccountNumber bankAccountNumber, LinkedStatusIsValid linkedStatusIsValid)
    {
        return new RegisteredBankAccount(registeredBankId.registeredBankId, membershipId.membershipId,
                bankName.bankName, bankAccountNumber.bankAccountNumber, linkedStatusIsValid.linkedStatusIsValid
                );
    }


    @Value
    public static class RegisteredBankAccountId{
        public RegisteredBankAccountId(String value){
            this.registeredBankId = value;
        }
        String registeredBankId;
    }
    @Value
    public static class MembershipId{
        public MembershipId(String value){
            this.membershipId = value;
        }
        String membershipId;
    }
    @Value
    public static class BankName{
        public BankName(String value){
            this.bankName = value;
        }
        String bankName;
    }
    @Value
    public static class BankAccountNumber{
        public BankAccountNumber(String value){
            this.bankAccountNumber = value;
        }
        String bankAccountNumber;
    }
    @Value
    public static class LinkedStatusIsValid{
        public LinkedStatusIsValid(boolean value){
            this.linkedStatusIsValid = value;
        }
        boolean linkedStatusIsValid;
    }


}
