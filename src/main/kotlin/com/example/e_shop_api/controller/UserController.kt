package com.example.e_shop_api.controller

import com.example.e_shop_api.model.dto.UserDto
import com.example.e_shop_api.model.dto.UserLoginDto
import com.example.e_shop_api.model.dto.UserRegisterDto
import com.example.e_shop_api.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {

    @PostMapping("/register")
    fun register(@RequestBody dto: UserRegisterDto): ResponseEntity<UserDto> {
        val user = userService.register(dto.username!!, dto.password!!)
        return ResponseEntity.ok(user)
    }

    @PostMapping("/login")
    fun login(@RequestBody dto: UserLoginDto): ResponseEntity<UserDto> {
        val user = userService.login(dto.username!!, dto.password!!)
        return ResponseEntity.ok(user)
    }

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long): ResponseEntity<UserDto> {
        val user = userService.getUserById(id)
        return ResponseEntity.ok(user)
    }
}
