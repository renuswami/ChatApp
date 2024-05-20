package com.renu.chatapp.data

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.renu.chatapp.data.remote.FirestoreCollections.userColl
import com.renu.chatapp.domain.model.User
import kotlinx.coroutines.tasks.await

class UserRepo {

    suspend fun saveUser(user: User ){
        Firebase.firestore
            .userColl()
            .add(user)
            .await()
    }

    suspend fun getAllUsers(): List<User>{
        return Firebase.firestore
            .userColl()
            .get()
            .await()
            .toObjects(User::class.java)
    }

    suspend fun getUserWithEmail(email: String): User? {
        return Firebase.firestore
            .userColl()
            .whereEqualTo(User::email.name, email)
            .get()
            .await()
            .toObjects(User::class.java)
            .firstOrNull()
    }
}