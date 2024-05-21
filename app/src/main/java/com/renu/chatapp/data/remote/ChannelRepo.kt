package com.renu.chatapp.data.remote

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.renu.chatapp.data.remote.FirestoreCollections.channelsColl
import com.renu.chatapp.domain.model.Channel
import kotlinx.coroutines.tasks.await


class ChannelRepo{

    suspend fun getOneToOneChannel(
         currentUserId: String,
         otherUserId: String
    ): Channel? {
        return Firebase.firestore
            .channelsColl()
            .whereEqualTo(Channel::type.name, Channel.Type.OneToOne)
            .whereArrayContainsAny(Channel::members.name, listOf( currentUserId, otherUserId))
            .get()
            .await()
            .toObjects(Channel::class.java)
            .firstOrNull{
                it.members == listOf(currentUserId, otherUserId)
            }
    }
}