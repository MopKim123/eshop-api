package com.example.e_shop_api.model.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "products")
class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    var name: String? = null

    var description: String? = null

    @Column(nullable = false)
    var price: BigDecimal? = null

    var stock: Int = 0
}
