package com.example.e_shop_api.model.dto

class OrderItemDto {
    var productId: Long? = null
    var productName: String? = null
    var quantity: Int = 1
    var price: String? = null
    var subtotal: String? = null
}

class OrderDto {
    var id: Long? = null
    var userId: Long? = null
    var items: MutableList<OrderItemDto> = mutableListOf()
    var totalAmount: String? = null
    var status: String? = null
    var createdAt: String? = null
}
