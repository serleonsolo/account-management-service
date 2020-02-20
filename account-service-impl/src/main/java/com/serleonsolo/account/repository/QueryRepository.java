package com.serleonsolo.account.repository;

import com.serleonsolo.account.repository.mapper.AccountRowMapper;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * Execute sql query and convert result to required type
 * @param <T> object with type T will contains result of sql query
 */
public interface QueryRepository<T> {

    /**
     * @param query sql query
     * @param params param that insert in query
     * @param rowMapper class that map result of sql query to required type
     * @return result of sql query in required type
     */
    Flux<T> getQueryResult(String query, Object[] params, AccountRowMapper<T> rowMapper);

    /**
     * @param query sql query
     * @param params param that insert in query
     * @return number updated rows
     */
    int update(String query, Object[] params);

    /**
     * @param query sql query
     * @param params param that update in query
     * @return array of number updated rows
     */
    int[] batchUpdate(String query, List<Object[]> params);
}
