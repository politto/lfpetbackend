package com.example.lFPetBackend.models.entities

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "Post")
data class PostEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postId")
    var postId: Long? = null,

    @Column(name = "postTitle")
    var postTitle: String? = null,

    @Column(name = "postContent")
    var postContent: String? = null,

    @Column(name = "postDate")
    var postDate: Date? = null,

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "accountId")
    var accountId: Long? = null,

    //Either lost or found
    @Column(name = "postType")
    var postType: String? = null,

    @Column(name = "postImage")
    var postImageLink: String? = null,

//    @Column(name = "postLink")
//    var postLink: String? = null,

    @Column(name = "postStatus")
    var postStatus: String? = null,

    @Column(name = "isDeleted")
    var isDeleted: Boolean = false,

    // change to JDBC
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//        name = "petParticipationInPosts",
//        joinColumns = [JoinColumn(name = "postId")],
//        inverseJoinColumns = [JoinColumn(name = "petId")]
//    )
//    var petParticipated: List<PetInfoEntity> = listOf()


)