package com.renu.chatapp.data

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.renu.chatapp.data.remote.FirestoreCollections.usersColl
import com.renu.chatapp.domain.model.User
import kotlinx.coroutines.tasks.await

class UserRepo {

    suspend fun saveUser(user: User ): User {
        val collRef = Firebase.firestore.usersColl()
            val id = collRef.document().id
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
}