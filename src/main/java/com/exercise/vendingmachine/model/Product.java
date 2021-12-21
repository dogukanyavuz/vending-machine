package com.exercise.vendingmachine.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table
@Data
@Builder
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Integer amountAvailable;

    public Integer cost;

    @Column(length = 64)
    public String productName;

    public Long sellerId;

    public Product(long id, int amountAvailable, BigInteger bigInteger, String fanta, long sellerId) { }

}
