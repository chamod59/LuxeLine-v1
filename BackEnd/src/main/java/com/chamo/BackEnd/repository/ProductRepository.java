package com.chamo.BackEnd.repository;

import com.chamo.BackEnd.entity.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductRepository extends MongoRepository<ProductEntity,String> {
    Optional<ProductEntity> findByTitle(String productTitle);
}
