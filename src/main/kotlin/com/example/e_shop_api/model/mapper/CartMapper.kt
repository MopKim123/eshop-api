package com.example.e_shop_api.model.mapper

import com.example.e_shop_api.model.dto.CartDto
import com.example.e_shop_api.model.dto.CartItemDto
import com.example.e_shop_api.model.entity.Cart
import com.example.e_shop_api.model.entity.CartItem
import com.example.e_shop_api.model.entity.Order
import com.example.e_shop_api.model.entity.Product
import java.math.BigDecimal
import java.time.LocalDateTime

object CartMapper {

    fun Cart.toResponse(): CartDto {
        val dto = CartDto()
        dto.id = this.id
        dto.userId = this.user?.id
        dto.items = this.items.map { it.toResponse() }.toMutableList()
        dto.totalAmount = this.items.sumOf { it.subtotal?.toDouble() ?: 0.0 }.toString()
        return dto
    }

    fun CartItem.toResponse(): CartItemDto {
        val dto = CartItemDto()
        dto.productId = this.product?.id
        dto.productName = this.product?.name
        dto.quantity = this.quantity
        dto.price = this.product?.price.toString()
        dto.subtotal = this.subtotal.toString()
        return dto
    }

    fun Cart.addItem(product: Product, quantity: Int): CartItem {
        val newItem = CartItem()
        newItem.cart = this
        newItem.product = product
        newItem.quantity = quantity
        newItem.subtotal = product.price?.multiply(BigDecimal(quantity))
        this.items.add(newItem)
        return newItem
    }

    fun Cart.toOrder(): Order {
        val order = Order()
        order.user = this.user
        order.totalAmount = this.items.sumOf { it.subtotal ?: BigDecimal.ZERO }
        order.status = "PENDING"
        order.createdAt = LocalDateTime.now()
        return order
    }
}