package com.example.lFPetBackend.repository

import com.example.lFPetBackend.models.entities.PostEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PostRepository: JpaRepository<PostEntity, Long> {
    @Query("from PostEntity p where p.account.accountId = :accountId order by p.postDate desc limit 20 offset :startIdx")
    fun findPostsByAccountId(@Param("accountId") accountId: Long, @Param("startIdx") startIdx: Int): List<PostEntity>

    @Query("from PostEntity p order by p.postDate desc limit 20 offset :startIdx")
    fun getSomePosts(@Param("startIdx") startIdx: Int): List<PostEntity>

    //setIsDeleted = true
    @Query("update PostEntity p set p.isDeleted = true where p.postId = :id")
    fun setIsDeleted(@Param("id") id: Long): Int

}