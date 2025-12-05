package com.example.e_shop_api.service

import com.example.e_shop_api.model.dto.UserDto

interface UserService {
    fun getUserById(id: Long): UserDto
    fun register(username: String, password: String): UserDto
}