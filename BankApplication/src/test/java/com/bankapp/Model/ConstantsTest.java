package com.bankapp.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConstantsTest {

    @Test
    void testDepositZeroChecking() throws Exception {
        CheckingAccount acc = new CheckingAccount(1234567890L, "Test Name",50000, "Checking");
        Response res = acc.deposit(0);
        assertEquals(res.getStatus(), "FAILED");
        assertEquals(res.getMessage(), "Deposit Amount Should be Greater than Zero");
    }

    @Test
    void testDepositNegativeChecking() throws Exception {
        CheckingAccount acc = new CheckingAccount(1234567890L, "Test Name",50000, "Checking");
        Response res = acc.deposit(-100);
        assertEquals(res.getStatus(), "FAILED");
        assertEquals(res.getMessage(), "Deposit Amount Should be Greater than Zero");
    }

    @Test
    void testDepositPositiveChecking() throws Exception {
        CheckingAccount acc = new CheckingAccount(1234567890L, "Test Name",50000, "Checking");
        Response res = acc.deposit(5000);
        assertEquals(res.getStatus(), "SUCCESS");
        assertEquals(res.getMessage(), "Amount has been credited to your Account Successfully");
        assertEquals(res.getUserName(), "Test Name");
        assertEquals(res.getAccountNumber(), 1234567890L);
        assertEquals(res.getAccountType(), "Checking");
        assertEquals(res.getAmount(), 55000.0);
    }

    @Test
    void testDepositZeroSavings() throws Exception {
        SavingsAccount acc = new SavingsAccount(1234567890L, "Test Name",50000, "Savings");
        Response res = acc.deposit(0);
        assertEquals(res.getStatus(), "FAILED");
        assertEquals(res.getMessage(), "Deposit Amount Should be Greater than Zero");
    }

    @Test
    void testDepositNegativeSavings() throws Exception {
        SavingsAccount acc = new SavingsAccount(1234567890L, "Test Name",50000, "Savings");
        Response res = acc.deposit(-100);
        assertEquals(res.getStatus(), "FAILED");
        assertEquals(res.getMessage(), "Deposit Amount Should be Greater than Zero");
    }

    @Test
    void testDepositPositiveSavings() throws Exception {
        SavingsAccount acc = new SavingsAccount(1234567890L, "Test Name",50000, "Savings");
        Response res = acc.deposit(5000);
        assertEquals(res.getStatus(), "SUCCESS");
        assertEquals(res.getMessage(), "Amount has been credited to your Account Successfully");
        assertEquals(res.getUserName(), "Test Name");
        assertEquals(res.getAccountNumber(), 1234567890L);
        assertEquals(res.getAccountType(), "Savings");
        assertEquals(res.getAmount(), 55000.0);
    }

    @Test
    void testWithdrawZeroChecking() throws Exception {
        CheckingAccount acc = new CheckingAccount(1234567890L, "Test Name",50000, "Checking");
        Response res = acc.withdraw(0);
        assertEquals(res.getStatus(), "FAILED");
        assertEquals(res.getMessage(), "Withdrawal Amount Should be Greater than Zero");
    }

    @Test
    void testWithDrawNegativeChecking() throws Exception {
        CheckingAccount acc = new CheckingAccount(1234567890L, "Test Name",50000, "Checking");
        Response res = acc.withdraw(-100);
        assertEquals(res.getStatus(), "FAILED");
        assertEquals(res.getMessage(), "Withdrawal Amount Should be Greater than Zero");
    }

    @Test
    void testWithdrawZeroSavings() throws Exception {
        SavingsAccount acc = new SavingsAccount(1234567890L, "Test Name",50000, "Savings");
        Response res = acc.withdraw(0);
        assertEquals(res.getStatus(), "FAILED");
        assertEquals(res.getMessage(), "Withdrawal Amount Should be Greater than Zero");
    }

    @Test
    void testWithDrawNegativeSavings() throws Exception {
        SavingsAccount acc = new SavingsAccount(1234567890L, "Test Name",50000, "Savings");
        Response res = acc.withdraw(-100);
        assertEquals(res.getStatus(), "FAILED");
        assertEquals(res.getMessage(), "Withdrawal Amount Should be Greater than Zero");
    }

    @Test
    void testWithDrawPositiveCheckingNotEnoughBalance() throws Exception {
        CheckingAccount acc = new CheckingAccount(1234567890L, "Test Name",5000, "Checking");
        Response res = acc.withdraw(6000);
        assertEquals(res.getStatus(), "FAILED");
        assertEquals(res.getMessage(), "Account does not have sufficient balance to make this transaction");
        assertEquals(res.getAmount(), 5000);
    }

    @Test
    void testWithDrawPositiveCheckingEnoughBalance() throws Exception {
        CheckingAccount acc = new CheckingAccount(1234567890L, "Test Name",5000, "Checking");
        Response res = acc.withdraw(2000);
        assertEquals(res.getStatus(), "SUCCESS");
        assertEquals(res.getMessage(), "Transaction has been completed Successfully");
        assertEquals(res.getAmount(), 3000);
        assertEquals(res.getUserName(), "Test Name");
        assertEquals(res.getAccountNumber(), 1234567890L);
        assertEquals(res.getAccountType(), "Checking");
    }

    @Test
    void testWithDrawPositiveSavingsNotEnoughBalance() throws Exception {
        SavingsAccount acc = new SavingsAccount(1234567890L, "Test Name",5000, "Savings");
        Response res = acc.withdraw(6000);
        assertEquals(res.getStatus(), "FAILED");
        assertEquals(res.getMessage(), "Account does not have sufficient balance to make this transaction");
        assertEquals(res.getAmount(), 5000);
    }

    @Test
    void testWithDrawPositiveSavingsEnoughBalance() throws Exception {
        SavingsAccount acc = new SavingsAccount(1234567890L, "Test Name",5000, "Savings");
        Response res = acc.withdraw(2000);
        assertEquals(res.getStatus(), "SUCCESS");
        assertEquals(res.getMessage(), "Transaction has been completed Successfully");
        assertEquals(res.getAmount(), 3000);
        assertEquals(res.getUserName(), "Test Name");
        assertEquals(res.getAccountNumber(), 1234567890L);
        assertEquals(res.getAccountType(), "Savings");
    }

    @Test
    void testInterestChecking() throws Exception {
        CheckingAccount acc = new CheckingAccount(1234567890L, "Test Name",5000, "Checking");
        Response res = acc.calculateInterest();
        assertEquals(res.getStatus(), "FAILED");
        assertEquals(res.getMessage(), "Interest Can be calculated only for Savings Account");
        assertEquals(res.getUserName(), "Test Name");
        assertEquals(res.getAccountNumber(), 1234567890L);
        assertEquals(res.getAccountType(), "Checking");
    }

    @Test
    void testInterestSavings() throws Exception {
        SavingsAccount acc = new SavingsAccount(1234567890L, "Test Name",3000, "Savings");
        Response res = acc.calculateInterest();
        assertEquals(res.getStatus(), "SUCCESS");
        assertEquals(res.getMessage(), "Interest has been calculated Successfully");
        assertEquals(res.getExistingBalance(), 3000);
        assertEquals(res.getNewBalance(), 3090);
        assertEquals(res.getUserName(), "Test Name");
        assertEquals(res.getAccountNumber(), 1234567890L);
        assertEquals(res.getAccountType(), "Savings");
    }

}