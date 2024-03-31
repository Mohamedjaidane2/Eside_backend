package com.eside.auth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.eside.auth.model.Role.NAME;
import static com.eside.auth.model.Role.TABLE_NAME;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = TABLE_NAME,uniqueConstraints = @UniqueConstraint(name = "define_unique",columnNames = NAME))
@Entity
public class Role {

    public static final String TABLE_NAME="role_table";
    public static final String NAME="name";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = NAME)
    private String name;

    public Role(String name) {
        this.name = name;
    }
}