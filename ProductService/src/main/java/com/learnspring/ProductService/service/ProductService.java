package com.learnspring.ProductService.service;

import com.learnspring.ProductService.model.ProductRequest;

public interface ProductService {
    long addProduct(ProductRequest productRequest);
}
