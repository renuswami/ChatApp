package com.renu.chatapp.data.remote

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.renu.chatapp.data.remote.FirestoreCollections.channelsColl
import com.renu.chatapp.domain.model.Channel
import com.renu.chatapp.domain.model.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class ChannelRepo {

    suspend fun getOneToOneChannel(
        currentUserId: String,
        otherUserId: String
    ): Channel? {
        return Firebase.firestore
            .channelsColl()
            .whereEqualTo(Channel::type.name, Channel.Type.OneToOne)
            .whereArrayContainsAny(Channel::members.name, listOf(currentUserId, otherUserId))
            .get()
            .await()
            .toObjects(Channel::class.java)
            .firstOrNull {
                it.members == listOf(currentUserId, otherUserId)
            }
    }

    suspend fun createOneToOneChannel(
        currentUserId: String,
        otherUserId: String
    ): String {
        val collRef = Firebase.firestore.channelsColl()
        val id = collRef.document().id
        Firebase.firestore
        collRef.document(id)
            .set(
                Channel(
                    imageUrl = null,
                    type = Channel.Type.OneToOne,
                    name = "OneToOne",
                    description = null,
                    members = listOf(
                        currentUserId, otherUserId
                    ),
                    messages = emptyList()
                )
            )
            .await()
        return id
    }

    suspend fun createGroupChannel(
        currentUserId: String,
        name: String,
        description: String?,
        groupImageUrl: String?,
        members: List<String>,
    ): String {
        val collRef = Firebase.firestore.channelsColl()
        val id = collRef.document().id
        Firebase.firestore
        collRef.document(id)
            .set(
                Channel(
                    imageUrl = groupImageUrl,
                    type = Channel.Type.Group,
                    name = name,
                    description = description,
                    members = members + currentUserId,
                    messages = emptyList()
                )
            )
            .await()
        return id
    }

    suspend fun getAllChannelsOf(userId: String): List<Channel> {
        return Firebase.firestore
            .channelsColl()
            .whereArrayContains(Channel::members.name, userId)
            .get()
            .await()
            .toObjects(Channel::class.java)
    }

    suspend fun getChannel(channelId: String): Channel {
        return Firebase.firestore
            .channelsColl()
            .document(channelId)
            .get()
            .await()
            .toObject(Channel::class.java)
            ?: error("No channel found with id : $channelId")
    }

    suspend fun subscribeToChannel(channelId: String): Flow<Channel> {
        return callbackFlow {
            val registration = Firebase.firestore
                .channelsColl()
                .document(channelId)
                .addSnapshotListener { snapshot, error ->
                    error?.let { throw it }
                    val channel = snapshot?.toObject(Channel::class.java)
                    channel?.let {
                        CoroutineScope(coroutineContext).launch {
                            send(it)
                        }
                    }
                }
            awaitClose { registration .remove() }
        }

    }

    suspend fun sendMessage(channelId: String, message: Message) {
        Firebase.firestore
            .channelsColl()
            .document(channelId)
            .update(Channel::messages.name, FieldValue.arrayUnion(message))
            .await()
    }
}