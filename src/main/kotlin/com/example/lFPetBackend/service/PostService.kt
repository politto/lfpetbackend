package com.example.lFPetBackend.service

import com.example.lFPetBackend.models.dto.PostWithPetsDto
import com.example.lFPetBackend.models.entities.PetInfoEntity
import com.example.lFPetBackend.models.entities.PostEntity
import com.example.lFPetBackend.repository.PostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.bind.DefaultValue
import org.springframework.stereotype.Service

@Service
class PostService {
    //service layer for post

    @Autowired
    lateinit var postRepository: PostRepository

    fun getAllPosts(): List<PostEntity> = postRepository.findAll()

    fun getSomePosts(startPostIndex: Int): List<PostEntity> = postRepository.getSomePosts(startPostIndex)

    fun getPostById(id: Long): PostEntity = postRepository.findById(id).get()

    fun getPostsByAccountId(accountId: Long, startPostIndex: Int): List<PostEntity> = postRepository.findPostsByAccountId(accountId, startPostIndex)

    fun createPost(post: PostEntity): PostEntity = postRepository.save(post)

    fun savePostWithPets(postWithPetsDto: PostWithPetsDto): PostEntity {
        val pets = postWithPetsDto.pets.map {
            PetInfoEntity(
                petName = it.petName,
                petType = it.petType,
                breed = it.breed,
                birthDate = it.birthDate,
                gender = it.gender,
                isAdopted = it.isAdopted,
                detail = it.detail,
                lastLat = it.lastLat,
                lastLng = it.lastLng,
                isLost = it.isLost,
                isDeceased = it.isDeceased,
                lastPicLink = it.lastPicLink,
                isDeleted = it.isDeleted
            )
        }

        val post = PostEntity(
            postTitle = postWithPetsDto.postTitle,
            postContent = postWithPetsDto.postContent,
            postDate = postWithPetsDto.postDate,
            postType = postWithPetsDto.postType,
            postImageLink = postWithPetsDto.postImageLink,
            postStatus = postWithPetsDto.postStatus,
            isDeleted = postWithPetsDto.isDeleted,
            petParticipated = pets
        )

        return postRepository.save(post)
    }

    fun updatePost(post: PostEntity): PostEntity = postRepository.save(post)

    fun deletePost(id: Long):Int = postRepository.setIsDeleted(id)

    fun trueDeletePost(id: Long) = postRepository.deleteById(id)
}