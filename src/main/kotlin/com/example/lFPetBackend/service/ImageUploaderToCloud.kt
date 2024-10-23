package com.example.lFPetBackend.service

import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import io.github.cdimascio.dotenv.Dotenv
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream

@Service
class ImageUploaderToCloud {

    @Value("\${cloudinary.cloud_name}")
    private lateinit var cloudName: String

    @Value("\${cloudinary.api_key}")
    private lateinit var apiKey: String

    @Value("\${cloudinary.api_secret}")
    private lateinit var apiSecret: String

    @Value("\${cloudinary.url}")
    private lateinit var cloudinaryUrl: String

    fun uploadImageToCloud(image: MultipartFile, displayName: String): String {

//        return "https://res.cloudinary.com/dvo4douge/image/upload/v1726739552/samples/animals/cat.jpg"
        val cloudinary = Cloudinary(cloudinaryUrl)

        // Convert MultipartFile to File
        val tempFile = File.createTempFile("temp", image.originalFilename)
        FileOutputStream(tempFile).use { fos ->
            fos.write(image.bytes)
        }


        // Upload the image
        val params1 = ObjectUtils.asMap(
            "use_filename", true,
            "unique_filename", false,
            "overwrite", true
        )
        val uploadResult = cloudinary.uploader().upload(tempFile, params1)
        return uploadResult["url"].toString()

    }

    //delete uploaded image
    fun deleteImageFromCloud(imageUrl: String) {
        val cloudinary = Cloudinary(cloudinaryUrl)
        val publicId = imageUrl.substringAfterLast("/").substringBeforeLast(".")
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap())

    }

}