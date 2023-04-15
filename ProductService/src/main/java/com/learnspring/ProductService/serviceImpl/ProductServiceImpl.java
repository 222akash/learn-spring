package com.learnspring.ProductService.serviceImpl;

import com.learnspring.ProductService.entity.Product;
import com.learnspring.ProductService.model.ProductRequest;
import com.learnspring.ProductService.repository.ProductRepository;
import com.learnspring.ProductService.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Override
    public long addProduct(ProductRequest productRequest) {
       log.info("Inside addProduct method in repository ");
       Product product =
               Product.builder()
                       .productName(productRequest.getName())
                       .price(productRequest.getPrice())
                       .quantity(productRequest.getQuantity())
                       .build();

        productRepository.save(product);

        log.info("Product save successfully ");
        return product.getProductId();
    }
}
