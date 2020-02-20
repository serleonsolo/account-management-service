package com.serleonsolo.account.repository;

import com.serleonsolo.account.repository.mapper.AccountRowHandlerImpl;
import com.serleonsolo.account.repository.mapper.AccountRowMapper;
import org.jetbrains.annotations.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.function.Consumer;

@Repository
@Transactional
public class QueryRepositoryImpl<T>
        implements QueryRepository<T> {

    private final JdbcTemplate jdbcTemplate;

    @Contract(pure = true)
    @Autowired
    public QueryRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Flux<T> getQueryResult(String query,
                                  Object[] params,
                                  AccountRowMapper<T> rowMapper) {
        return Flux
                .create((Consumer<FluxSink<T>>) costFluxSink -> {
                    jdbcTemplate.query(query, params, new AccountRowHandlerImpl<T>(costFluxSink, rowMapper));
                    costFluxSink.complete();
                })
                .subscribeOn(Schedulers.elastic(), false);
    }

    @Override
    public int update(String query,
                      Object[] params) {
        return jdbcTemplate.update(query, params);
    }

    public int[] batchUpdate(String query,
                             List<Object[]> params) {
        return jdbcTemplate.batchUpdate(query, params);
    }
}
