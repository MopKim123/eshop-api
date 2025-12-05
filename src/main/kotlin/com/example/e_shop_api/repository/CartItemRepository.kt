package com.example.e_shop_api.repository

import com.example.e_shop_api.model.entity.CartItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CartItemRepository : JpaRepository<CartItem, Long> {
    fun findByCartId(cartId: Long): List<CartItem>
}
