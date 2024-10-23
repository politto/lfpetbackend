package com.example.lFPetBackend.models.dto

import com.example.lFPetBackend.models.entities.PetInfoEntity

data class PostDTO (
    //like PostEntity but account is a number instead of Accountentity
    var postId: Long? = null,
    var postTitle: String? = null,
    var postContent: String? = null,
    var postDate: String? = null,
    var accountId: Long? = null,
    var postType: String? = null,
    var postImageLink: String? = null,
    var postStatus: String? = null,
    var isDeleted: Boolean = false,
    var petParticipated: List<PetInfoEntity> = listOf()

)