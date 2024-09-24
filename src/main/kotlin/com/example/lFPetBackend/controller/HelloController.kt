package com.example.lFPetBackend.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController
{
    @GetMapping("/")
    fun index():ResponseEntity<String>{
        return ResponseEntity.ok("LoveLingOrm")
    }
}
