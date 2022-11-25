package com.example.salonbackend.repository.user

import org.springframework.security.core.userdetails.UserDetailsService

interface UserDetailsService: UserDetailsService {
    fun changeUserPassword(email: String, password: String)
}