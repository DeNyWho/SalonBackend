package com.example.salonbackend.controller

import com.example.salonbackend.jpa.user.User
import com.example.salonbackend.jpa.visits.Visits
import com.example.salonbackend.model.responses.ServiceResponse
import com.example.salonbackend.repository.user.RoleRepository
import com.example.salonbackend.repository.user.UserRepository
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@RestController
@Tag(name = "User API", description = "All about users")
@RequestMapping("/api2/users/")
class UserController {

    @Autowired
    lateinit var roleRepository: RoleRepository

    @Autowired
    lateinit var userRepository: UserRepository


    @GetMapping
    fun getUsers(
        role: String,
        httpServletResponse: HttpServletResponse
    ): ServiceResponse<User> {
        return try {
            val roling = roleRepository.findByName(role)
            return ServiceResponse(
                data = listOf(
                    userRepository.findUserByRoles(roling).get()
                ),
                message = "Success",
                status = HttpStatus.OK
            )
        } catch (e: ChangeSetPersister.NotFoundException) {
            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        }
    }


}