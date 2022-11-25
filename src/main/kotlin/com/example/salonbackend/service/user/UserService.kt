package com.example.salonbackend.service.user

import com.example.salonbackend.repository.user.UserDetailsService
import com.example.salonbackend.repository.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors

@Service
class UserService: UserDetailsService {
    @Autowired
    lateinit var userRepository: UserRepository


    override fun changeUserPassword (email: String, password: String) {
        val user = userRepository.findByEmail(email).get()
        user.password = password

        userRepository.save(user)

    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username).get()

        val authorities: List<GrantedAuthority> = user.roles!!.stream().map { role -> SimpleGrantedAuthority(role.name) }
            .collect(
                Collectors.toList<GrantedAuthority>())

        return org.springframework.security.core.userdetails.User
            .withUsername(username)
            .password(user.password)
            .authorities(authorities)
            .accountExpired(false)
            .accountLocked(false)
            .credentialsExpired(false)
            .disabled(false)
            .build()
    }

    companion object {
        const val TOKEN_VALID: String = "valid"
        const val TOKEN_INVALID: String = "invalid"
        const val TOKEN_EXPIRED: String = "expired"
    }
}