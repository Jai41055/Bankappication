package com.bankapp.controller;

import com.bankapp.Model.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("feature")
public class FeatureController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final Log logger = LogFactory.getLog(FeatureController.class);

    @PostMapping("createAccount")
    public Response createAccount(@RequestBody UserAccount account) throws SQLException {

        Response res = new Response();
        logger.info("-----" + this.getClass().getName() + "-- " + "CreateMethod method begins!");
        try {
            //Generating Random Account Number with 10 Digit
            long accountNumber = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;

            //Inserting Data into table
            String history = Double.toString(account.getInitialAmount());
            int value = jdbcTemplate.update(DBQueryConstants.CREATE_ACCOUNT_INSERT,account.getFirstName(), account.getLastName(),
                    account.getUserEmail(), account.getUserPhone(), accountNumber, history, account.getInitialAmount(),
                    account.getAccountType());

            if(value == 1){
                res.setStatus(Constants.SUCCESS);
                res.setMessage(Constants.CREATED);
                res.setAccountNumber(accountNumber);
                res.setUserName(account.getFirstName() + " " + account.getLastName());
                res.setExistingBalance(account.getInitialAmount());
                res.setAccountType(account.getAccountType());
            } else {
                res.setStatus(Constants.FAILED);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        logger.info("-----" + this.getClass().getName() + "-- " + "CreateMethod method End!");
        return res;
    }

    @PostMapping("transfer")
    public Response depositMoney(@RequestBody UserAccount account){
        logger.info("-----" + this.getClass().getName() + "-- " + "DepositMoney method begins!");
        Response res = new Response();
        try{
            //Getting Account Details
            ArrayList data = getData(account.getAccountNumber());

            //Depositing and withdraw Money
            if(data.get(2).toString().equals("Checking")){
                CheckingAccount acc  = new CheckingAccount((long) data.get(3), data.get(0) + " " + data.get(1), (double) data.get(4), data.get(2).toString());
                if(account.getServiceId() == 3){
                    res = acc.deposit(account.getDepositAmount());
                } else {
                    res = acc.withdraw(account.getWithdrawAmount());
                }
            } else {
                SavingsAccount acc  = new SavingsAccount((long) data.get(3), data.get(0) + " " + data.get(1), (double) data.get(4), data.get(2).toString());
                if(account.getServiceId() == 3){
                    res = acc.deposit(account.getDepositAmount());
                } else {
                    res = acc.withdraw(account.getWithdrawAmount());
                }
            }

            //Getting Transactions list
            String[] arr = data.get(5).toString().split(",");
            ArrayList<String> listOfTransaction = new ArrayList<>(Arrays.asList(arr));
            if(res.getStatus().equals("SUCCESS")){
                if(account.getServiceId() == 3){
                    listOfTransaction.add(Double.toString(account.getDepositAmount()));
                } else {
                    listOfTransaction.add("-"+ account.getWithdrawAmount());
                }
            }
            res.setTransactions(listOfTransaction);

            //Updating Transactions List
            StringBuilder sb = new StringBuilder();
            for (int i=0;i<listOfTransaction.size()-1;i++){
                sb.append(listOfTransaction.get(i)).append(",");
            }
            sb.append(listOfTransaction.get(listOfTransaction.size() -1));

            //Updating the data in the Table to the specific Account
            jdbcTemplate.update(DBQueryConstants.UPDATE_DATA, res.getAmount(),sb, account.getAccountNumber());


        } catch (Exception e){
            e.printStackTrace();
            res.setStatus(Constants.FAILED);
            res.setMessage(e.getMessage());
        }
        logger.info("-----" + this.getClass().getName() + "-- " + "DepositMoney method End!");
        return res;
    }

    @PostMapping("interest")
    public Response calculateInterest(@RequestBody UserAccount account){
        logger.info("-----" + this.getClass().getName() + "-- " + "CalculateInterest method Begins!");
        Response res = new Response();
        try{
            //Getting Account Details
            ArrayList data = getData(account.getAccountNumber());

            //Depositing and withdraw Money
            if(data.get(2).toString().equals("Checking")){
                CheckingAccount acc  = new CheckingAccount((long) data.get(3), data.get(0) + " " + data.get(1), (double) data.get(4), data.get(2).toString());
                res = acc.calculateInterest();
            } else {
                SavingsAccount acc  = new SavingsAccount((long) data.get(3), data.get(0) + " " + data.get(1), (double) data.get(4), data.get(2).toString());
                res = acc.calculateInterest();
            }

            //Updating the data in the Table to the specific Account
            if(res.getStatus().equals("SUCCESS")){
                jdbcTemplate.update(DBQueryConstants.UPDATE_BALANCE, res.getNewBalance(), account.getAccountNumber());
            }

        } catch (Exception e){
            e.printStackTrace();
            res.setStatus(Constants.FAILED);
            res.setMessage(e.getMessage());
        }
        logger.info("-----" + this.getClass().getName() + "-- " + "CalculateInterest method Begins!");
        return res;

    }

    @PostMapping("balance")
    public Response getBalance(@RequestBody UserAccount account){
        logger.info("-----" + this.getClass().getName() + "-- " + "Get Balance method Begins!");
        Response res = new Response();
        try {
            //Getting Account Details
            ArrayList data = getData(account.getAccountNumber());
            res.setStatus(Constants.SUCCESS);
            res.setMessage(Constants.BALANCE);
            res.setUserName(data.get(0) + " " + data.get(1));
            res.setAccountNumber(account.getAccountNumber());
            res.setAccountType(data.get(2).toString());
            res.setAmount((double) data.get(4));

        } catch (Exception e){
            e.printStackTrace();
            res.setStatus(Constants.FAILED);
            res.setMessage(e.getMessage());
        }
        logger.info("-----" + this.getClass().getName() + "-- " + "Get Balance method End!");
        return res;
    }

    public ArrayList getData(long accountNumber) {
        return jdbcTemplate.query(DBQueryConstants.GET_ACCOUNT_DETAILS, rs -> {
            ArrayList list = new ArrayList();
            while (rs.next()){
                list.add(rs.getString("first_name"));
                list.add(rs.getString("last_name"));
                list.add(rs.getString("account_type"));
                list.add(rs.getLong("account_number"));
                list.add(rs.getDouble("account_balance"));
                list.add(rs.getString("history"));
            }
            return list;
        }, accountNumber);
    }

    //Quit the application

    public void quitApplication(){
        logger.info("-----" + this.getClass().getName() + "-- " + "Quit Application method Begins!");
        logger.info("-----" + "Exit the application - Terminated!");
        System.exit(0);
    }
}
