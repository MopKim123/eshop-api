package com.example.e_shop_api.service

import com.example.e_shop_api.model.dto.OrderDto

interface OrderService {
    fun checkout(userId: Long): OrderDto
    fun getOrders(userId: Long): List<OrderDto>
    fun cancelOrder(orderId: Long, userId: Long): OrderDto
}