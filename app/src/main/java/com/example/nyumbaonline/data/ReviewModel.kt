package com.example.nyumbaonline.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object FirestoreRepository {
    private val db = FirebaseFirestore.getInstance()

    suspend fun getUserDetails(userName: String): Map<String, Any>? {
        val doc = db.collection("users").document(userName).get().await()
        return doc.data
    }

    suspend fun saveReview(
        userName: String,
        propertyId: String,
        reviewText: String,
        smileyRating: Int,
        userDetails: Map<String, Any>?
    ) {
        val review = hashMapOf(
            "userName" to userName,
            "propertyId" to propertyId,
            "reviewText" to reviewText,
            "smileyRating" to smileyRating,
            "userDetails" to userDetails
        )
        db.collection("reviews").add(review).await()
    }
}