package com.example.nyumbaonline.models

// src/main/java/com/example/nyumbaonline/models/ManagementData.kt
import java.io.Serializable

data class ManagementData(
    val id: String? = null,
    val fullname: String = "",
    val email: String = "",
    val password: String = "",
    val company: String = "",
    val nationality: String = "",
    val idNo: String = "",
    val homeCounty: String = ""
) : Serializable