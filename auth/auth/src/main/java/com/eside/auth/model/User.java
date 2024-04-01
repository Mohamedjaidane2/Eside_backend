package com.eside.auth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import static com.eside.auth.model.User.EMAIL;
import static com.eside.auth.model.User.TABLE_NAME;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = TABLE_NAME,uniqueConstraints = @UniqueConstraint(name = "define_unique",columnNames = EMAIL))

public class User implements UserDetails {

    public static final String TABLE_NAME = "user_table";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    public static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String ROLE_ID = "role_id";
    private static final String LOGIN_STATUS = "login-status";
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = FIRST_NAME, nullable = false)
    private String firstName;

    @Column(name = LAST_NAME, nullable = false)
    private String lastName;

    @Column(name = EMAIL)
    private String email;

    @Column(name = PASSWORD, nullable = false)
    private String password;

    @Column(name = LOGIN_STATUS, nullable = false)
    private Boolean loginStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = ROLE_ID)
    private Role role;

    private Long accountId;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getName()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}