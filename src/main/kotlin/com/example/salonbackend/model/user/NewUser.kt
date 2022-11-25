package com.example.salonbackend.model.user

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

class NewUser : Serializable {

    @JsonProperty("name")
    var name: String? = null

    @JsonProperty("name")
    var secondName: String? = null

    @JsonProperty("name")
    var thirdName: String? = null

    @JsonProperty("email")
    var email: String? = null

    @JsonProperty("password")
    var password: String? = null

    @JsonProperty("role")
    var role: String? = null
}