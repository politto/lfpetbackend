package com.example.lFPetBackend.models.dto

import com.example.lFPetBackend.models.entities.PetInfoEntity
import java.util.*

data class PostWithPetsDto(
    val postId: Long,
    val postTitle: String,
    val postContent: String,
    val postDate: Date,
    val postType: String,
    val postImageLink: String,
    val postStatus: String,
    val isDeleted: Boolean,
    val accountId: Long,
    val pets: List<PetInfoEntity>
)

data class PetDto(
    val petName: String,
    val petType: String,
    val breed: String,
    val birthDate: Date,
    val gender: String,
    val isAdopted: Boolean,
    val detail: String,
    val lastLat: Float,
    val lastLng: Float,
    val isLost: Boolean,
    val isDeceased: Boolean,
    val lastPicLink: String,
    val isDeleted: Boolean
)