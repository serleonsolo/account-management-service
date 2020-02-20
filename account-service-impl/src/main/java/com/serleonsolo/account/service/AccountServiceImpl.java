package com.serleonsolo.account.service;

import com.serleonsolo.account.exception.ExceptionFactory;
import com.serleonsolo.account.model.Account;
import com.serleonsolo.account.model.AccountSearchParameters;
import com.serleonsolo.account.repository.QueryRepository;
import com.serleonsolo.account.repository.mapper.AccountRowMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static com.serleonsolo.account.repository.query.SqlQuery.*;

@Service
@Slf4j
public class AccountServiceImpl
        implements AccountService {

    private final QueryRepository queryRepository;
    private final AccountRowMapper<Account> accountRowMapper;

    @Contract(pure = true)
    @Autowired
    public AccountServiceImpl(QueryRepository queryRepository,
                              AccountRowMapper<Account> accountRowMapper) {
        this.queryRepository = queryRepository;
        this.accountRowMapper = accountRowMapper;
    }

    public Flux<Account> getAccount(String id) {
        final List<Object> queryParams = new ArrayList<>();
        queryParams.add(id);
        return calculateAccounts(GET_ACCOUNT_BY_ID, queryParams.toArray())
                .switchIfEmpty(Flux.error(ExceptionFactory.notFoundError("Account not found")));
    }

    public Flux<Account> getAccounts(@NotNull AccountSearchParameters params) {
        // TODO: improve validations
        if (params.getDate() == null && params.getAccountName() == null && params.getStatus() == null) {
            return Flux.error(ExceptionFactory.badRequestError("At least one filter parameter should be specified"));
        }

        final StringBuilder query = new StringBuilder(GET_ALL_ACCOUNTS);
        final List<Object> queryParams = new ArrayList<>();

        if (params.getStatus() != null) {
            query.append(ACCOUNT_STATUS);
            queryParams.add(params.getStatus());
        }

        if (params.getAccountName() != null) {
            query.append(ACCOUNT_NAME);
            queryParams.add(params.getAccountName());
        }

        if (params.getDate() != null) {
            query.append(ACCOUNT_WITH_DATE);
            queryParams.add(params.getDate());
            queryParams.add(params.getDate());
        } else {
            query.append(ACCOUNT_WITHOUT_DATE);
        }

        return calculateAccounts(query.toString(), queryParams.toArray())
                .switchIfEmpty(Flux.just());
    }

    @Override
    @Transactional
    public Mono<Boolean> updateAccounts(@NotNull List<Account> accounts) {
        return Mono.<Boolean>create(sink -> {
            // TODO: improve validations
            final List<Object[]> updateBatch = new ArrayList<>();
            for (final Account account : accounts) {
                final Object[] values = new Object[]{
                        account.getAccountName(),
                        account.getBalance(),
                        account.getStatus()};
                updateBatch.add(values);
            }
            boolean result = validateSqlOperation(accounts,
                    queryRepository.batchUpdate(UPDATE_ACCOUNTS_WITHOUT_DATE, updateBatch));

            if (result) {
                final List<Object[]> insertBatch = new ArrayList<>();
                for (final Account account : accounts) {
                    final Object[] values = new Object[]{
                            account.getAccountName(),
                            account.getBalance(),
                            account.getStatus()};
                    insertBatch.add(values);
                }
                result = validateSqlOperation(accounts,
                        queryRepository.batchUpdate(INSERT_ACCOUNTS, insertBatch));
            }

            if (result) {
                sink.success(result);
            } else {
                sink.error(ExceptionFactory.notFoundError("Accounts not found"));
            }
        }).subscribeOn(Schedulers.elastic());
    }

    private Flux<Account> calculateAccounts(@NotNull String query,
                                            Object[] queryParams) {
        return queryRepository.getQueryResult(query, queryParams, accountRowMapper);
    }

    private boolean validateSqlOperation(@NotNull List<Account> accounts,
                                         int[] sqlResult) {
        if (accounts.size() != ArrayUtils.getLength(sqlResult)) {
            return false;
        }

        for (int value : sqlResult) {
            if (value != 1) {
                return false;
            }
        }

        return true;
    }
}
