package com.example.salonbackend.repository.event

import com.example.salonbackend.jpa.event.Events
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EventRepository: JpaRepository<Events, String> {

}