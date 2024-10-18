package com.example.lFPetBackend.service

import com.example.lFPetBackend.models.entities.AccountEntity
import com.example.lFPetBackend.models.entities.PetInfoEntity
import com.example.lFPetBackend.models.entities.PetOwnershipEntity
import com.example.lFPetBackend.repository.AccountRepository
import com.example.lFPetBackend.repository.PetOwnershipRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class AccountService {

    @Autowired
    lateinit var accountRepository: AccountRepository

    @Autowired
    lateinit var petOwnerShipRepository: PetOwnershipRepository

    fun getAllAccounts(): List<AccountEntity> = accountRepository.findAll()

    fun getAllPresentOwnedPet(accId: Long): List<PetOwnershipEntity> = petOwnerShipRepository.findAllPetsByAccountId(accId)

    fun getAccountById(id: Long): Optional<AccountEntity> = accountRepository.findById(id)

    fun createAccount(account: AccountEntity): AccountEntity? {
        val existAccount = getAccountByAccountName(account.accountName)
        if (existAccount != null) {
            return null
        }
        return accountRepository.save(account)

    }

    fun updateAccount(account: AccountEntity) = accountRepository.save(account)

    fun logicalDeleteById(id: Long): Boolean {
        if (accountRepository.findById(id).isEmpty) return false;
        val res: Int = accountRepository.setIsDeleted(true, id);
        return res >= 0
    }

    fun getAccountByAccountName(accName: String) = accountRepository.findByAccName(accName)

    fun setSessionToken(sessionToken: String, accId: Long): Boolean {
        val res: Int = accountRepository.setSessionToken(sessionToken, accId)
        return res >= 0
    };


}