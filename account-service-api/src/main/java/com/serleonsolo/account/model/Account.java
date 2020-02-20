package com.serleonsolo.account.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Account
        implements Serializable {
    private static final long serialVersionUID = -5363940581654194986L;

    @JsonIgnore
    private UUID id;

    private String accountName;
    private BigDecimal balance;
    private Integer status;
    private Timestamp activeFrom;
    private Timestamp activeTo;
}
