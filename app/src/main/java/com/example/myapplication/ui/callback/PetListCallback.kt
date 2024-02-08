package com.example.myapplication.ui.callback

import com.example.myapplication.data.model.AnimalData

interface PetListCallback {
    fun onPetItemClick(animalData: AnimalData)

    fun onLoadNextPage()
}