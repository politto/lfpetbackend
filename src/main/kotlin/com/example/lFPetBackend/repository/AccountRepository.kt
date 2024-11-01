package com.example.lFPetBackend.repository

import com.example.lFPetBackend.models.entities.AccountEntity
import com.example.lFPetBackend.models.entities.PetOwnershipEntity
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : JpaRepository<AccountEntity, Long> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update AccountEntity a set a.isDeleted = :isDeleted where a.id = :id")
    fun setIsDeleted(@Param("isDeleted") isDeleted: Boolean, @Param("id") id: Long): Int

    @Query("from AccountEntity a where a.accountName = :accName")
    fun findByAccName(@Param("accName") accName: String): AccountEntity?

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update AccountEntity a set a.sessionToken = :sessionToken where a.id = :id")
    fun setSessionToken(@Param("sessionToken") sessionToken: String, @Param("id") id: Long): Int

//    @Query("Select pets from AccountEntity a where a.id = :accId and (Select accountId from PetOwnershipEntity p where p.accountId = a.id)")
//    fun findAllPresentOwnedPet(@Param("accId") accId: Long): List<PetOwnershipEntity>


}