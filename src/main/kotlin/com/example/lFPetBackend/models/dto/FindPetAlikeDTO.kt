package com.example.lFPetBackend.models.dto

data class FindPetAlikeDTO (
    var petName: String,
    var petType: String,
    var gender: String,
    var color: String,
    var isAdopted: Boolean,
    var lastLat: Float,
    var lastLng: Float,
    var numOfLostDays: Int
)