package com.example.salonbackend.jpa.user

import lombok.NoArgsConstructor
import java.util.*
import javax.persistence.*

@NoArgsConstructor
@Entity
@Table(name = "roles")
data class Role (

    @Id
        val id: String? = UUID.randomUUID().toString(),

    @Column(name="name", nullable = false)
        val name: String? = null
)
