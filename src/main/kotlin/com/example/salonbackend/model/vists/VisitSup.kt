package com.example.salonbackend.model.vists

import lombok.NoArgsConstructor
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@NoArgsConstructor
@Entity
@Table(name = "visiting_sup")
data class VisitSup(

    @Id
    val id: String? = UUID.randomUUID().toString(),
    @Column(name="time", nullable = false)
    val time: String = "",
    @Column(name="client", nullable = true)
    val clientId: String = ""
)