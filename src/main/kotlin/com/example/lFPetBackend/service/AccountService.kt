package com.example.lFPetBackend.service

import com.example.lFPetBackend.models.entities.AccountEntity
import com.example.lFPetBackend.repository.AccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AccountService {

    @Autowired
    lateinit var accountRepository: AccountRepository

    fun getAllAccounts() = accountRepository.findAll()

    fun getAccountById(id: Long) = accountRepository.findById(id)

    fun createAccount(account: AccountEntity): Map<String, String> {
        val existAccount = getAccountByAccountName(account.accountName)
        if (existAccount != null) {
            return mapOf("msg" to "error")
        }
        accountRepository.save(account)
        return mapOf("msg" to "success")
    }

    fun updateAccount(account: AccountEntity) = accountRepository.save(account)

    fun logicalDeleteById(id: Long): Map<String, String> {
        if (accountRepository.findById(id).isEmpty) return mapOf("msg" to "error")
        val res: Int = accountRepository.setIsDeleted(true, id);
        return if (res >= 0) mapOf("msg" to "success") else mapOf("msg" to "error")
    }

    fun getAccountByAccountName(accName: String) = accountRepository.findByAccName(accName)


}