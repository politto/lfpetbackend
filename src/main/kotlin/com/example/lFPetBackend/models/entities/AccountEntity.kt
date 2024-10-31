package com.example.lFPetBackend.models.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "Account")
data class AccountEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accountId")
    var accountId: Long? = null,

    @Column(name = "accountName", nullable = false, unique = true)
    var accountName: String,

    @Column(name = "SessionToken")
    var sessionToken: String,

    @Column(name = "email")
    var email: String,

    @Column(name = "phoneNumber")
    var phoneNumber: String,

    @Column(name = "isDeleted")
    var isDeleted: Boolean,

//    @OneToMany(mappedBy = "account", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
//    var pets: List<PetOwnershipEntity>?,
//
//    @JsonIgnore
//    @OneToMany(mappedBy = "account")
//    var posts: List<PostEntity>?

)
