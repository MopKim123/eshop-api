package com.example.e_shop_api.service

import com.example.e_shop_api.model.dto.ProductDto

interface ProductService {
    fun getAllProducts(): List<ProductDto>
    fun getProductById(id: Long): ProductDto
}