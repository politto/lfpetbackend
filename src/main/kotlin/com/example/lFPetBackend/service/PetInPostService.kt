package com.example.LFPetBackend.service

import com.example.lFPetBackend.models.dto.PostWithPetsDto
import com.example.lFPetBackend.models.entities.PetInfoEntity
import org.springframework.beans.factory.annotation.Autowired
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
            postDate = rs.getDate("post_date"),
            postType = rs.getString("post_type"),
            postImageLink = rs.getString("post_image_link"),
            postStatus = rs.getString("post_status"),
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
                    lastLat = rs.getFloat("last_lat"),
                    lastLng = rs.getFloat("last_lng"),
                    isLost = rs.getBoolean("is_lost"),
                    isDeceased = rs.getBoolean("is_deceased"),
                    lastPicLink = rs.getString("last_pic_link"),
                    isDeleted = rs.getBoolean("is_deleted")
                )
            )

        )
    }

    fun createPetInPost(petId: Long, postId: Long) {
        val sql = "INSERT INTO pet_in_post (pet_id, post_id) VALUES (?, ?)"

        jdbcTemplate.update(sql, petId, postId)

    }

    fun deletePetInPost(petId: Long, postId: Long) {
        val sql = "DELETE FROM pet_in_post WHERE pet_id = ? AND post_id = ?"

        jdbcTemplate.update(sql, petId, postId)

    }

    fun getPostIdsByPetId(petId: Long): List<Long> {
        val sql = "SELECT post_id FROM pet_in_post WHERE pet_id = ?"

        return jdbcTemplate.queryForList(sql, Long::class.java, petId)

    }

    fun getPostsWithPets(startIndex: Int, limit: Int): List<PostWithPetsDto> {
        val sql = """
            SELECT p.*, pi.*
            FROM Post p
            LEFT JOIN pet_in_post pip ON p.postId = pip.post_id
            LEFT JOIN PetInfo pi ON pip.pet_id = pi.petId
            limit ? offset ?
        """
        return jdbcTemplate.query(sql, postWithPetsRowMapper, limit, startIndex)
    }


}