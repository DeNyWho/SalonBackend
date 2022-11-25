package com.example.salonbackend.controller

import com.example.salonbackend.repository.user.RoleRepository
import com.example.salonbackend.repository.user.UserRepository
import com.example.salonbackend.jpa.user.User
import com.example.salonbackend.jwt.JwtProvider
import com.example.salonbackend.model.responses.ServiceResponse
import com.example.salonbackend.model.responses.UserResponseDto
import com.example.salonbackend.model.user.LoginUser
import com.example.salonbackend.model.user.NewUser
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

@RestController
@Tag(name = "Authorization API", description = "All about user authorization")
@RequestMapping("/api2/auth/")
class AuthController {

    @Value("\${afa.app.authCookieName}")
    lateinit var authCookieName: String

    @Value("\${afa.app.isCookieSecure}")
    var isCookieSecure: Boolean = true

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var roleRepository: RoleRepository

    @Autowired
    lateinit var encoder: PasswordEncoder

    @Autowired
    lateinit var jwtProvider: JwtProvider


    @PostMapping("/signin")
    fun authenticateUser(
        @Valid @RequestBody loginRequest: LoginUser,
        response: HttpServletResponse
    ): ServiceResponse<UserResponseDto?> {

        val userCandidate: Optional<User> = userRepository.findByEmail(loginRequest.email!!)

        if (userCandidate.isPresent) {
            val user: User = userCandidate.get()
            val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(user.username, loginRequest.password)

            if(!usernamePasswordAuthenticationToken.isAuthenticated) {

                try {
                    authenticationManager.authenticate(usernamePasswordAuthenticationToken)
                } catch (e: Exception){
                    if(e.message.toString() == "Bad credentials"){
                        return ServiceResponse(
                            data = null,
                            status = HttpStatus.OK,
                            message = "Wrong login or password"
                        )
                    }
                }
                val auth = authenticationManager.authenticate(usernamePasswordAuthenticationToken)
                if(auth.isAuthenticated) {
                    SecurityContextHolder.getContext().authentication = auth
                    val jwt: String = jwtProvider.generateJwtToken(username = user.username!!)

                    val cookie = Cookie(authCookieName, jwt)
                    cookie.maxAge = jwtProvider.jwtExpiration!!
                    cookie.secure = isCookieSecure
                    cookie.isHttpOnly = true
                    cookie.path = "/"
                    response.addCookie(cookie)

//            val authorities: List<GrantedAuthority> =
//                user.roles!!.stream().map { role -> SimpleGrantedAuthority(role.name) }
//                    .collect(
//                        Collectors.toList<GrantedAuthority>()
//                    )

                    return ServiceResponse(
                        data = listOf(
                            UserResponseDto(
                                id = user.id,
                                name = user.name,
                                secondName = user.secondName,
                                thirdName = user.thirdName,
                                email = user.email,
                                password = user.password,
                                token = user.token,
                                roles = user.roles,
                                created = user.created,
                            )
                        ),
                        status = HttpStatus.OK,
                        message = cookie.value
                    )
                }
                else {
                    return ServiceResponse(
                        data = null,
                        status = HttpStatus.BAD_REQUEST,
                        message = "Smth going wrong"
                    )
                }
            } else {
                return ServiceResponse(
                    data = null,
                    status = HttpStatus.OK,
                    message = "User have auth"
                )
            }
        } else {
            return ServiceResponse(
                data = null,
                status = HttpStatus.BAD_REQUEST,
                message = "Wrong login or password"
            )
        }
    }

    @PostMapping("/findUserByToken")
    fun findUserByToken(@RequestParam token: String): ServiceResponse<UserResponseDto> {
        val user = userRepository.findByToken(token).get()
        return ServiceResponse(
            data = listOf(
                UserResponseDto(
                    id = user.id,
                    name = user.name,
                    secondName = user.secondName,
                    thirdName = user.thirdName,
                    email = user.email,
                    password = user.password,
                    token = user.token,
                    roles = user.roles,
                    created = user.created
                )
            ), status = HttpStatus.OK
        )
    }

    @PostMapping("/findUserByUserName")
    fun findUserByUserName(@RequestParam name: String): ServiceResponse<Boolean> {
        val userCandidate: Optional<User> = userRepository.findByUsername(name)
        return if (!userCandidate.isPresent) {
            ServiceResponse(
                data = listOf(true),
                status = HttpStatus.OK,
                message = "Everything is fine"
            )
        } else ServiceResponse(
            data = listOf(false),
            status = HttpStatus.BAD_REQUEST,
            message = "This username already exists!"
        )
    }


    @PostMapping("/signup")
    fun registerUser(@Valid @RequestBody newUser: NewUser): ServiceResponse<UserResponseDto?> {

        val userCandidate: Optional<User> = userRepository.findByEmail(newUser.email!!)
        if (emailExists(newUser.email!!)) {
            return ServiceResponse(
                data = null,
                status = HttpStatus.BAD_REQUEST,
                message = "This email already exists!"
            )
        } else if (!userCandidate.isPresent) {
            val token = UUID.randomUUID().toString()
            var user: User = User()
            try {

                user = User(
                    name = newUser.name,
                    username = "${user.name} ${user.secondName} ${user.thirdName}",
                    secondName = newUser.secondName,
                    thirdName = newUser.thirdName,
                    email = newUser.email!!,
                    password = encoder.encode(newUser.password),
                    token = token,
                    created = LocalDateTime.now()
                )

                user.roles = listOf(roleRepository.findByName(newUser.role!!))

                userRepository.save(user)

            } catch (e: Exception) {
                return ServiceResponse(
                    data = null,
                    status = HttpStatus.SERVICE_UNAVAILABLE,
                    message = "${e.message}"
                )
            }

            return ServiceResponse(
                data = listOf(
                    UserResponseDto(
                        id = user.id,
                        name = user.name,
                        secondName = user.secondName,
                        thirdName = user.thirdName,
                        email = user.email,
                        password = user.password,
                        token = user.token,
                        roles = user.roles,
                        created = user.created,
                    )
                ),
                status = HttpStatus.OK,
                message = "Registration completed!"
            )
        } else {
            return ServiceResponse(
                data = null,
                status = HttpStatus.BAD_REQUEST,
                message = "This email already exists!"
            )
        }
    }

    private fun emailExists(email: String): Boolean {
        return userRepository.findByEmail(email).isPresent
    }

}
