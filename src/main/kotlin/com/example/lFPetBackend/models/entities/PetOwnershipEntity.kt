package com.example.lFPetBackend.models.entities

import jakarta.persistence.*
import java.io.Serializable
import java.util.*

@Entity
@Table(name = "PetOwnership")
data class PetOwnershipEntity (
    @EmbeddedId
    var id: PetOwnershipCompoId? = null,

    @ManyToOne
    @MapsId("accountId")
    @JoinColumn(name = "accountId")
    var account: AccountEntity? = null,

    @ManyToOne
    @MapsId("petId")
    @JoinColumn(name = "petId")
    var pet: PetInfoEntity? = null,

    @Column(name = "AdoptedFrom")
    var adoptedFrom: Date? = null,

    @Column(name = "AdoptedTo")
    var adoptedTo: Date? = null

    // Composite primary key representation:
    // In the database, the composite primary key will be represented as two separate columns: accountId and petId.
    // In the entity object, the composite key will be represented by an instance of the PetOwnershipCompoId class.
    /*
    Example usage
    val compositeId = PetOwnershipCompoId(accountId = 1, petId = 2)
    val petOwnership = PetOwnershipEntity(id = compositeId, adoptedFrom = Date(), adoptedTo = null)
     */

)

@Embeddable
data class PetOwnershipCompoId(
    var accountId: Long = 0,
    var petId: Long = 0
) : Serializable {
    override fun hashCode(): Int {
        return accountId.hashCode() + petId.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PetOwnershipCompoId) return false
        return accountId == other.accountId && petId == other.petId
    }

}