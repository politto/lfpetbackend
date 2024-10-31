package com.example.lFPetBackend.service

import com.example.LFPetBackend.service.PetInPostService
import com.example.lFPetBackend.models.dto.PostWithPetsDto
import com.example.lFPetBackend.models.entities.PetInfoEntity
import com.example.lFPetBackend.models.entities.PostEntity
import com.example.lFPetBackend.repository.PostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class PostService {
    //service layer for post

    @Autowired
    lateinit var postRepository: PostRepository

    @Autowired
    lateinit var petInfoService: PetInfoService

    @Autowired
    lateinit var petOwnershipService: PetOwnershipService

    @Autowired
    lateinit var petInPostService: PetInPostService

    @Autowired
    lateinit var imageUploaderToCloud: ImageUploaderToCloud

    fun getAllPosts(): List<PostEntity> = postRepository.findAll()

    fun getSomePosts(startPostIndex: Int): List<PostWithPetsDto> {
        val pageable = PageRequest.of(startPostIndex / 20, 20)
        return petInPostService.getPostsWithPets(pageable)
    }

    fun getPostById(id: Long): PostEntity {

        return postRepository.findById(id).get()
    }

    fun getPostsByAccountId(accountId: Long, startPostIndex: Int): List<PostEntity>{
        val pageable = PageRequest.of(startPostIndex / 20, 20)
        return postRepository.findPostsByAccountId(accountId, pageable)
    }

    fun createPost(post: PostEntity): PostEntity = postRepository.save(post)

    fun createPostWithPets(postWithPetsDto: PostWithPetsDto): PostEntity {
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
            isDeleted = postWithPetsDto.isDeleted
        )

        for (pet in pets) {
            petInfoService.createPetInfo(pet)
        }

        //random prob 0.02
        if (Math.random() < 0.02) {
            //search for posts that have posted over 2 months
            val oldPosts = postRepository.findOldPosts()
            //iterate over oldPosts and call trueDeletePost
            for (oldPost in oldPosts) {
                imageUploaderToCloud.deleteImageFromCloud(oldPost.postImageLink!!)
                postRepository.delete(oldPost)
            }
        }

        return postRepository.save(post)
    }

    fun updatePost(post: PostEntity): PostEntity = postRepository.save(post)

    fun deletePost(id: Long):Int = postRepository.setIsDeleted(id)

    fun trueDeletePost(id: Long) = postRepository.deleteById(id)
}