package com.example.lFPetBackend.models.entities

import jakarta.persistence.*
import java.util.*
import java.util.logging.Level.ALL

@Entity
@Table(name = "PetInfo")
data class PetInfoEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "petId")
    var petId: Long? = null,

    @Column(name = "petName")
    var petName: String? = null,

    @Column(name = "petType")
    var petType : String? = null,

    @Column(name = "breed")
    var breed : String? = null,

    // may be estimated
    @Column(name = "birthDate")
    var birthDate : Date? = null,

    // Either F, M or U
    @Column(name = "Gender")
    var gender : String? = null,

    @Column(name = "isAdopted")
    var isAdopted: String? = null,

    @Column(name = "detail")
    var detail: String? = null,

    @Column(name = "lastLat")
    var lastLat: Float? = null,

    //Distance(km)=1.85×cos(latitude degrees)
    @Column(name = "lastLng")
    var lastLng: Float? = null,

    @Column(name = "isLost")
    var isLost: Boolean? = null,

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "pet", fetch = FetchType.LAZY)
    var ownerHistList: List<PetOwnershipEntity>? = null,

    @Column(name = "isDeceased")
    var isDeceased: Boolean? = null,

    @Column(name = "lastPicLink")
    var lastPicLink: String? = null



)