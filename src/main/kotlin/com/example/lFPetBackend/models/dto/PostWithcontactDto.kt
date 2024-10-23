package com.example.lFPetBackend.models.dto

import com.example.lFPetBackend.models.entities.PostEntity

data class PostWithContactDto (
    //all properties from postEntity
    val postData: PostEntity,
    val email: String = "",
    val phoneNumber: String = ""
    )