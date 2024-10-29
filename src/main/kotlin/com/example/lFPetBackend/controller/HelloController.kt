package com.example.LFPetBackend.controller

import com.example.lFPetBackend.models.*
import com.example.lFPetBackend.models.dto.FindPetAlikeDTO
import com.example.lFPetBackend.models.dto.PostDTO
import com.example.lFPetBackend.models.dto.PostWithContactDto
import com.example.lFPetBackend.models.dto.PostWithPetsDto
import com.example.lFPetBackend.models.entities.AccountEntity
import com.example.lFPetBackend.models.entities.PetInfoEntity
import com.example.lFPetBackend.models.entities.PetOwnershipEntity
import com.example.lFPetBackend.models.entities.PostEntity
import com.example.lFPetBackend.service.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*

@CrossOrigin(origins = ["http://localhost:3000"])
@RestController
class HelloController
{

    @Autowired
    lateinit var accountService: AccountService

    @Autowired
    lateinit var postService: PostService

    @Autowired
    lateinit var petService: PetInfoService

    @Autowired
    lateinit var imageUploaderToCloud: ImageUploaderToCloud

    @Autowired
    lateinit var petOwnershipService: PetOwnershipService
//    @lateinit var petInPostService: PetInPostService

    @GetMapping("/")
    fun index():ResponseEntity<String>{
        return ResponseEntity.ok("LoveLingOrm")
    }

    //--- Account ----

    @PostMapping("/createAccount")
    fun createAccount(@RequestBody accountEntity: AccountEntity):ResponseEntity<AccountEntity>{
        return ResponseEntity.ok(accountService.createAccount(accountEntity))
    }

    @GetMapping("/getAccounts")
    fun getAccounts():ResponseEntity<List<AccountEntity>>{
        return ResponseEntity.ok(accountService.getAllAccounts())
    }

    @GetMapping("/getAccountById")
    fun getAccountById(@RequestParam accountId: Long):ResponseEntity<Optional<AccountEntity>>{
        return ResponseEntity.ok(accountService.getAccountById(accountId))
    }

    @GetMapping("/getAllPresentOwnedPet")
    fun getAllPresentOwnedPet(@RequestParam accountId: Long):ResponseEntity<List<PetOwnershipEntity>>{
        return ResponseEntity.ok(accountService.getAllPresentOwnedPet(accountId))
    }

    // Not sure concept of session token
    @PatchMapping("/authenticate")
    fun authenticate(@RequestParam sessionToken: String, @RequestParam accountId: Long):Boolean{
        return accountService.setSessionToken(sessionToken, accountId)
    }

    @PatchMapping("/updateAccount")
    fun updateAccount(@RequestBody accountEntity: AccountEntity):ResponseEntity<AccountEntity>{
        return ResponseEntity.ok(accountService.updateAccount(accountEntity))
    }

    @PostMapping("/deleteAccount")
    fun deleteAccount(@RequestParam accountId: Long):ResponseEntity<Boolean>{
        return ResponseEntity.ok(accountService.logicalDeleteById(accountId))
    }



    //--- Post ----

    @PostMapping("/createPost")
    fun createPost(@RequestBody postDTO: PostDTO):ResponseEntity<PostEntity>{
        val foundAccount = accountService.getAccountById(postDTO.accountId!!)
        if (foundAccount.isEmpty){
            return ResponseEntity.status(400).body(null)
        }



        //convert postDTO to postEntity
        val postEntity = PostEntity(
            postTitle = postDTO.postTitle,
            postContent = postDTO.postContent,
            postDate = Date(),
            postType = postDTO.postType,
            postImageLink = postDTO.postImageLink,
            postStatus = postDTO.postStatus,
            isDeleted = postDTO.isDeleted,
            account = foundAccount.get(),
            petParticipated = postDTO.petParticipated
        )
        return ResponseEntity.ok(postService.createPost(postEntity))
    }

    @PostMapping
    fun addPostWithPets(@RequestBody postWithPetsDto: PostWithPetsDto): ResponseEntity<PostEntity> {
        val savedPost = postService.savePostWithPets(postWithPetsDto)
        return ResponseEntity(savedPost, HttpStatus.CREATED)
    }

//    @GetMapping("/getTwentyPosts")
//    fun get20Posts(@RequestParam index: Int):ResponseEntity<List<PostEntity>>{
//        return ResponseEntity.ok(postService.getSomePosts(index))
//    }

    @GetMapping("/getTwentyPosts")
    fun get20Posts(@RequestParam index: Int):ResponseEntity<List<PostWithContactDto>>{
        //return List of postEntity and contact, email from accountEntity by accountId
//        var ret: List<PostWithContactDto>  = mutableListOf();
//        val posts20: List<PostEntity> = postService.getSomePosts(index)
//        for (post in posts20){
//            val account = post.account
//            if (account != null){
//                val postWithContact = PostWithContactDto(post, account.email, account.phoneNumber)
//                ret += postWithContact
//            }
//            else {
//                val postWithContact = PostWithContactDto(post)
//                ret += postWithContact
//            }
//        }
//        return ResponseEntity.ok(ret)

    }

    @GetMapping("/getPostsById")
    fun getPostsById(@RequestParam postId: Long):ResponseEntity<PostEntity>{
        return ResponseEntity.ok(postService.getPostById(postId))
    }

    @GetMapping("/getPostsByAccountId")
    fun getPostsByAccountId(@RequestParam accountId: Long, @RequestParam startIndex: Int):ResponseEntity<List<PostEntity>>{
        return ResponseEntity.ok(postService.getPostsByAccountId(accountId, startIndex))
    }

    @PatchMapping("/updatePost")
    fun updatePost(@RequestBody postEntity: PostEntity):ResponseEntity<PostEntity>{
        val updatedPost = postService.updatePost(postEntity)
        return ResponseEntity.ok(updatedPost)
    }

    @PostMapping("/deletePost")
    fun deletePost(@RequestParam postId: Long):ResponseEntity<Boolean>{
        return ResponseEntity.ok(postService.deletePost(postId) > 0)
    }

    @PostMapping("/sendLostPetEmail")
    fun sendLostPetEmail(@RequestParam postId: Long):ResponseEntity<Boolean>{

        //To be implemented in future
        return ResponseEntity.status(501).body(false)
    }

    //--- Pet ----

    @PostMapping("/createPet")
    fun createPet(@RequestBody petInfoEntity: PetInfoEntity):ResponseEntity<PetInfoEntity>{
        return ResponseEntity.ok(petService.createPetInfo(petInfoEntity))
    }

    @PatchMapping("/updatePet")
    fun updatePet(@RequestBody petInfoEntity: PetInfoEntity):PetInfoEntity{
        return petService.updatePetInfo(petInfoEntity)
    }

    @GetMapping("/getPets")
    fun getPets():ResponseEntity<List<PetInfoEntity>>{
        return ResponseEntity.ok(petService.getAllPets())
    }

    @GetMapping("/getPetById")
    fun getPetById(@RequestParam petId: Long):ResponseEntity<PetInfoEntity>{
        return ResponseEntity.ok(petService.getPetInfoById(petId))
    }

    @GetMapping("/findPetsThatAlike")
    fun findPetsThatAlike(@RequestBody findPetAlikeDto: FindPetAlikeDTO):ResponseEntity<List<PetInfoEntity>>{
        return ResponseEntity.ok(petService.findPetsThatAlike(findPetAlikeDto))
    }

    @PatchMapping("/setIsAdopted")
    fun setIsAdopted(@RequestParam isAdopted: Boolean, @RequestParam petId: Long):ResponseEntity<Int>{
        return ResponseEntity.ok(petService.setIsAdopted(isAdopted, petId))
    }

    @PostMapping("/deletePet")
    fun deletePet(@RequestParam petId: Long):ResponseEntity<Unit>{
        petService.logicalDelete(petId)
        //method in Spring Framework used to create an HTTP response with a status code of 200 OK and no body content.
        return ResponseEntity.ok().build()
    }

    //--- PetOwnerShip ----

    @PostMapping("/createPetOwnership")
    fun createPetOwnership(@RequestBody petOwnershipEntity: PetOwnershipEntity):ResponseEntity<String>{
        val createdPetInfo =  petOwnershipService.createPetOwnership(petOwnershipEntity)
        return ResponseEntity.ok("PetOwnership Created")
    }

    @GetMapping("/getPetsByAccountId")
    fun getPetsByAccountId(@RequestParam accountId: Long):ResponseEntity<List<PetInfoEntity>>{
        return ResponseEntity.ok(petOwnershipService.getPetsByAccountId(accountId))
    }

    @GetMapping("/getCurrentPetsByAccountId")
    fun getCurrentPetsByAccountId(@RequestParam accountId: Long):ResponseEntity<List<PetInfoEntity>>{
        return ResponseEntity.ok(petOwnershipService.getCurrentPetsByAccountId(accountId))
    }



    //--- PetInPost -----

    //--- imgUploader ----
    @PostMapping("/uploadImg")
    fun uploadImg(@RequestParam img: MultipartFile, @RequestParam displayName: String):ResponseEntity<String>{
        if (img.isEmpty) {
            return ResponseEntity.badRequest().body("File is empty")
        }
        return ResponseEntity.ok(imageUploaderToCloud.uploadImageToCloud(img, displayName))
    }

    //delete
    @PostMapping("/deleteImg")
    fun deleteImg(@RequestParam imgName: String):ResponseEntity<Boolean>{
        ResponseEntity.ok(imageUploaderToCloud.deleteImageFromCloud(imgName))
        return ResponseEntity.ok(true)
    }





}
