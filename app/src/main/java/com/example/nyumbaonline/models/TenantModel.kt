// File: app/src/main/java/com/example/nyumbaonline/models/TenantModel.kt
data class TenantModel(
    val id: String? = null,
    val email: String = "",
    val password: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val idNumber: String = "",
    val profilePhotoUrl: String = "",
    val county: String = "",
    val estate: String = "",
    val houseNumber: String = "",
    val propertyId: String = "" // Add this line
)