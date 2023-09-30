package com.mati.fadu_backend.controller.model;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
public class Product {
    int productId;
    String productName;
    String shortDescription;
    String description;
    String specifications;
    private String supplier;
    String category;
    String subCategory;
    float starRating;
}