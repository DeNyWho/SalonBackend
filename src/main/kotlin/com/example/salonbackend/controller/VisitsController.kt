package com.example.salonbackend.controller

import com.example.salonbackend.model.responses.ServiceResponse
import com.example.salonbackend.model.vists.VisitsTiming
import com.example.salonbackend.service.visits.VisitsService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
@Tag(name = "Visits API", description = "All about visits")
@RequestMapping("/api2/visits/")
class VisitsController {

    @Autowired
    lateinit var service: VisitsService

    @PostMapping
    fun createNewVisits(
        clientID: String,
        employeeID: String,
        eventID: String,
        date: List<VisitsTiming>,
        httpServletResponse: HttpServletResponse
    ): ServiceResponse<Boolean> {
        return try {
            return ServiceResponse(
                data = listOf(service.addNewVisits(clientID = clientID, employeeID = employeeID, eventID = eventID, date = date)),
                message = "Event has been created",
                status = HttpStatus.OK
            )
        } catch (e: ChangeSetPersister.NotFoundException) {
            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        }
    }

    @GetMapping
    fun getVisits() {
    }


}