package com.example.e_shop_api.model.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "order_items")
class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    var order: Order? = null

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    var product: Product? = null

    var quantity: Int = 1
    var price: BigDecimal? = null
    var subtotal: BigDecimal? = null
}