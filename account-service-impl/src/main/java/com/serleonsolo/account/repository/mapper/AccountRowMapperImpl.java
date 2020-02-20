package com.serleonsolo.account.repository.mapper;

import com.serleonsolo.account.constants.AccountManagementConstants;
import com.serleonsolo.account.model.Account;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Service
public class AccountRowMapperImpl
        implements AccountRowMapper<Account> {

    @Override
    public Account mapRow(@NotNull ResultSet resultSet)
            throws SQLException {
        return Account.builder()
                .id(UUID.fromString(resultSet.getString(AccountManagementConstants.ID)))
                .accountName(resultSet.getString(AccountManagementConstants.ACCOUNT_NAME))
                .balance(resultSet.getBigDecimal(AccountManagementConstants.BALANCE))
                .status(resultSet.getInt(AccountManagementConstants.STATUS))
                .activeFrom(resultSet.getTimestamp(AccountManagementConstants.ACTIVE_FROM))
                .activeTo(resultSet.getTimestamp(AccountManagementConstants.ACTIVE_TO))
                .build();
    }
}
