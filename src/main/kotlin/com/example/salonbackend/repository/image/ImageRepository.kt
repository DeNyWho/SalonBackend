package com.example.salonbackend.repository.image

import com.example.salonbackend.jpa.image.Image
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ImageRepository: JpaRepository<Image, String> {
}