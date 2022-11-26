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

    @Column(name="employee", nullable = false)
    val employee: String? = null,

    @OneToOne(cascade = [CascadeType.ALL])
    val event: EventSub = EventSub(),

    @OneToMany(
        fetch = FetchType.EAGER,
        cascade = [CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH]
    )
    @Column(nullable = true)
    val timings: MutableSet<VisitsTiming> = mutableSetOf()
) {
    fun addNewVisits(timingsZxc: VisitsTiming): Visits {
        timings.add(timingsZxc)
        return this
    }
}

