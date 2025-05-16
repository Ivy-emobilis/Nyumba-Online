package com.example.nyumbaonline.data

import androidx.lifecycle.ViewModel
import com.example.nyumbaonline.models.PropertyData
import com.google.firebase.firestore.FirebaseFirestore

class PropertyViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()

    fun addProperty(propertyData: PropertyData, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        firestore.collection("properties")
            .add(propertyData)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }
}
