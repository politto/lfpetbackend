package com.example.lFPetBackend.repository

import com.example.lFPetBackend.models.entities.PostEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PostRepository: JpaRepository<PostEntity, Long> {
    @Query("from PostEntity p where p.accountId = :accountId order by p.postDate desc")
    fun findPostsByAccountId(@Param("accountId") accountId: Long, pageable: Pageable): List<PostEntity>

    @Query("from PostEntity p order by p.postDate desc")
    fun getSomePosts(pageable: Pageable): List<PostEntity>

    //setIsDeleted = true
    @Query("update PostEntity p set p.isDeleted = true where p.postId = :id")
    fun setIsDeleted(@Param("id") id: Long): Int

    //find posts that posted over 2 months
    @Query("from PostEntity p where p.postDate < current_date - INTERVAL '2 months'", nativeQuery = true)
    fun findOldPosts(): List<PostEntity>

}