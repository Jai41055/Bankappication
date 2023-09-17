package com.bankapp.Model;

public class DBQueryConstants {

    public final static String CREATE_ACCOUNT_INSERT = "insert into accounts (first_name, last_name, email_address, phone_number, account_number,history, account_balance, account_Type) values (?::varchar,?::varchar,?::varchar,?::varchar,?::bigint,?::varchar,?::double precision,?::varchar)";

    public final static String GET_ACCOUNT_DETAILS = "select first_name,last_name,account_balance,account_number,account_type,history from accounts where account_number = ?::bigint;";

    public final static String UPDATE_DATA= "update accounts set account_balance = ?::double precision,history = ?::varchar where account_number = ?::bigint";

    public final static String UPDATE_BALANCE= "update accounts set account_balance = ?::double precision where account_number = ?::bigint";
}
