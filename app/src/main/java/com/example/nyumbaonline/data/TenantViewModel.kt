package com.example.nyumbaonline.data


import androidx.lifecycle.ViewModel
import com.example.nyumbaonline.models.TenantModel
import com.google.firebase.firestore.FirebaseFirestore

// File: app/src/main/java/com/example/nyumbaonline/data/TenantViewModel.kt
class TenantViewModel : ViewModel() {
    internal val firestore = FirebaseFirestore.getInstance()

    fun addTenant(tenant: TenantModel, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        firestore.collection("tenants")
            .add(tenant)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun fetchTenantsByProperty(propertyId: String, onSuccess: (List<TenantModel>) -> Unit, onFailure: (Exception) -> Unit) {
        firestore.collection("tenants")
            .whereEqualTo("propertyId", propertyId)
            .get()
            .addOnSuccessListener { result ->
                val tenants = result.documents.mapNotNull { it.toObject(TenantModel::class.java)?.copy(id = it.id) }
                onSuccess(tenants)
            }
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
            .set(updatedTenant.copy(id = tenantId)) // Ensure the ID is preserved
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun fetchTenants(onSuccess: (List<TenantModel>) -> Unit, onFailure: (Exception) -> Unit) {
        firestore.collection("tenants")
            .get()
            .addOnSuccessListener { result ->
                val tenants = result.documents.mapNotNull { it.toObject(TenantModel::class.java)?.copy(id = it.id) }
                onSuccess(tenants)
            }
            .addOnFailureListener { onFailure(it) }
    }
}
