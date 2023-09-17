package com.bankapp;

import com.bankapp.Model.CheckingAccount;
import com.bankapp.Model.Response;
import com.bankapp.Model.SavingsAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;

@SpringBootApplication
public class BankApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);

	}

}
