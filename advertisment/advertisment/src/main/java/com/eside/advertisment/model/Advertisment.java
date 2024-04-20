package com.eside.advertisment.model;

import com.eside.advertisment.enums.AdvertisementSoldStatusEnum;
import com.eside.advertisment.enums.AdvertisementStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class Advertisment {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    private Double price;
    private Double oldPrice;

    @Enumerated(EnumType.STRING)
    private AdvertisementSoldStatusEnum advertisementSoldStatusEnum ;

    @Enumerated(EnumType.STRING)
    private AdvertisementStatusEnum advertisementStatusEnum ;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date", updatable = false)
    private Date creationDate;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date", updatable = true)
    private Date updateDate;

    @JoinColumn(name = "owner_id")
    private Long userAccountId;

    @JoinColumn(name = "owner_accountName")
    private String ownerAccountName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "order_Id",nullable = true)
    private Long orderId ;
    @Override
    public String toString() {
        return "Advertisment{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", oldPrice=" + oldPrice +
                ", advertisementSoldStatusEnum=" + advertisementSoldStatusEnum +
                ", advertisementStatusEnum=" + advertisementStatusEnum +
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                ", userAccountId=" + userAccountId +
                ", product=" + product +
                '}';
    }
}
