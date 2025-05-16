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
                    val property = document.toObject(PropertyData::class.java)
                    properties.add(property)
                }
            }
            .addOnFailureListener {
                // Handle error if needed
            }
    }
}
