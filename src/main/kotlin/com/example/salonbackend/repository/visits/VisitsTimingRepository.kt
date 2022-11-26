package com.example.salonbackend.repository.visits

import com.example.salonbackend.model.vists.VisitsTiming
import org.springframework.data.jpa.repository.JpaRepository

interface VisitsTimingRepository: JpaRepository<VisitsTiming, String>