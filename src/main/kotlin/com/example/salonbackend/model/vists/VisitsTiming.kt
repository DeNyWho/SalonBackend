package com.example.salonbackend.model.vists

import com.example.salonbackend.jpa.event.EventSub
import com.example.salonbackend.jpa.event.Events
import lombok.NoArgsConstructor
import java.util.*
import javax.persistence.*

@NoArgsConstructor
@Entity
@Table(name = "visiting_timings")
data class VisitsTiming(

    @Id
    val id: String? = UUID.randomUUID().toString(),
    val date: String = "",
    @OneToMany(
        fetch = FetchType.EAGER,
        cascade = [CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH]
    )
    @Column(nullable = true)
    val time: List<VisitSup> = listOf(
        VisitSup(
            time = "10:00"
        ),
        VisitSup(
            time = "10:30"
        ),
        VisitSup(
            time = "11:00"
        ),
        VisitSup(
            time = "11:30"
        ),
        VisitSup(
            time = "12:00"
        ),
        VisitSup(
            time = "12:30"
        ),
        VisitSup(
            time = "13:00"
        ),
        VisitSup(
            time = "13:30"
        ),
        VisitSup(
            time = "14:00"
        ),
        VisitSup(
            time = "14:30"
        ),
        VisitSup(
            time = "15:00"
        ),
        VisitSup(
            time = "15:30"
        ),
        VisitSup(
            time = "16:00"
        ),
        VisitSup(
            time = "16:30"
        ),
        VisitSup(
            time = "17:00"
        ),
        VisitSup(
            time = "17:30"
        ),

    ),
)