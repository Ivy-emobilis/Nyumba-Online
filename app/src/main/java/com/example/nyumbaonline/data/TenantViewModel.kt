package com.example.nyumbaonline.data

import androidx.lifecycle.ViewModel
import com.example.nyumbaonline.models.TenantModel
import com.google.firebase.firestore.FirebaseFirestore

class TenantViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()

    fun addTenant(tenant: TenantModel, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        firestore.collection("tenants")
            .add(tenant)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun updateTenantPassword(tenantId: String, newPassword: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        firestore.collection("tenants")
            .document(tenantId)
            .update("password", newPassword)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun updateTenantDetails(tenantId: String, updatedTenant: TenantModel, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        firestore.collection("tenants")
            .document(tenantId)
            .set(updatedTenant)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }
}
