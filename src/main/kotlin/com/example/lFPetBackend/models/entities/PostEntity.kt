package com.example.lFPetBackend.models.entities

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "Post")
class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postId")
    var postId: Long? = null

    @Column(name = "postTitle")
    var postTitle: String? = null

    @Column(name = "postContent")
    var postContent: String? = null

    @Column(name = "postDate")
    var postDate: Date? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId")
    var account: AccountEntity? = null

    //Either lost or found
    @Column(name = "postType")
    var postType: String? = null

    @Column(name = "postImage")
    var postImage: String? = null

    @Column(name = "postLink")
    var postLink: String? = null

    @Column(name = "postStatus")
    var postStatus: String? = null


}