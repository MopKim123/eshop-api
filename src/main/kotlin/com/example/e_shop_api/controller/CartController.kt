package com.example.e_shop_api.controller

import com.example.e_shop_api.model.dto.CartDto
import com.example.e_shop_api.service.CartService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/cart")
class CartController(
    private val cartService: CartService
) {

    @GetMapping("/{userId}")
    fun getCart(@PathVariable userId: Long): ResponseEntity<CartDto> {
        return ResponseEntity.ok(cartService.getCart(userId))
    }

    @PostMapping("/{userId}/add")
    fun addToCart(
        @PathVariable userId: Long,
        @RequestParam productId: Long,
        @RequestParam quantity: Int
    ): ResponseEntity<CartDto> {
        return ResponseEntity.ok(cartService.addToCart(userId, productId, quantity))
    }

    @PutMapping("/item/{cartItemId}")
    fun updateCartItem(
        @PathVariable cartItemId: Long,
        @RequestParam quantity: Int
    ): ResponseEntity<CartDto> {
        return ResponseEntity.ok(cartService.updateCartItem(cartItemId, quantity))
    }

    @DeleteMapping("/item/{cartItemId}")
    fun removeCartItem(@PathVariable cartItemId: Long): ResponseEntity<Void> {
        cartService.removeCartItem(cartItemId)
        return ResponseEntity.noContent().build()
    }
}
