package com.example.nyumbaonline.data

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.nyumbaonline.models.PropertyData
import com.google.firebase.firestore.FirebaseFirestore

class PropertyViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()

    val properties = mutableStateListOf<PropertyData>()

    fun addProperty(propertyData: PropertyData, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        firestore.collection("properties")
            .add(propertyData)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }

    fun fetchPropertiesForManagement(managementId: String) {
        firestore.collection("properties")
            .whereEqualTo("managementId", managementId)
            .get()
            .addOnSuccessListener { result ->
                properties.clear()
                for (document in result) {
                    val property = document.toObject(PropertyData::class.java).copy(id = document.id)
                    properties.add(property)
                }
            }
            .addOnFailureListener {
                // Handle error if needed
            }
    }

    fun fetchPropertyById(propertyId: String, onSuccess: (PropertyData) -> Unit) {
        firestore.collection("properties").document(propertyId).get()
            .addOnSuccessListener { document ->
                val property = document.toObject(PropertyData::class.java)?.copy(id = document.id)
                if (property != null) onSuccess(property)
            }
            .addOnFailureListener { e -> /* Log error */ }
    }

    fun updateProperty(property: PropertyData, onSuccess: () -> Unit) {
        firestore.collection("properties").document(property.id).set(property)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> /* Log error */ }
    }

    fun deleteProperty(propertyId: String, onSuccess: () -> Unit) {
        firestore.collection("properties").document(propertyId).delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> /* Log error */ }
    }
}
