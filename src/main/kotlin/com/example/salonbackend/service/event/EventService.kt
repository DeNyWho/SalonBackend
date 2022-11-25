package com.example.salonbackend.service.event

import com.example.salonbackend.jpa.event.EventSub
import com.example.salonbackend.jpa.event.Events
import com.example.salonbackend.repository.event.EventRepository
import com.example.salonbackend.service.files.FileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class EventService {

    @Autowired
    lateinit var eventRepository: EventRepository

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

    fun addNewSubEvent(id: String, title: List<String>, image: List<MultipartFile>, employee: String): Boolean {
        return try {
            val events = eventRepository.findById(id).get()
            val temp = mutableListOf<EventSub>()
            title.forEachIndexed { index, s ->
                temp.add(
                    EventSub(
                        title = s,
                        image = fileService.save(image[index]),
                        employee = employee
                    )
                )
            }

            eventRepository.save(
                Events(
                    id = events.id,
                    title = events.title,
                    image = events.image,
                    sub = temp.toList()
                )
            )

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