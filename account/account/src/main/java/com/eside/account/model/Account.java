package com.eside.account.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class Account {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "account_name", nullable = false)
    private String accountName;

    private String firstName;

    private String lastName;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date", updatable = false)
    private Date creationDate;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date", updatable = false)
    private Date updateDate;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @JsonIgnore
    @OneToMany(mappedBy = "receiverAccount", cascade = CascadeType.ALL)
    private List<FeedBack> recivedFeedBack;
    @JsonIgnore
    @OneToMany(mappedBy = "senderAccount", cascade = CascadeType.ALL)
    private List<FeedBack> sendedFeedBacks;
    @JsonIgnore
    @OneToOne()
    @JoinColumn(name = "information_id")
    private Information information;

}
