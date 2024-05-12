package com.renu.chatapp.data.remote

import com.google.firebase.firestore.FirebaseFirestore

object FirestoreCollections {

    fun FirebaseFirestore.userColl() = collection("users")
}