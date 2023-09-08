package com.deymerhmarker.domain.service;

import com.deymerhmarker.domain.Product;
import com.deymerhmarker.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAll(){
        return productRepository.getAll();
    }

    public Optional<Product> getProduct(int productId){
        return productRepository.getProduct(productId);
    }

    public Optional<List<Product>> getByCategory(int categoryId){
        return productRepository.getByCategory(categoryId);
    }

    Optional<List<Product>> getScaresProducts(int quantity){
        return productRepository.getScaresProducts(quantity);
    }

    public Product save(Product product){
        return productRepository.save(product);
    }

    public boolean delete(int productId){
        return getProduct(productId).map((product) -> {
            productRepository.delete(productId);
            return true;
        }).orElse(false);
    }

    public boolean delete2(int productId){
        if (!getProduct(productId).isPresent()){
            return false;
        }
        productRepository.delete(productId);
        return true;
    }

    public void update (int productId , Product product){
        Product newProduct = getProduct(productId).get();
        newProduct.setName(product.getName());
        newProduct.setCategory(product.getCategory());
        newProduct.setActive(product.isActive());
        newProduct.setPrice(product.getPrice());
        newProduct.setStock(product.getStock());
        save(newProduct);
    }
}