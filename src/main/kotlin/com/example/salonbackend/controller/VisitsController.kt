package com.example.salonbackend.controller

import com.example.salonbackend.jpa.visits.Visits
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
        employeeID: String,
        eventID: String,
        date: List<VisitsTiming>,
        httpServletResponse: HttpServletResponse
    ): ServiceResponse<Boolean> {
        return try {
            return ServiceResponse(
                data = listOf(service.addNewVisits(employeeID = employeeID, eventID = eventID)),
                message = "Visits has been created",
                status = HttpStatus.OK
            )
        } catch (e: ChangeSetPersister.NotFoundException) {
            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        }
    }

    @PostMapping("sub")
    fun addNewSubVisit(
        id: String,
        date: String,
        timings: VisitsTiming,
        httpServletResponse: HttpServletResponse
    ): ServiceResponse<Boolean> {
        return try {
            return ServiceResponse(
                data = listOf(service.addNewSubVisits(id, date, timings) ),
                message = "SubVisit has been created",
                status = HttpStatus.OK
            )
        } catch (e: ChangeSetPersister.NotFoundException) {
            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        }
    }

    @GetMapping
    fun getVisits(): ServiceResponse<Visits> {
        return try {
            return ServiceResponse(
                data = service.getVisits(),
                message = "Success",
                status = HttpStatus.OK
            )
        } catch (e: ChangeSetPersister.NotFoundException) {
            ServiceResponse(status = HttpStatus.NOT_FOUND, message = e.message!!)
        }
    }


}