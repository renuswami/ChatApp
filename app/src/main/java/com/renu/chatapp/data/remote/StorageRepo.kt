package com.renu.chatapp.data.remote

import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await


class StorageRepo {

    suspend fun uploadFile(path: String, uri: Uri): String {
        Firebase
            .storage
            .getReference(path)
            .putFile(uri)
            .await()
        val downloadUrl = Firebase.storage.getReference(path).downloadUrl.await()
            ?: throw IllegalStateException("Unable to get download url")
        return downloadUrl.toString()
    }
}