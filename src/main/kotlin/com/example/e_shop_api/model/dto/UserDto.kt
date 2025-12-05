package com.example.e_shop_api.model.dto

class UserDto {
    var id: Long? = null
    var username: String? = null
}

class UserRegisterDto {
    var username: String? = null
    var password: String? = null
}

class UserLoginDto {
    var username: String? = null
    var password: String? = null
}
