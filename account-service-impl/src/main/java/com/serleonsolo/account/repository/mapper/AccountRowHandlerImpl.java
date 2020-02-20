package com.serleonsolo.account.repository.mapper;

import org.jetbrains.annotations.Contract;
import org.springframework.jdbc.core.RowCallbackHandler;
import reactor.core.publisher.FluxSink;

import java.sql.ResultSet;

public class AccountRowHandlerImpl<T>
        implements RowCallbackHandler {

    private AccountRowMapper<T> rowMapper;
    private FluxSink<T> costFluxSink;

    @Contract(pure = true)
    public AccountRowHandlerImpl(FluxSink<T> costFluxSink,
                                 AccountRowMapper<T> rowMapper) {
        this.costFluxSink = costFluxSink;
        this.rowMapper = rowMapper;
    }

    @Override
    public void processRow(ResultSet resultSet) {
        try {
            T row = rowMapper.mapRow(resultSet);
            if (row != null) {
                costFluxSink.next(row);
            }
        } catch (Exception e) {
            costFluxSink.error(e);
        }
    }
}
