package com.chamo.BackEnd.services;

import com.chamo.BackEnd.dto.product.ProductReqDto;
import com.chamo.BackEnd.dto.product.ProductResDto;
import com.chamo.BackEnd.entity.ProductEntity;
import com.chamo.BackEnd.repository.ProductRepository;
import com.mongodb.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductEntity> findAll(){
        return productRepository.findAll();
    }

    public ProductEntity createProduct(ProductReqDto productData){
        ProductEntity newProduct = new ProductEntity(
                productData.getQty(),
                productData.getDetails(),
                productData.getTitle(),
                productData.getPrice());
        return productRepository.save(newProduct);
    }


    public ProductResDto addProduct(ProductReqDto productData) {
        try {

            if (productData == null) {
                return new ProductResDto(null, "Product data cannot be null");
            }

            if (productData.getTitle() == null || productData.getTitle().trim().isEmpty()) {
                return new ProductResDto(null, "Product title is required");
            }

            if (productData.getPrice() == null || productData.getPrice() <= 0) {
                return new ProductResDto(null, "Product price must be positive");
            }

            if (productData.getQty() < 0) {
                return new ProductResDto(null, "Product quantity cannot be negative");
            }

            Optional<ProductEntity> existingProduct = productRepository.findByTitle(productData.getTitle());
            if (existingProduct.isPresent()) {
                return new ProductResDto(null, "Product with this title already exists");
            }

            ProductEntity newProduct = this.createProduct(productData);

            if (newProduct.getId() == null) {
                return new ProductResDto(null, "Failed to create product - no ID generated");
            }

            return new ProductResDto(
                    String.format("Product '%s' created successfully with ID: %s",
                            newProduct.getTitle(),
                            newProduct.getId()),
                    null
            );

        } catch (DuplicateKeyException e) {
            return new ProductResDto(null, "Product with this title already exists");

        } catch (IllegalArgumentException e) {
            return new ProductResDto(null, "Invalid input data: " + e.getMessage());

        } catch (Exception e) {
            return new ProductResDto(null, "Failed to create product: " + e.getMessage());
        }
    }

}
