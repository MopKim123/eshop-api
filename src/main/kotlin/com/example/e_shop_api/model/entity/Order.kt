package com.example.e_shop_api.model.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: User? = null

    var totalAmount: BigDecimal? = null
    var status: String = "PENDING"
    var createdAt: LocalDateTime = LocalDateTime.now()

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    var items: MutableList<OrderItem> = mutableListOf()
}