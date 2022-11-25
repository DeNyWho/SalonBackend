package com.example.salonbackend.jpa.event

import lombok.NoArgsConstructor
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@NoArgsConstructor
@Entity
@Table(name = "event_sub")
data class EventSub(

    @Id
        val id: String? = UUID.randomUUID().toString(),

    @Column(name="title", nullable = false)
        val title: String? = null,

    @Column(name="price", nullable = false)
        val price: String? = null,

    @Column(name="image", nullable = false)
        val image: String? = null,

    @Column(name="employee", nullable = false)
        val employee: String? = null
)