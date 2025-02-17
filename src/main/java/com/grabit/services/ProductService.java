package com.grabit.services;


import com.grabit.dtos.ProductDTO;
import com.grabit.entities.Product;
import com.grabit.mappers.ProductMapper;
import com.grabit.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    private final ProductMapper productMapper = ProductMapper.INSTANCE;

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productMapper::toProductDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(UUID id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return productMapper.toProductDTO(product);
    }

    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = productMapper.toProduct(productDTO);
        product = productRepository.save(product);
        return productMapper.toProductDTO(product);
    }

    public ProductDTO updateProduct(UUID id, ProductDTO productDTO) {
        /// TODO: change use of RuntimeException to a custom exception 
        Product existingProduct = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found"));
        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct = productRepository.save(existingProduct);
        return productMapper.toProductDTO(existingProduct);
    }

    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }
}
