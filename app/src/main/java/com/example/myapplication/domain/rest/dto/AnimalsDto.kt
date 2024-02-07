package com.example.myapplication.domain.rest.dto

import com.google.gson.annotations.SerializedName

data class AnimalsResponseDto(
    @SerializedName("animals")
    val animals: List<AnimalDto> = listOf(),
    @SerializedName("pagination")
    val pagination: PaginationDto? = PaginationDto()
)

data class AnimalDto(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("organization_id") val organizationId: String? = null,
    @SerializedName("url") val url: String? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("species") val species: String? = null,
    @SerializedName("breeds") val breeds: BreedDto? = BreedDto(),
    @SerializedName("colors") var colors: ColorDto? = ColorDto(),
    @SerializedName("age") val age: String? = null,
    @SerializedName("gender") val gender: String? = null,
    @SerializedName("size") val size: String? = null,
    @SerializedName("coat") val coat: String? = null,
    @SerializedName("tags") val tags: ArrayList<String> = arrayListOf(),
    @SerializedName("name") val name: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("photos") val photos: ArrayList<PhotoDto> = arrayListOf(),
    @SerializedName("status") val status: String? = null,
    @SerializedName("published_at") val publishedAt: String? = null,
)

data class PhotoDto(
    @SerializedName("small") val small: String? = null,
    @SerializedName("medium") val medium: String? = null,
    @SerializedName("large") val large: String? = null,
    @SerializedName("full") val full: String? = null
)

data class BreedDto(
    @SerializedName("primary")
    val primary: String? = null,
    @SerializedName("secondary")
    val secondary: String? = null,
    @SerializedName("mixed")
    val mixed: Boolean? = null,
    @SerializedName("unknown")
    val unknown: Boolean? = null
)


data class ColorDto(
    @SerializedName("primary") val primary: String? = null,
    @SerializedName("secondary") val secondary: String? = null,
    @SerializedName("tertiary") val tertiary: String? = null
)

data class PaginationDto(
    @SerializedName("count_per_page") val countPerPage: Int? = null,
    @SerializedName("total_count") val totalCount: Int? = null,
    @SerializedName("current_page") val currentPage: Int? = null,
    @SerializedName("total_pages") val totalPages: Int? = null,
)