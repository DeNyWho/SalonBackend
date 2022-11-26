package com.example.salonbackend.jpa.event

import lombok.NoArgsConstructor
import java.util.*
import javax.persistence.*

@NoArgsConstructor
@Entity
@Table(name = "events")
data class Events (

    @Id
        val id: String? = UUID.randomUUID().toString(),

    @Column(name="title", nullable = false)
        val title: String? = null,

    @Column(name="image", nullable = false)
        val image: String? = null,

    @OneToMany(
        fetch = FetchType.EAGER,
        cascade = [CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH]
    )
    @Column(nullable = true)
        val sub: MutableSet<EventSub> = mutableSetOf()
) {
    fun addToSub(eventSub: EventSub): Events {
        sub.add(eventSub)
        return this
    }
}
