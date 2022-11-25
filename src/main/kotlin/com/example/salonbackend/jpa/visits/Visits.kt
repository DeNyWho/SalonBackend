package com.example.salonbackend.jpa.visits

import com.example.salonbackend.jpa.event.EventSub
import com.example.salonbackend.jpa.event.Events
import com.example.salonbackend.model.vists.VisitsTiming
import lombok.NoArgsConstructor
import java.util.*
import javax.persistence.*

@NoArgsConstructor
@Entity
@Table(name = "visits")
data class Visits(

    @Id
    val id: String? = UUID.randomUUID().toString(),

    @Column(name="client", nullable = false)
    val client: String? = null,

    @Column(name="employee", nullable = false)
    val employee: String? = null,

    @OneToOne(cascade = [CascadeType.ALL])
    val event: EventSub = EventSub(),

    @OneToMany
    val timings: List<VisitsTiming> = listOf()
)