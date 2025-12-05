package com.example.e_shop_api.model.mapper

import com.example.e_shop_api.model.dto.CartDto
import com.example.e_shop_api.model.dto.CartItemDto
import com.example.e_shop_api.model.dto.OrderDto
import com.example.e_shop_api.model.dto.OrderItemDto
import com.example.e_shop_api.model.entity.Cart
import com.example.e_shop_api.model.entity.CartItem
import com.example.e_shop_api.model.entity.Order
import com.example.e_shop_api.model.entity.OrderItem

object OrderMapper {

    fun Order.toResponse(): OrderDto {
        val dto = OrderDto()
        dto.id = this.id
        dto.userId = this.user?.id
        dto.items = this.items.map { it.toResponse() }.toMutableList()
        dto.totalAmount = this.totalAmount.toString()
        dto.status = this.status
        dto.createdAt = this.createdAt.toString()
        return dto
    }

    fun OrderItem.toResponse(): OrderItemDto {
        val dto = OrderItemDto()
        dto.productId = this.product?.id
        dto.productName = this.product?.name
        dto.quantity = this.quantity
        dto.price = this.price.toString()
        dto.subtotal = this.subtotal.toString()
        return dto
    }

    fun Cart.toOrderItems(order: Order): List<OrderItem> {
        return this.items.map { cartItem ->
            val orderItem = OrderItem()
            orderItem.order = order
            orderItem.product = cartItem.product
            orderItem.quantity = cartItem.quantity
            orderItem.price = cartItem.product?.price
            orderItem.subtotal = cartItem.subtotal
            orderItem
        }
    }
}