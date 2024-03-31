package com.eside.account.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "account_information")
public class Information {
    @Id
    @GeneratedValue()
    private Long id;

    private String profilePicture;

    private String bio;

    private String phoneNumber;

    private String address ;

    private String optionalAddress ;

    private int postalCode ;

    private String city ;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date", updatable = false)
    private Date creationDate;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date", updatable = false)
    private Date updateDate;

    @OneToOne()
    @JoinColumn(name = "account_id")
    private Account account;
}
