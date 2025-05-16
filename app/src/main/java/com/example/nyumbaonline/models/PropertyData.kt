package com.example.nyumbaonline.models

data class PropertyData(
    val pictureUri: String = "",
    val name: String = "",
    val numberOfUnits: Int = 0,
    val address: String = "",
    val description: String = "",
    val managementId: String = "" // To associate the property with a management
)
