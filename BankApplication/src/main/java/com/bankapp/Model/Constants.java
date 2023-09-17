package com.bankapp.Model;

public class Constants {
    public final static String SUCCESS = "SUCCESS";
    public final static String FAILED = "FAILED";
    public final static String INVALID_DEPOSIT = "Deposit Amount Should be Greater than Zero";
    public final static String INVALID_WITHDRAW = "Withdrawal Amount Should be Greater than Zero";
    public final static String LOW_BALANCE = "Account does not have sufficient balance to make this transaction";
    public final static String VALID_DEPOSIT = "Amount has been credited to your Account Successfully";
    public final static String VALID_WITHDRAW = "Transaction has been completed Successfully";
    public final static String CHECKING_INTEREST = "Interest Can be calculated only for Savings Account";
    public final static String SAVINGS_INTEREST = "Interest has been calculated Successfully";
    public final static String CREATED = "Account has been created Successfully";
    public final static String BALANCE = "Latest Balance details fetched Successfully";

    public static Response actionDeposit(BankAccount account,double amount){
        Response res = new Response();
        if(amount <= 0){
            res.setStatus(Constants.FAILED);
            res.setMessage(Constants.INVALID_DEPOSIT);
        } else {
            account.setBalance(account.getBalance() + amount);
            res.setStatus(Constants.SUCCESS);
            res.setUserName(account.getAccountHolder());
            res.setAccountNumber(account.getAccountNumber());
            res.setAccountType(account.getAccountType());
            res.setMessage(Constants.VALID_DEPOSIT);
            res.setAmount(account.getBalance());
        }
        return res;
    }

    public static Response actionWithdraw(BankAccount account, double amount){
        Response res = new Response();
        if(amount <= 0){
            res.setStatus(Constants.FAILED);
            res.setMessage(Constants.INVALID_WITHDRAW);
        } else {
            if(account.getBalance() >= amount){
                account.setBalance(account.getBalance() - amount);
                res.setStatus(Constants.SUCCESS);
                res.setMessage(Constants.VALID_WITHDRAW);
                res.setAmount(account.getBalance());
                res.setUserName(account.getAccountHolder());
                res.setAccountNumber(account.getAccountNumber());
                res.setAccountType(account.getAccountType());
            } else {
                res.setStatus(Constants.FAILED);
                res.setMessage(Constants.LOW_BALANCE);
                res.setAmount(account.getBalance());
            }

        }
        return res;
    }

    public static Response getInterest(BankAccount account){
        Response res = new Response();
        if(account.getAccountType().equals("Checking")){
            res.setStatus(Constants.FAILED);
            res.setMessage(Constants.CHECKING_INTEREST);
        } else {
            res.setStatus(Constants.SUCCESS);
            res.setMessage(Constants.SAVINGS_INTEREST);
            double balance = account.getBalance();
            res.setExistingBalance(account.getBalance());
            double rate = 0.03;
            int years = 1;
            double interest = (rate * years * balance);
            account.setBalance(account.getBalance() + interest);
            res.setNewBalance(account.getBalance());
        }
        res.setUserName(account.getAccountHolder());
        res.setAccountNumber(account.getAccountNumber());
        res.setAccountType(account.getAccountType());
        return res;
    }
}
