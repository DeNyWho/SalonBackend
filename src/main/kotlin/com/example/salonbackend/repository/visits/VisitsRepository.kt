package com.example.salonbackend.repository.visits

import com.example.salonbackend.jpa.visits.Visits
import org.springframework.data.jpa.repository.JpaRepository

interface VisitsRepository: JpaRepository<Visits, String> {
}