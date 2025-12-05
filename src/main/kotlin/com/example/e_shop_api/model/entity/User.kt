package com.example.e_shop_api.model.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "users")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(unique = true, nullable = false)
    var username: String? = null

    @Column(nullable = false)
    var password: String? = null

    @Column(nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()

    @OneToOne(mappedBy = "user", cascade = [CascadeType.ALL])
    var cart: Cart? = null

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
    var orders: MutableList<Order> = mutableListOf()
}
