package com.eside.auth.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GuardResponse {
    private String email;
    private String role;
    private Boolean isExpired;
}
