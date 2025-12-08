package com.example.e_shop_api.service.impl

import com.example.e_shop_api.model.dto.OrderDto
import com.example.e_shop_api.model.mapper.CartMapper.toOrder
import com.example.e_shop_api.model.mapper.OrderMapper.toOrderItems
import com.example.e_shop_api.model.mapper.OrderMapper.toResponse
import com.example.e_shop_api.repository.CartItemRepository
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
    private val cartItemRepository: CartItemRepository,
): OrderService {

    @Transactional
    override fun checkout(userId: Long): OrderDto =
        cartRepository.findByUserId(userId)?.takeIf { it.items.isNotEmpty() }
            ?.let { cart ->
                val order = cart.toOrder().also(orderRepository::save)

                cart.toOrderItems(order).also { orderItems ->
                    order.items.addAll(orderItems)
                    orderItemRepository.saveAll(orderItems)
                }

                // Delete cart items and clear collection
                cartItemRepository.deleteAll(cart.items)
                cart.items.clear()
                cart.apply { updatedAt = LocalDateTime.now() }
                    .also(cartRepository::save)

                order.toResponse()
            } ?: throw RuntimeException("Cart is empty")


    override fun getOrders(userId: Long): List<OrderDto> =
        orderRepository.findByUserId(userId).map { it.toResponse() }

    @Transactional
    override fun cancelOrder(orderId: Long, userId: Long): OrderDto {
        val order = orderRepository.findById(orderId)
            .orElseThrow { RuntimeException("Order not found") }
            .takeIf { it.user?.id == userId }
            ?: throw RuntimeException("Unauthorized")

        val sevenDaysAgo = LocalDateTime.now().minusDays(7)
        if (order.createdAt.isBefore(sevenDaysAgo)) {
            throw RuntimeException("Cannot cancel orders older than 7 days")
        }

        return order.apply {
            status = "CANCELLED"
            orderRepository.save(this)
        }.toResponse()
    }
}
