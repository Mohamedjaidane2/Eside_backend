package com.eside.payment.externalData;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Account {
    private Long id;

    private String accountName;

    private Date createdAt;

    private Date updateAt;

    private boolean isActive;
}
