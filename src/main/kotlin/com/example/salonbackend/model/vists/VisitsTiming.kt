package com.example.salonbackend.model.vists

import lombok.NoArgsConstructor
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@NoArgsConstructor
@Entity
@Table(name = "visiting_timings")
data class VisitsTiming(

    @Id
    val id: String? = UUID.randomUUID().toString(),
    val date: String = "",
    @OneToMany
    val time: List<VisitSup> = listOf(),
)