package com.example.myapplication.data.model

data class AnimalsList(
    val animals: List<AnimalData>,
    val currentPage: Int,
    val hasNextPage: Boolean,
)

data class AnimalData(
    val id: Long,
    val url: String,
    val type: String,
    val species: String,
    val primaryBreed: String,
    val isMixedBreed: Boolean,
    val primaryColor: String,
    val age: String,
    val gender: String,
    val size: String,
    val coat: String,
    val name: String? = null,
    val description: String,
    val smallPhoto: String,
    val bigPhoto: String,
    val status: String,
    val publishedAt: String,
)
