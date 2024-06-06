package com.renu.chatapp.data.remote

import com.google.firebase.firestore.FirebaseFirestore

object FirestoreCollections {

    fun FirebaseFirestore.usersColl() = collection("users")
    fun FirebaseFirestore.channelsColl() = collection("channels")
    fun FirebaseFirestore.otherColl() = collection("other")
}