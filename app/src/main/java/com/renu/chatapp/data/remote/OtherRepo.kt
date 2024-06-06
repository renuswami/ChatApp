package com.renu.chatapp.data.remote

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.renu.chatapp.data.remote.FirestoreCollections.otherColl
import com.renu.chatapp.domain.model.Secret
import kotlinx.coroutines.tasks.await

class OtherRepo {

    suspend fun getServiceAccountJson(): String {
        return Firebase.firestore.otherColl()
            .document("secret")
            .get()
            .await()
            .toObject(Secret::class.java)
            ?.svcAc
            ?: error("SvcAc not found on firestore")
    }
}