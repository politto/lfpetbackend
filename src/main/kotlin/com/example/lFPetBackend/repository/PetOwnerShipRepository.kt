package com.example.lFPetBackend.repository

import com.example.lFPetBackend.models.entities.PetOwnershipCompoId
import com.example.lFPetBackend.models.entities.PetOwnershipEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PetOwnershipRepository: JpaRepository<PetOwnershipEntity, PetOwnershipCompoId> {

    @Query("from PetOwnershipEntity p where p.id = :id")
    fun findMyCompoId(@Param("id") id: Long): PetOwnershipEntity;

    @Query("from PetOwnershipEntity p where p.id.accountId = :accountId")
    fun findAllPetsByAccountId(@Param("accountId") accountId: Long): List<PetOwnershipEntity>

    @Query("from PetOwnershipEntity p where p.id.petId = :petId")
    fun findAllOwnersByPetId(@Param("petId") petId: Long): List<PetOwnershipEntity>

    //logical delete
//    @Query("update PetOwnershipEntity p set p.isDeleted = true where p.id = :id")
//    fun setIsDeleted(@Param("id") id: Long): Int

}