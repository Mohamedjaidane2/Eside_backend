package com.eside.account.externalDto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private String firstName;
    private String lastName;
    private String email;
    private Boolean loginStatus;
    private String roleName;
}
