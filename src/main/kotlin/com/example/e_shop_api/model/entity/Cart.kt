package com.example.e_shop_api.model.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "carts")
class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    var user: User? = null

    var updatedAt: LocalDateTime = LocalDateTime.now()

    @OneToMany(mappedBy = "cart", cascade = [CascadeType.ALL])
    var items: MutableList<CartItem> = mutableListOf()
}