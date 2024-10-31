package com.example.lFPetBackend.service

import com.example.lFPetBackend.models.entities.PetInfoEntity
import com.example.lFPetBackend.models.entities.PetOwnershipCompoId
import com.example.lFPetBackend.models.entities.PetOwnershipEntity
import com.example.lFPetBackend.repository.AccountRepository
import com.example.lFPetBackend.repository.PetInfoRepository
import com.example.lFPetBackend.repository.PetOwnershipRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Service

@Service
class PetOwnershipService {

    lateinit var petOwnerShipRepository: PetOwnershipRepository
    lateinit var accountRepository: AccountRepository
    lateinit var petInfoRepository: PetInfoRepository

    fun getPetsByAccountId(accountId: Long): List<PetInfoEntity> {
        val petIds = petOwnerShipRepository.findAllPetsByAccountId(accountId).map { it.id!!.petId }
        return petInfoRepository.findAllById(petIds)

    }

    fun getCurrentPetsByAccountId(accountId: Long): List<PetInfoEntity> {
        val petIds = petOwnerShipRepository.findAllPetsByAccountId(accountId).filter { it.adoptedTo == null }.map { it.id!!.petId }
        return petInfoRepository.findAllById(petIds)

    }

    fun createPetOwnership(petOwnershipEntity: PetOwnershipEntity): PetOwnershipEntity {
        val petOwnership: PetOwnershipEntity = petOwnerShipRepository.save(petOwnershipEntity)
        return petOwnershipEntity
    }

    //delete
    fun deletePetOwnership(accountId: Long, petId: Long) {
        val petOwnershipCompoId = PetOwnershipCompoId(accountId = accountId, petId = petId)
        petOwnerShipRepository.deleteById(petOwnershipCompoId)
    }




    // implement some function support home migration (future work)





}