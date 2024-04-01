package com.eside.auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String role;

    private Long accountId;
}
