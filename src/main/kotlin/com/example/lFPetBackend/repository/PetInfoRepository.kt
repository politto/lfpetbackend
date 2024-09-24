package com.example.lFPetBackend.repository

import com.example.lFPetBackend.models.dto.FindPetAlikeDTO
import com.example.lFPetBackend.models.entities.PetInfoEntity
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PetInfoRepository : JpaRepository<PetInfoEntity, Long> {
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update PetInfoEntity p set p.isAdopted = :isAdopted where p.petId = :id")
    fun setIsAdopted(@Param("isAdopted") isAdopted: Boolean, @Param("id") id: Long): Int


    @Query("from PetInfoEntity p " +
            "where p.petName like %:findPetAlikeDto.petName% " +
            "and p.petType like %:findPetAlikeDto.petType% " +
            "and p.gender = :findPetAlikeDto.gender " +
            "and p.color like %:findPetAlikeDto.color% " +
            "and p.isAdopted = :findPetAlikeDto.isAdopted " +
            "and p.isLost = :findPetAlikeDto.isLost " +
            "and (p.lastLat < 0.05 * :findPetAlikeDto.numOfLostDays or p.lastLat > 0.05 * :findPetAlikeDto.numOfLostDays) and (p.lastLng < 0.05 * cos(:findPetAlikeDto.lastLat) * :findPetAlikeDto.numOfLostDays or p.lastLng > 0.05 * :findPetAlikeDto.numOfLostDays)")
    //                 Distance(km)=1.85×cos(latitude degrees)
    fun findPetsThatAlike(@Param("findPetAlikeDto") findPetAlikeDto: FindPetAlikeDTO): List<PetInfoEntity>



}

