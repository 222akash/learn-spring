package com.learnspring.ProductService.service;

import com.learnspring.ProductService.model.ProductRequest;
import com.learnspring.ProductService.model.ProductResponse;

public interface ProductService {
    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(Long productId);

    void reduceQuantity(long productId, long quantity);
}
