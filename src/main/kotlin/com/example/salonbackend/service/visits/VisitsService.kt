package com.example.salonbackend.service.visits

import com.example.salonbackend.jpa.event.EventSub
import com.example.salonbackend.jpa.visits.Visits
import com.example.salonbackend.model.vists.VisitsTiming
import com.example.salonbackend.repository.event.EventRepository
import com.example.salonbackend.repository.event.EventSubRepository
import com.example.salonbackend.repository.visits.VisitsRepository
import com.example.salonbackend.repository.visits.VisitsTimingRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class VisitsService {

    @Autowired
    lateinit var visitsRepository: VisitsRepository

    @Autowired
    lateinit var eventRepository: EventRepository

    @Autowired
    lateinit var eventSubRepository: EventSubRepository

    @Autowired
    lateinit var visitsTimingRepository: VisitsTimingRepository

    fun addNewVisits(employeeID: String, eventID: String): Boolean {
        return try {
            val event = eventSubRepository.findById(eventID).get()
            visitsRepository.save(
                Visits(
                    employee = employeeID,
                    event = event
                )
            )
            true
        } catch (e: Exception){
            false
        }
    }

    fun addNewSubVisits(id: String, date: String, timings: VisitsTiming): Boolean {
        return try {
            val second = visitsTimingRepository.save(
                VisitsTiming(
                    date = date
                )
            )

            val b = visitsRepository.findById(id).get().addNewVisits(
                second
            )
            visitsRepository.save(b)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getVisits(): MutableList<Visits> = visitsRepository.findAll()

















}