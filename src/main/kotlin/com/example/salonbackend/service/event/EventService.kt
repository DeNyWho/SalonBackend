package com.example.salonbackend.service.event

import com.example.salonbackend.jpa.event.EventSub
import com.example.salonbackend.jpa.event.Events
import com.example.salonbackend.repository.event.EventRepository
import com.example.salonbackend.repository.event.EventSubRepository
import com.example.salonbackend.service.files.FileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class EventService {

    @Autowired
    lateinit var eventRepository: EventRepository

    @Autowired
    lateinit var eventSubRepository: EventSubRepository

    @Autowired
    lateinit var fileService: FileService

    fun addNewEvent(title: String, image: MultipartFile): Boolean {
        return try {
            eventRepository.save(
                Events(
                    title = title,
                    image = fileService.save(image)
                )
            )
            true
        } catch (e: Exception) {
            false
        }
    }

    fun addNewSubEvent(id: String, title: String, image: MultipartFile, employee: String): Boolean {
        return try {
            val second = eventSubRepository.save(
                EventSub(
                    id = UUID.randomUUID().toString(),
                    title = title,
                    image = fileService.save(image)
                )
            )

            val b = eventRepository.findById(id).get().addToSub(
                second
            )
            eventRepository.save(b)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getEvent(id: String): Events {
        return eventRepository.findById(id).get()
    }

    fun deleteEvent(id: String){
        eventRepository.deleteById(id)
    }
}