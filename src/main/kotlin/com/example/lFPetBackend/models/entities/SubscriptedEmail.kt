package com.example.lFPetBackend.models.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import jakarta.persistence.Table


@Entity
@Table(name = "SubscribedEmail")
class SubscribedEmail {
    @Id
    @Column(name = "email")
    var email: String? = null

    @Column(name = "isSubscribed")
    var isSubscribed: Boolean? = null

    @OneToOne(mappedBy = "email")
    var account: AccountEntity? = null
}