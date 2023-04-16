package com.learnspring.ProductService.serviceImpl;

import com.learnspring.ProductService.entity.Product;
import com.learnspring.ProductService.exception.ProductServiceCustomException;
import com.learnspring.ProductService.model.ProductRequest;
import com.learnspring.ProductService.model.ProductResponse;
import com.learnspring.ProductService.repository.ProductRepository;
import com.learnspring.ProductService.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.beans.BeanUtils.*;

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

    @Override
    public ProductResponse getProductById(Long productId) {
        log.info("Inside getProductById method in repository ");
        Product product=
                productRepository.findById(productId)
                        .orElseThrow(()->new ProductServiceCustomException("Product with given Id not Exists","ERROR101"));

        ProductResponse productResponse =
                new ProductResponse();
        copyProperties(product,productResponse); //if all the field all same then it work || BeanUtils.copyProperties(product,productResponse);


        return productResponse;
    }

    @Override
    public void reduceQuantity(long productId, long quantity) {
        log.info("Reduce Quantity: {} for ProductId: {}", quantity, productId);
        Product product =
                productRepository.findById(productId)
                        .orElseThrow(() -> new ProductServiceCustomException("Product with given ProductId does not Exist",
                                "ERROR-P01"));
        if (product.getQuantity() < quantity) {
            throw new ProductServiceCustomException("Product Does not have Sufficent Quantity",
                    "ERROR-P02");
        }
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
        log.info("Product quantity Successfully updated");
    }
}
