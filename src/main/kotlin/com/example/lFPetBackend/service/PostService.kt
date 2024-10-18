package com.example.lFPetBackend.service

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

    fun updatePost(post: PostEntity): PostEntity = postRepository.save(post)

    fun deletePost(id: Long):Int = postRepository.setIsDeleted(id)

    fun trueDeletePost(id: Long) = postRepository.deleteById(id)
}