package com.example.salonbackend.model.requests

import com.example.salonbackend.jpa.event.EventSub

data class EventRequest(
    val events: List<EventSub> = listOf(EventSub())
)