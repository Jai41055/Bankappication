package com.bankapp.Model;

public class SavingsAccount extends BankAccount {

    public SavingsAccount(long accountNumber, String accountHolder, double balance, String accountType) {
        super(accountNumber, accountHolder, balance, accountType);
    }

    @Override
    public Response deposit(double amount) throws Exception{
        return Constants.actionDeposit(this, amount);
    }

    @Override
    public Response withdraw(double amount) throws Exception{
        return Constants.actionWithdraw(this, amount);
    }

    @Override
    public Response calculateInterest() throws Exception{
        return Constants.getInterest(this);
    }
}
