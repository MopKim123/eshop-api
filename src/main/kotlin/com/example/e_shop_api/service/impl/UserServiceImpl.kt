package com.example.e_shop_api.service.impl

import com.example.e_shop_api.model.dto.UserDto
import com.example.e_shop_api.model.entity.User
import com.example.e_shop_api.repository.UserRepository
import com.example.e_shop_api.service.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
): UserService {

    override fun getUserById(id: Long): UserDto =
        userRepository.findById(id)
            .orElseThrow { RuntimeException("User not found") }
            .let { UserDto().apply { this.id = it.id; this.username = it.username } }

    override fun register(username: String, password: String): UserDto {
        require(userRepository.findByUsername(username) == null) { "Username already exists" }

        return User().apply {
            this.username = username
            this.password = password
        }.let(userRepository::save)
            .let { UserDto().apply { this.id = it.id; this.username = it.username } }
    }

    override fun login(username: String, password: String): UserDto {
        val user = userRepository.findByUsername(username)
            ?: throw RuntimeException("Invalid username or password")

        if (user.password != password) throw RuntimeException("Invalid username or password")

        return UserDto().apply {
            id = user.id
            this.username = user.username
        }
    }
}
