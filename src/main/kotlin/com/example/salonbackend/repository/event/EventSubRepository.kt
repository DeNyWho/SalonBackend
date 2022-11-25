package com.example.salonbackend.repository.event

import com.example.salonbackend.jpa.event.EventSub
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EventSubRepository: JpaRepository<EventSub, String> {
}