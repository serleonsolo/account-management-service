package com.serleonsolo.account.repository.mapper;

import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public interface AccountRowMapper<T> {

    T mapRow(ResultSet resultSet)
            throws SQLException;

    default UUID getUUID(@NotNull ResultSet resultSet,
                         String columnName)
            throws SQLException {
        String value = resultSet.getString(columnName);
        return value != null ? UUID.fromString(value) : null;
    }
}
