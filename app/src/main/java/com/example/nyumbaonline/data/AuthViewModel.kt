package com.example.nyumbaonline.data

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.nyumbaonline.models.ManagementData
import com.example.nyumbaonline.navigation.ROUTE_MANAGEMENT_DASHBOARD
import com.google.firebase.firestore.FirebaseFirestore

class AuthViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()

    fun registerManagement(
        managementData: ManagementData,
        navController: NavController,
        context: Context
    ) {
        firestore.collection("managements")
            .add(managementData)
            .addOnSuccessListener {
                Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT).show()
                navController.navigate(ROUTE_MANAGEMENT_DASHBOARD) {
                    popUpTo(ROUTE_MANAGEMENT_DASHBOARD) { inclusive = true }
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Registration Failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
