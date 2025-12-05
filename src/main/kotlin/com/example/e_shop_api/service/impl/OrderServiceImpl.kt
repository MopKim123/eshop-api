package com.example.e_shop_api.service.impl

import com.example.e_shop_api.model.dto.OrderDto
import com.example.e_shop_api.model.mapper.CartMapper.toOrder
import com.example.e_shop_api.model.mapper.OrderMapper.toOrderItems
import com.example.e_shop_api.model.mapper.OrderMapper.toResponse
import com.example.e_shop_api.repository.CartRepository
import com.example.e_shop_api.repository.OrderItemRepository
import com.example.e_shop_api.repository.OrderRepository
import com.example.e_shop_api.service.OrderService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class OrderServiceImpl(
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository,
): OrderService {

    @Transactional
    override fun checkout(userId: Long): OrderDto {
        val cart = cartRepository.findByUserId(userId)?.takeIf { it.items.isNotEmpty() }
            ?: throw RuntimeException("Cart is empty")

        val order = cart.toOrder().also(orderRepository::save)

        cart.toOrderItems(order).also { orderItems ->
            order.items.addAll(orderItems)
            orderItemRepository.saveAll(orderItems)
        }

        cart.items.clear()
        cartRepository.save(cart)

        return order.toResponse()
    }

    override fun getOrders(userId: Long): List<OrderDto> =
        orderRepository.findByUserId(userId).map { it.toResponse() }

    @Transactional
    override fun cancelOrder(orderId: Long, userId: Long): OrderDto {
        val order = orderRepository.findById(orderId)
            .orElseThrow { RuntimeException("Order not found") }
            .takeIf { it.user?.id == userId }
            ?: throw RuntimeException("Unauthorized")

        if (order.createdAt.isAfter(LocalDateTime.now().minusDays(7)))
            throw RuntimeException("Cannot cancel order within 7 days")

        return order.apply {
            status = "CANCELLED"
            orderRepository.save(this)
        }.toResponse()
    }
}
