package com.example.salonbackend.jpa.user

import org.hibernate.Hibernate
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "users")
data class User (

    @Id
        val id: String? = UUID.randomUUID().toString(),

    @Column(name="username")
        var username: String? = null,

    @Column(name="name")
        var name: String? = null,

    @Column(name="second_name")
        var secondName: String? = null,

    @Column(name="third_name")
        var thirdName: String? = null,

    @Column(name="email")
        var email: String? = null,

    @Column(name="password")
        var password: String? = null,

    @Column(name="token")
        var token: String? = null,

    @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(
                name = "users_roles",
                joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
        )
        var roles: Collection<Role>? = null,

    @CreatedDate
        @Column(name = "created")
        var created: LocalDateTime? = null
){

        override fun equals(other: Any?): Boolean {
                if (this === other) {
                        return true
                }
                if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) {
                        return false
                }
                val user = other as User
                return id != null && id == user.id
        }

        override fun hashCode(): Int {
                return javaClass.hashCode()
        }
}
