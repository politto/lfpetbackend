package com.example.lFPetBackend.service

import com.example.lFPetBackend.models.entities.PostEntity
import com.example.lFPetBackend.repository.PostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PostService {
    //service layer for post

    @Autowired
    lateinit var postRepository: PostRepository

    fun getAllPosts() = postRepository.findAll()

    fun getPostById(id: Long) = postRepository.findById(id)

    fun createPost(post: PostEntity) = postRepository.save(post)

    fun updatePost(post: PostEntity) = postRepository.save(post)

    fun deletePost(id: Long) = postRepository.deleteById(id)
}