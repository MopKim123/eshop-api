package com.example.e_shop_api.service

import com.example.e_shop_api.model.dto.CartDto

interface CartService {
    fun getCart(userId: Long): CartDto
    @Transactional
    fun addToCart(userId: Long, productId: Long, quantity: Int): CartDto
    @Transactional
    fun updateCartItem(cartItemId: Long, quantity: Int): CartDto
}