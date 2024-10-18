package com.example.lFPetBackend.service

import com.example.lFPetBackend.models.dto.FindPetAlikeDTO
import com.example.lFPetBackend.models.entities.PetInfoEntity
import com.example.lFPetBackend.repository.PetInfoRepository
import org.springframework.stereotype.Service

@Service
class PetInfoService {

    lateinit var petInfoRepository: PetInfoRepository

    fun getAllPets(): List<PetInfoEntity> = petInfoRepository.findAll()

    fun getPetInfoById(id: Long): PetInfoEntity = petInfoRepository.findById(id).get()

    fun createPetInfo(petInfo: PetInfoEntity): PetInfoEntity = petInfoRepository.save(petInfo)

    fun findPetsThatAlike(data: FindPetAlikeDTO): List<PetInfoEntity> {
        return petInfoRepository.findPetsThatAlike(data)
    }

    fun setIsAdopted(isAdopted: Boolean, id: Long): Int {
        return petInfoRepository.setIsAdopted(isAdopted, id)
    }

    fun updatePetInfo(petInfo: PetInfoEntity): PetInfoEntity = petInfoRepository.save(petInfo)

    //logical delete
    fun logicalDelete(id: Long): Int {
        return petInfoRepository.setIsDeleted(id)
    }

    fun deletePetInfo(id: Long) = petInfoRepository.deleteById(id)


}