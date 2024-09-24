package com.example.lFPetBackend.service

import com.example.lFPetBackend.models.dto.FindPetAlikeDTO
import com.example.lFPetBackend.models.entities.PetInfoEntity
import com.example.lFPetBackend.repository.PetInfoRepository
import org.springframework.stereotype.Service

@Service
class PetInfoService {

    lateinit var petInfoRepository: PetInfoRepository

    fun getAllPets() = petInfoRepository.findAll()

    fun getPetInfoById(id: Long) = petInfoRepository.findById(id)

    fun createPetInfo(petInfo: PetInfoEntity) = petInfoRepository.save(petInfo)

    fun findPetsThatAlike(data: FindPetAlikeDTO): List<PetInfoEntity> {
        return petInfoRepository.findPetsThatAlike(data)
    }

    fun setIsAdopted(isAdopted: Boolean, id: Long): Int {
        return petInfoRepository.setIsAdopted(isAdopted, id)
    }

    fun updatePetInfo(petInfo: PetInfoEntity) = petInfoRepository.save(petInfo)

    fun deletePetInfo(id: Long) = petInfoRepository.deleteById(id)


}