package com.eside.auth.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RegisterRequest {

    @NotEmpty(message = "FirstName is mandatory")
    @NotBlank(message = "FirstName is mandatory")
    private String firstname ;

    @NotEmpty(message = "LastName is mandatory")
    @NotBlank(message = "LastName is mandatory")
    private String lastname ;

    @Email(message = "Email is not formatted")
    @NotEmpty(message = "Email is mandatory")
    @NotBlank(message = "Email is mandatory")
    private String email ;

    @NotEmpty(message = "Password is mandatory")
    @NotBlank(message = "Password is mandatory")
    @Size(min = 8 , message = "Password should be 8 characters Long minimum")
    private String password ;
}
