package com.example.demo.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.demo.models.Product;

public interface ProductMongoRepository extends MongoRepository<Product, String> {
    Product findById(ObjectId id);
}
