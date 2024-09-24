package com.example.lFPetBackend.models.entities

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

    @Column(name = "password", nullable = false)
    var password: String,

    @OneToOne(cascade = [CascadeType.ALL], optional = false)
    @JoinColumn(name = "email")
    var email: SubscribedEmail,

    @Column(name = "phoneNumber", nullable = false)
    var phoneNumber: String?,

    @Column(name = "isDeleted", nullable = false)
    var isDeleted: Boolean,

    @OneToMany(mappedBy = "account", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var pets: List<PetOwnershipEntity>?,

    @OneToMany(mappedBy = "account")
    var posts: List<PostEntity>?

)