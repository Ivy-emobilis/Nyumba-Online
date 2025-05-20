import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
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
    val houseNumber: String = ""
) : Parcelable