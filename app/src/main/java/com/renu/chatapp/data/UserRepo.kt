package com.renu.chatapp.data

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.renu.chatapp.data.remote.FirestoreCollections.usersColl
import com.renu.chatapp.domain.model.User
import kotlinx.coroutines.tasks.await

class UserRepo {

    suspend fun upsertUser(user: User ): User {
        val collRef = Firebase.firestore.usersColl()
            val id = user.id ?: collRef.document().id
            collRef.document(id).set(user).await()
            return user.copy(id = id)
    }

    suspend fun getAllUsers(): List<User>{
        return Firebase.firestore
            .usersColl()
            .get()
            .await()
            .toObjects(User::class.java)
    }

    suspend fun getUserWithEmail(email: String): User? {
        return Firebase.firestore
            .usersColl()
            .whereEqualTo(User::email.name, email)
            .get()
            .await()
            .toObjects(User::class.java)
            .firstOrNull()
    }

    suspend fun getUserById(id: String): User {
        return Firebase.firestore
            .usersColl()
            .document(id)
            .get()
            .await()
            .toObject(User::class.java)
            ?: error("no user found with id: $id")
    }

    suspend fun updateFcmToken(token: String, userId: String){
        Firebase.firestore
            .usersColl()
            .document(userId)
            .update(User::fcmToken.name, token)
            .await()
    }
}