package com.example.e_shop_api.controller

import com.example.e_shop_api.model.dto.OrderDto
import com.example.e_shop_api.service.OrderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/orders")
class OrderController(
    private val orderService: OrderService
) {

    @PostMapping("/checkout/{userId}")
    fun checkout(@PathVariable userId: Long): ResponseEntity<OrderDto> {
        return ResponseEntity.ok(orderService.checkout(userId))
    }

    @GetMapping("/user/{userId}")
    fun getOrders(@PathVariable userId: Long): ResponseEntity<List<OrderDto>> {
        return ResponseEntity.ok(orderService.getOrders(userId))
    }

    @PostMapping("/{orderId}/cancel/{userId}")
    fun cancelOrder(@PathVariable orderId: Long, @PathVariable userId: Long): ResponseEntity<OrderDto> {
        return ResponseEntity.ok(orderService.cancelOrder(orderId, userId))
    }
}
