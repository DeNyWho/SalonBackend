package com.example.salonbackend.repository.user

import com.example.salonbackend.jpa.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface UserRepository: JpaRepository<User, Long> {

    fun findByUsername(@Param("username") username: String): Optional<User>


    @Query(value = "SELECT u FROM User u where u.token = :token")
    fun findByToken(@Param("token") token: String): Optional<User>

    @Query(value = "SELECT u FROM User u where u.email = :email")
    fun findByEmail(@Param("email") email: String): Optional<User>


}