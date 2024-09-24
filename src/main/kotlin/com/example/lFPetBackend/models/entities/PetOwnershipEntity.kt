package com.example.lFPetBackend.models.entities

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "PetOwnership")
data class PetOwnershipEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ownershipId")
    var ownershipId: Long? = null,

    @ManyToOne
    @JoinColumn(name = "accountId")
    var account: AccountEntity? = null,

    @ManyToOne
    @JoinColumn(name = "ownerHistList")
    var pet: PetInfoEntity? = null,

    @Column(name = "AdoptedFrom")
    var adoptedFrom: Date? = null,

    @Column(name = "AdoptedTo")
    var adoptedTo: Date? = null


)