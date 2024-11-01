package com.example.LFPetBackend.service

import com.example.lFPetBackend.models.dto.PostWithPetsDto
import com.example.lFPetBackend.models.entities.PetInfoEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Service

@Service
class PetInPostService {

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    private val postWithPetsRowMapper = RowMapper { rs, _ ->
        PostWithPetsDto(
            postId = rs.getLong("post_id"),
            postTitle = rs.getString("post_title"),
            postContent = rs.getString("post_content"),
            postDate = rs.getDate("post_date") ?: java.sql.Date(0),
            accountId = rs.getLong("account_id"),
            postType = rs.getString("post_type"),
            postImageLink = rs.getString("post_image") ?: "",
            postStatus = rs.getString("post_status") ?: "",
            isDeleted = rs.getBoolean("is_deleted"),
            pets = listOf(
                PetInfoEntity(
                    petId = rs.getLong("pet_id"),
                    petName = rs.getString("pet_name"),
                    petType = rs.getString("pet_type"),
                    breed = rs.getString("breed"),
                    birthDate = rs.getDate("birth_date"),
                    gender = rs.getString("gender"),
                    isAdopted = rs.getBoolean("is_adopted"),
                    detail = rs.getString("detail"),
                    lastLat = rs.getFloat("last_lat")  ?: 100.0f,
                    lastLng = rs.getFloat("last_lng") ?: 13.0f,
                    isLost = rs.getBoolean("is_lost"),
                    isDeceased = rs.getBoolean("is_deceased") ?: false,
                    lastPicLink = rs.getString("last_pic_link") ?: "",
                    isDeleted = rs.getBoolean("is_deleted")
                )
            )

        )
    }

    fun createPetInPost(petId: Long, postId: Long): String {
        println("enter sql query...")
        val sql = "INSERT INTO pets_participation_in_posts (pet_id, post_id) VALUES (?, ?)"

        jdbcTemplate.update(sql, petId, postId)
        return "OK"

    }

    fun deletePetInPost(petId: Long, postId: Long) {
        val sql = "DELETE FROM pets_participation_in_posts WHERE pet_id = ? AND post_id = ?"

        jdbcTemplate.update(sql, petId, postId)

    }

    fun getPostIdsByPetId(petId: Long): List<Long> {
        val sql = "SELECT post_id FROM pets_participation_in_posts WHERE pet_id = ?"

        return jdbcTemplate.queryForList(sql, Long::class.java, petId)

    }

    fun getPostsWithPets(pageable: PageRequest): List<PostWithPetsDto> {
        val postsSql = """
        SELECT p.*
        FROM Post p
        LIMIT ? OFFSET ?
    """
        val posts: MutableList<PostWithPetsDto> = jdbcTemplate.query(postsSql, RowMapper { rs, _ ->
            PostWithPetsDto(
                postId = rs.getLong("post_id"),
                postTitle = rs.getString("post_title"),
                postContent = rs.getString("post_content"),
                postDate = rs.getDate("post_date") ?: java.sql.Date(0),
                accountId = rs.getLong("account_id"),
                postType = rs.getString("post_type"),
                postImageLink = rs.getString("post_image") ?: "",
                postStatus = rs.getString("post_status") ?: "",
                isDeleted = rs.getBoolean("is_deleted"),
                pets = mutableListOf()
            )
        }, pageable.pageSize, pageable.offset)

        // Step 2: Fetch pets for each post and group them
        val postIds = posts.map { it.postId }
        if (postIds.isNotEmpty()) {
            val petsSql = """
            SELECT pip.post_id, pi.*
            FROM pets_participation_in_posts pip
            LEFT JOIN pet_info pi ON pip.pet_id = pi.pet_id
            WHERE pip.post_id IN (${postIds.joinToString(",")})
        """
            val pets = jdbcTemplate.query(petsSql, RowMapper { rs, _ ->
                rs.getLong("post_id") to PetInfoEntity(
                    petId = rs.getLong("pet_id"),
                    petName = rs.getString("pet_name"),
                    petType = rs.getString("pet_type"),
                    breed = rs.getString("breed"),
                    birthDate = rs.getDate("birth_date"),
                    gender = rs.getString("gender"),
                    isAdopted = rs.getBoolean("is_adopted"),
                    detail = rs.getString("detail"),
                    lastLat = rs.getFloat("last_lat") ?: 100.0f,
                    lastLng = rs.getFloat("last_lng") ?: 13.0f,
                    isLost = rs.getBoolean("is_lost"),
                    isDeceased = rs.getBoolean("is_deceased") ?: false,
                    lastPicLink = rs.getString("last_pic_link") ?: "",
                    isDeleted = rs.getBoolean("is_deleted")
                )
            })

            // Group pets by postId
            val petsByPostId = pets.groupBy({ it.first }, { it.second })
            posts.forEach { post ->
                post.pets = petsByPostId[post.postId] ?: emptyList()
            }
        }

        return posts
    }


}