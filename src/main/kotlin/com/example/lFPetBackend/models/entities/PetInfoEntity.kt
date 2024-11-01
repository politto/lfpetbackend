package com.example.lFPetBackend.models.entities

import com.fasterxml.jackson.annotation.JsonIgnore
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
    @Column(name = "gender")
    var gender : String? = null,

    @Column(name = "isAdopted")
    var isAdopted: Boolean = false,

    @Column(name = "detail")
    var detail: String? = null,

    @Column(name = "lastLat")
    var lastLat: Float? = null,

    //Distance(km)=1.85×cos(latitude degrees)
    @Column(name = "lastLng")
    var lastLng: Float? = null,

    @Column(name = "isLost")
    var isLost: Boolean? = null,

    @Column(name = "isDeceased")
    var isDeceased: Boolean? = false,

    @Column(name = "lastPicLink")
    var lastPicLink: String? = null,

    @Column(name = "isDeleted")
    var isDeleted: Boolean = false,

    //Change to manual JDBC
//    @ManyToMany(mappedBy = "petParticipated", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
//    @JsonIgnore
//    var postParticipation: List<PostEntity> = listOf()



)