package com.example.e_shop_api.model.mapper

import com.example.e_shop_api.model.dto.CartDto
import com.example.e_shop_api.model.dto.CartItemDto
import com.example.e_shop_api.model.dto.ProductDto
import com.example.e_shop_api.model.entity.Cart
import com.example.e_shop_api.model.entity.CartItem
import com.example.e_shop_api.model.entity.Product

object ProductMapper {

    fun Product.toResponse(): ProductDto {
        val dto = ProductDto()
        dto.id = this.id
        dto.name = this.name
        dto.description = this.description
        dto.price = this.price.toString()
        dto.stock = this.stock
        return dto
    }
}