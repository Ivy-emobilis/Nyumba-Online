package com.example.nyumbaonline.data

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.nyumbaonline.models.ManagementData
import com.example.nyumbaonline.navigation.ROUTE_MANAGEMENT_DASHBOARD
import com.example.nyumbaonline.navigation.ROUTE_TENANT_DASHBOARD
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.collections.get
import kotlin.text.get
import kotlin.text.set


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
                Toast.makeText(context, "Registration Failed: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    fun login(email: String, password: String, navController: NavController, context: Context) {
        firestore.collection("tenants")
            .whereEqualTo("email", email)
            .whereEqualTo("password", password)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val tenantDoc = documents.documents[0]
                    val tenant =
                        tenantDoc.toObject(TenantModel::class.java)?.copy(id = tenantDoc.id)
                    navController.currentBackStackEntry?.savedStateHandle?.set("tenant", tenant)
                    navController.navigate(ROUTE_TENANT_DASHBOARD)
                } else {
                    firestore.collection("managements")
                        .whereEqualTo("email", email)
                        .whereEqualTo("password", password)
                        .get()
                        .addOnSuccessListener { managementDocs ->
                            if (!managementDocs.isEmpty) {
                                val managementDoc = managementDocs.documents[0]
                                val management = managementDoc.toObject(ManagementData::class.java)
                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    "management",
                                    management
                                )
                                navController.navigate(ROUTE_MANAGEMENT_DASHBOARD)
                            } else {
                                Toast.makeText(context, "Invalid credentials", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Login failed: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
