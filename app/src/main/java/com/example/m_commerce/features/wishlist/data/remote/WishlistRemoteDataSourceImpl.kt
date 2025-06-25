@file:Suppress("UNCHECKED_CAST")

package com.example.m_commerce.features.wishlist.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class WishlistRemoteDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore
) : WishlistRemoteDataSource {

    private val uid = FirebaseAuth.getInstance().currentUser?.uid
    override suspend fun addToWishlist(productVariantId: String) = flow {
        try {
            uid?.let {
                db.collection("users")
                    .document(uid)
                    .update("wishlist", FieldValue.arrayUnion(productVariantId))
                    .await()
                emit("Product added to wishlist successfully.")
            } ?: emit("User not logged in.")
        } catch (e: Exception) {
            emit("Failed to add product to wishlist: ${e.message}")
        }
    }

    override suspend fun deleteFromWishlist(productVariantId: String) = flow {
        try {
            if (uid == null) {
                emit("User not logged in.")
                return@flow
            }

            db.collection("users")
                .document(uid)
                .update("wishlist", FieldValue.arrayRemove(productVariantId))
                .await()

            emit("Product removed from wishlist successfully.")
        } catch (e: Exception) {
            emit("Failed to remove product from wishlist: ${e.message}")
        }
    }

    override suspend fun isInWishlist(productVariantId: String) = flow {
        var isIn = false
        uid?.let {
            val snapshot = db.collection("users")
                .document(uid)
                .get()
                .await()

            val wishlist = snapshot.get("wishlist") as? List<String> ?: emptyList()
            isIn = wishlist.contains(productVariantId)
        }
        emit(isIn)
    }

    override suspend fun getWishlist() = flow {
        var data = emptyList<String>()
        uid?.let {
            val snapshot = db.collection("users")
                .document(uid)
                .get()
                .await()
            data = snapshot.get("wishlist") as? List<String> ?: emptyList()
        }
        emit(data)
    }
}