package com.example.myapplication.data.mapper

import com.example.myapplication.data.model.AnimalData
import com.example.myapplication.data.model.AnimalsList
import com.example.myapplication.domain.rest.dto.AnimalsResponseDto

class AnimalsDtoToDataMapper : (AnimalsResponseDto) -> AnimalsList {
    override fun invoke(dto: AnimalsResponseDto): AnimalsList {
        return AnimalsList(
            animals = dto.animals.map {
                AnimalData(
                    id = it.id ?: 0,
                    url = it.url ?: "",
                    type = it.type ?: "",
                    species = it.species ?: "",
                    primaryBreed = it.breeds?.primary ?: "",
                    isMixedBreed = it.breeds?.mixed ?: false,
                    primaryColor = it.colors?.primary ?: "",
                    age = it.age ?: "",
                    size = it.size ?: "",
                    coat = it.coat ?: "",
                    name = it.name ?: "",
                    description = it.description ?: "",
                    smallPhoto = it.photos.firstOrNull()?.small ?: "",
                    bigPhoto = it.photos.firstOrNull()?.full ?: "",
                    status = it.status ?: "",
                    publishedAt = it.publishedAt ?: "",
                    gender = it.gender ?: "",
                )
            },
            currentPage = dto.pagination?.currentPage ?: 0,
            hasNextPage = (dto.pagination?.totalPages ?: 0) > (dto.pagination?.currentPage ?: 0)
        )
    }
}