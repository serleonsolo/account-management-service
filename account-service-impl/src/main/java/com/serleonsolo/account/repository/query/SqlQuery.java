package com.serleonsolo.account.repository.query;

public class SqlQuery {

    public static final String GET_ACCOUNT_BY_ID = "select * from account where active_to is null and id = ?::uuid";

    public static final String GET_ALL_ACCOUNTS = "select * from account where 1=1";

    public static final String ACCOUNT_WITH_DATE = " and ?::timestamptz >= active_from and (?::timestamptz < active_to or active_to is null)";
    public static final String ACCOUNT_WITHOUT_DATE = " and active_to is null";
    public static final String ACCOUNT_STATUS = " and status = ?::integer";
    public static final String ACCOUNT_NAME = " and account_name = ?::text";

    public static final String UPDATE_ACCOUNTS_WITHOUT_DATE =
            "update account set active_to = now() - interval '0.001' second" +
                    " where name = ?::text" +
                    " and balance = ?::decimal" +
                    " and status = ?::numeric" +
                    " and active_to is null;";

    public static final String INSERT_ACCOUNTS = "insert into account (account_name, balance, status) values(?, ?, ?)";
}
