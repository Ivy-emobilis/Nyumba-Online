// In ManagementViewModel.kt
package com.example.nyumbaonline.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.nyumbaonline.models.ManagementData

class ManagementViewModel : ViewModel() {
    fun getManagementDetails(propertyId: String) = liveData {
        // Simulate fetching data from a database or API
        val managementDetails = ManagementData(
            id = "1",
            fullname = "John Doe",
            email = "johndoe@example.com",
            company = "Doe Properties",
            nationality = "Kenyan",
            idNo = "12345678",
            homeCounty = "Nairobi"
        )
        emit(managementDetails) // Replace with actual database query
    }
}