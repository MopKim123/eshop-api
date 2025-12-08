package com.example.e_shop_api.service.impl

import com.example.e_shop_api.model.dto.CartDto
import com.example.e_shop_api.model.entity.Cart
import com.example.e_shop_api.model.mapper.CartMapper.addItem
import com.example.e_shop_api.model.mapper.CartMapper.toResponse
import com.example.e_shop_api.repository.CartItemRepository
import com.example.e_shop_api.repository.CartRepository
import com.example.e_shop_api.repository.ProductRepository
import com.example.e_shop_api.repository.UserRepository
import com.example.e_shop_api.service.CartService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
class CartServiceImpl(
    private val cartRepository: CartRepository,
    private val cartItemRepository: CartItemRepository,
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository
): CartService {

    override fun getCart(userId: Long): CartDto =
        cartRepository.findByUserId(userId)?.toResponse() ?: createCart(userId).toResponse()

    @Transactional
    override fun addToCart(userId: Long, productId: Long, quantity: Int): CartDto {
        val cart = cartRepository.findByUserId(userId) ?: createCart(userId)
        val product = productRepository.findById(productId)
            .orElseThrow { RuntimeException("Product not found") }

        cart.items.find { it.product?.id == productId }?.apply {
            this.quantity += quantity
            this.subtotal = product.price?.multiply(BigDecimal(this.quantity))
            cartItemRepository.save(this)
        } ?: cart.addItem(product, quantity).also(cartItemRepository::save)

        return cart.apply {
            updatedAt = LocalDateTime.now()
            cartRepository.save(this)
        }.toResponse()
    }

    @Transactional
    override fun updateCartItem(cartItemId: Long, quantity: Int): CartDto {
        val item = cartItemRepository.findById(cartItemId)
            .orElseThrow { RuntimeException("Cart item not found") }
            .apply {
                this.quantity = quantity
                this.subtotal = product?.price?.multiply(BigDecimal(quantity))
                cartItemRepository.save(this)
            }

        return item.cart!!.apply {
            updatedAt = LocalDateTime.now()
            cartRepository.save(this)
        }.toResponse()
    }

    @Transactional
    override fun removeCartItem(cartItemId: Long) {
        val item = cartItemRepository.findById(cartItemId)
            .orElseThrow { RuntimeException("Cart item not found") }

        val cart = item.cart ?: throw RuntimeException("Cart not found")

        // Delete the cart item
        cartItemRepository.deleteById(cartItemId)

        // Update cart timestamp
        cart.updatedAt = LocalDateTime.now()
        cartRepository.save(cart)
    }


    private fun createCart(userId: Long): Cart {
        return Cart().apply {
            user = userRepository.findById(userId)
                .orElseThrow { RuntimeException("User not found") }
        }.let(cartRepository::save)
    }
}
