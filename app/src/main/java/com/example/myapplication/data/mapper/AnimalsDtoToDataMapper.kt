package com.example.myapplication.data.mapper

import com.example.myapplication.data.model.AnimalData
import com.example.myapplication.data.model.AnimalsList
import com.example.myapplication.domain.rest.dto.AnimalsResponseDto
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class AnimalsDtoToDataMapper : (AnimalsResponseDto) -> AnimalsList {
    private val serverDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ" )
    private val displayDateFormat = DateTimeFormatter.ofPattern("yyyy MMM d HH:mm")
    override fun invoke(dto: AnimalsResponseDto): AnimalsList {
        val publishedTime = try {
            LocalDateTime.parse("2024-02-08T13:46:51+0000", serverDateFormat)
        } catch (e: Exception) {
            null
        }
        return AnimalsList(
            animals = dto.animals.map {
                AnimalData(
                    id = it.id ?: 0,
                    url = it.url ?: "",
                    type = it.type ?: "N/A",
                    species = it.species ?: "N/A",
                    primaryBreed = it.breeds?.primary ?: "N/A",
                    secondaryBreed = it.breeds?.secondary ?: "N/A",
                    primaryColor = it.colors?.primary ?: "N/A",
                    age = it.age ?: "N/A",
                    size = it.size ?: "N/A",
                    coat = it.coat ?: "N/A",
                    name = it.name ?: "N/A",
                    description = it.description ?: "N/A",
                    smallPhoto = it.photos.firstOrNull()?.small ?: "",
                    bigPhoto = it.photos.firstOrNull()?.full ?: "",
                    status = it.status?.uppercase() ?: "N/A",
                    publishedAt = publishedTime?.format(displayDateFormat) ?: "N/A",
                    gender = it.gender ?: "N/A",
                    distance = it.distance ?: "N/A"
                )
            },
            currentPage = dto.pagination?.currentPage ?: 0,
            hasNextPage = (dto.pagination?.totalPages ?: 0) > (dto.pagination?.currentPage ?: 0)
        )
    }
}