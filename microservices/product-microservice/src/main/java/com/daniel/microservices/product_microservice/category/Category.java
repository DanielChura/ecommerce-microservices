package com.daniel.microservices.product_microservice.category;

import java.util.ArrayList;
import java.util.List;

import com.daniel.microservices.product_microservice.product.Product;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @ToString.Exclude
    @Builder.Default
    private List<Product> products = new ArrayList<>();
}
