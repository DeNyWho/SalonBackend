package com.example.salonbackend.repository.user

import com.example.salonbackend.jpa.user.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param

interface RoleRepository : JpaRepository<Role, Long> {

    fun findByName(@Param("name") name: String): Role
}