package com.eside.advertisment.model;

import com.eside.advertisment.enums.ColorEnum;
import com.eside.advertisment.enums.ProductStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Advertisment advertisement;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Image> images;

    @Enumerated(EnumType.STRING)
    private ProductStatusEnum productStatus;

    @Enumerated(EnumType.STRING)
    private ColorEnum color;

    private String features;

    @ManyToOne
    @JoinColumn(name = "subCategorie_id",nullable = true)
    private SubCategory subCategory;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date", updatable = false)
    private Date creationDate;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productStatus=" + productStatus +
                ", color=" + color +
                ", features='" + features + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}