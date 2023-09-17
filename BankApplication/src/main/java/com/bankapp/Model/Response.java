package com.bankapp.Model;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class Response {

    private String status;
    private String message;
    private double amount;
    private double existingBalance;
    private double newBalance;
    private long accountNumber;
    private String userName;
    private String accountType;
    private ArrayList<String> transactions;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getExistingBalance() {
        return existingBalance;
    }

    public void setExistingBalance(double existingBalance) {
        this.existingBalance = existingBalance;
    }

    public double getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(double newBalance) {
        this.newBalance = newBalance;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public ArrayList<String> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<String> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "Response{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", amount=" + amount +
                ", existingBalance=" + existingBalance +
                ", newBalance=" + newBalance +
                ", accountNumber=" + accountNumber +
                ", userName='" + userName + '\'' +
                ", accountType='" + accountType + '\'' +
                ", transactions=" + transactions +
                '}';
    }
}
