package com.example.nyumbaonline.models

data class TenantModel(
    val id: String = "", // Firestore document ID
    val email: String = "",
    val password: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val idNumber: String = "",
    val profilePhotoUrl: String = "",
    val county: String = "",
    val estate: String = "",
    val houseNumber: String = ""
)
