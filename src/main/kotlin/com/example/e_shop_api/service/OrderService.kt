package com.example.e_shop_api.service

import com.example.e_shop_api.model.dto.OrderDto

interface OrderService {
    @Transactional
    fun checkout(userId: Long): OrderDto
    fun getOrders(userId: Long): List<OrderDto>
    @Transactional
    fun cancelOrder(orderId: Long, userId: Long): OrderDto
    @Transactional
    fun cancelOrder(orderId: Long, userId: Long): OrderDto
}