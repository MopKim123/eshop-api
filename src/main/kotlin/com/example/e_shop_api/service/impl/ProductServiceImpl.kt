package com.example.e_shop_api.service.impl

import com.example.e_shop_api.model.dto.ProductDto
import com.example.e_shop_api.model.mapper.ProductMapper.toResponse
import com.example.e_shop_api.repository.ProductRepository
import com.example.e_shop_api.service.ProductService
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl(
    private val productRepository: ProductRepository
): ProductService {

    override fun getAllProducts(): List<ProductDto> =
        productRepository.findAll().map { it.toResponse() }

    override fun getProductById(id: Long): ProductDto =
        productRepository.findById(id)
            .orElseThrow { RuntimeException("Product not found") }
            .toResponse()
}
