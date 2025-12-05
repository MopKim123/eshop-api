package com.example.e_shop_api.model.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "cart_items")
class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    var cart: Cart? = null

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    var product: Product? = null

    var quantity: Int = 1
    var subtotal: BigDecimal? = null
}