package com.example.salonbackend.model.responses

import com.example.salonbackend.jpa.user.Role
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.*

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserResponseDto(
    @Id
    val id: String? = UUID.randomUUID().toString(),

    @Column(name = "name")
    var name: String? = null,

    @Column(name = "secondName")
    var secondName: String? = null,

    @Column(name = "thirdName")
    var thirdName: String? = null,

    @Column(name = "email")
    var email: String? = null,

    @Column(name = "password")
    var password: String? = null,

    @Column(name = "enabled")
    var enabled: Boolean = false,

    @Column(name = "token")
    var token: String? = null,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")],
    )

    var roles: Collection<Role>? = null,

    @CreatedDate
    @Column(name = "created")
    var created: LocalDateTime? = null
)

