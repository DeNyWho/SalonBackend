package com.example.salonbackend.controller

import com.example.salonbackend.model.requests.EventRequest
import com.example.salonbackend.model.responses.ServiceResponse
import com.example.salonbackend.service.event.EventService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletResponse


@RestController
@Tag(name = "Event API", description = "All about events")
@RequestMapping("/api2/event/")
class EventController {

    @Autowired
    lateinit var service: EventService

    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun createNewEvent(
        @RequestParam title: String,
        image: MultipartFile,
        httpServletResponse: HttpServletResponse
    ): ServiceResponse<Boolean> {
        return try {
            return ServiceResponse(
                data = listOf(service.addNewEvent(title = title, image = image)),
                message = "Event has been created",
                status = HttpStatus.OK
            )
        } catch (e: ChangeSetPersister.NotFoundException) {
            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        }
    }

    @PostMapping("/sub",consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun addSubEvent(
        id: String,
        title: List<String>,
        image: List<MultipartFile>,
        eventRequest: EventRequest,
        employee: String,
        httpServletResponse: HttpServletResponse
    ): ServiceResponse<Boolean> {
        return try {
            return ServiceResponse(
                data = listOf(service.addNewSubEvent(id = id, title = title, image = image, employee = employee)),
                message = "Event has been created",
                status = HttpStatus.OK
            )
        } catch (e: ChangeSetPersister.NotFoundException) {
            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        }
    }

    @GetMapping
    fun getEvent(
        @RequestParam id: String,
        response: HttpServletResponse
    ): ServiceResponse<Any> {
        return try {
            return ServiceResponse(
                data = listOf(service.getEvent(id)),
                message = "Success",
                status = HttpStatus.OK
            )
        } catch (e: ChangeSetPersister.NotFoundException) {
            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        }
    }

    @DeleteMapping
    fun deleteEvent(
        @RequestParam id: String,
        response: HttpServletResponse
    ): ServiceResponse<String> {
        return try {
            service.deleteEvent(id)
            return ServiceResponse(
                data = null,
                message = "Event with id = $id has been deleted",
                status = HttpStatus.OK
            )
        } catch (e: ChangeSetPersister.NotFoundException) {
            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        }
    }


}