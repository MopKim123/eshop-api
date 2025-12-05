package com.example.e_shop_api.model.dto

class ProductDto {
    var id: Long? = null
    var name: String? = null
    var description: String? = null
    var price: String? = null // BigDecimal serialized as String
    var stock: Int = 0
}

