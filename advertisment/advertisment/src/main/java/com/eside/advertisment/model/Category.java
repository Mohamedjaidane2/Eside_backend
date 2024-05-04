package com.eside.advertisment.model;


import com.eside.advertisment.enums.ColorEnum;
import com.eside.advertisment.enums.ProductStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;


    @OneToMany(mappedBy = "Category", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<SubCategory> subCategories;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date", updatable = false)
    private Date creationDate;
}
