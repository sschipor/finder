package com.example.myapplication.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class AnimalsList(
    val animals: List<AnimalData> = emptyList(),
    val currentPage: Int = 1,
    val hasNextPage: Boolean = false,
)

@Parcelize
data class AnimalData(
    val id: Long = 0,
    val url: String = "",
    val species: String = "",
    val primaryBreed: String = "",
    val secondaryBreed: String = "",
    val age: String = "",
    val gender: String = "",
    val size: String = "",
    val name: String? = null,
    val description: String = "",
    val smallPhoto: String = "",
    val bigPhoto: String = "",
    val status: String = "",
    val publishedAt: String = "",
    val distance: String = "",
): Parcelable
