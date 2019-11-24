package com.example.demo.controllers;

import com.example.demo.models.Product;
import com.example.demo.repositories.ProductMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RequestMapping("/api/product")
@RestController
public class ProductController {
    @Autowired
    private ProductMongoRepository productMongoRepository;

    @GetMapping
    public ResponseEntity getProducts() {
        Map<String, List<Product>> res = new HashMap<String, List<Product>>();
        return new ResponseEntity(this.productMongoRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public Product addProduct(
            @Valid
            @RequestBody Product product,
            @RequestHeader Map<String, String> headers) {

        return this.productMongoRepository.insert(product);
    }

    @GetMapping("/{id}")
    public ResponseEntity getProduct(@Valid @PathVariable("id") String id) {
        Product product = this.productMongoRepository.findById(id).orElse(null);

        if (product == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(product, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateProduct(@PathVariable("id") String id, @RequestBody Product newProduct) {
        Product product = this.productMongoRepository.findById(id).orElse(null);

        if (product == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        System.out.println(product);

        product.setDescription(newProduct.getDescription());
        product.setName(newProduct.getName());

        return new ResponseEntity(this.productMongoRepository.save(product), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable("id") String id) {
        this.productMongoRepository.deleteById(id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
