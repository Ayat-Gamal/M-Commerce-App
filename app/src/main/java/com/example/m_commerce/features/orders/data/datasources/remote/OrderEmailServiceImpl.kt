package com.example.m_commerce.features.orders.data.datasources.remote

import android.util.Log
import com.example.m_commerce.features.orders.data.model.EmailRequest
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class OrderEmailServiceImpl @Inject constructor(private val fireStore: FirebaseFirestore) : OrderEmailService {

    override suspend fun sendEmail(emailRequest: EmailRequest) {
        val doc = fireStore.collection("mail").document()
        doc.set(emailRequest.toHashMap()).addOnSuccessListener {
            Log.d("Firestore", "Document created with ID: ${doc.id}")
        }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error adding document", e)
            }
    }
}