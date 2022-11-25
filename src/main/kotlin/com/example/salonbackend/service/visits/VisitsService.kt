package com.example.salonbackend.service.visits

import com.example.salonbackend.jpa.visits.Visits
import com.example.salonbackend.model.vists.VisitsTiming
import com.example.salonbackend.repository.event.EventRepository
import com.example.salonbackend.repository.event.EventSubRepository
import com.example.salonbackend.repository.visits.VisitsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class VisitsService {

    @Autowired
    lateinit var visitsRepository: VisitsRepository

    @Autowired
    lateinit var eventRepository: EventRepository

    @Autowired
    lateinit var eventSubRepository: EventSubRepository

    fun addNewVisits(clientID: String, employeeID: String, eventID: String, date: List<VisitsTiming>): Boolean {
        return try {
            val event = eventSubRepository.findById(eventID).get()
            visitsRepository.save(
                Visits(
                    client = clientID,
                    employee = employeeID,
                    event = event,
                    timings = date
                )
            )
            true
        } catch (e: Exception){
            false
        }
    }

















}