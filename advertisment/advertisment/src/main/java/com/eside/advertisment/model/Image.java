package com.eside.advertisment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Images")
public class Image {

    @Id
    @GeneratedValue()
    private Long id;

    private String name;

    private String type;

    private String path;

    @ManyToOne
    @JoinColumn(name = "product_id",nullable = true)
    @JsonIgnore
    private Product product;

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", path='" + path + '\'' +
                ", product=" + product +
                '}';
    }
}