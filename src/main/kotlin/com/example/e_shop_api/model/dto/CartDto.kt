package com.example.e_shop_api.model.dto

class CartItemDto {
    var productId: Long? = null
    var productName: String? = null
    var quantity: Int = 1
    var price: String? = null
    var subtotal: String? = null
}

class CartDto {
    var id: Long? = null
    var userId: Long? = null
    var items: MutableList<CartItemDto> = mutableListOf()
    var totalAmount: String? = null
}
