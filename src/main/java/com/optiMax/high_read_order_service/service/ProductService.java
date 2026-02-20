package com.optiMax.high_read_order_service.service;

import com.optiMax.high_read_order_service.dto.request.OrderRequest;
import com.optiMax.high_read_order_service.dto.request.ProductRequest;
import com.optiMax.high_read_order_service.dto.response.ProductResponse;
import com.optiMax.high_read_order_service.entity.Product;
import com.optiMax.high_read_order_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setAvailableQuantity(request.getQuantity());
        productRepository.save(product);

        return buildProductResponse(product);
    }

    private ProductResponse buildProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .availableQuantity(product.getAvailableQuantity())
                .build();
    }

    public Product findProductById(OrderRequest orderRequest) {
        return productRepository.findById(orderRequest.getProductId())
                .orElseThrow(()-> new RuntimeException("Product Not Found"));
    }

    public void isProductAvailable(Product product) {
        if (product.getAvailableQuantity() <= 0) {
            throw new RuntimeException("Out of stock");
        }
    }
}
