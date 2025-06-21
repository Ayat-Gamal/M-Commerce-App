@file:Suppress("UNCHECKED_CAST")

package com.example.m_commerce.features.wishlist.data.remote

import android.util.Log
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
    override suspend fun addToWishlist(productVariantId: String) {
        uid?.let {
            db.collection("users")
                .document(uid)
                .update("wishlist", FieldValue.arrayUnion(productVariantId))
                .await()
        }
    }

    override suspend fun deleteFromWishlist(productVariantId: String) {
        uid?.let {
            db.collection("users")
                .document(uid)
                .update("wishlist", FieldValue.arrayRemove(productVariantId))
                .await()
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
        Log.i("TAG", "uid: $uid")
        uid?.let {
            val snapshot = db.collection("users")
                .document(uid)
                .get()
                .await()
            val list = snapshot.get("wishlist") as? List<String> ?: emptyList()
            emit(list)
        }
        emit(emptyList())
    }
}