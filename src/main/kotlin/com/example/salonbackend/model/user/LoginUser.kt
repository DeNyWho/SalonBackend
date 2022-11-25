package com.example.salonbackend.model.user

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

class LoginUser : Serializable {

    @JsonProperty("email")
    var email: String? = null

    @JsonProperty("password")
    var password: String? = null
}