package com.renu.chatapp.temp

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.renu.chatapp.data.remote.ChannelRepo
import com.renu.chatapp.data.remote.FirestoreCollections.channelsColl
import com.renu.chatapp.data.remote.FirestoreCollections.usersColl
import com.renu.chatapp.domain.model.Channel
import com.renu.chatapp.domain.model.Gender
import com.renu.chatapp.domain.model.User
import kotlinx.coroutines.tasks.await

object Scripts {

    suspend fun saveDummyUsers(){
        val userList = listOf(
            User(
                name = "Alice",
                email = "alice@example.com",
                profileImageUrl = null,
                bio = "I'm Alice",
                gender = Gender.Female,
                dob = "1990-01-01",
                fcmToken = null
            ),
            User(
                name = "Bob",
                email = "bob@example.com",
                profileImageUrl = null,
                bio = "I'm Bob",
                gender = Gender.Male,
                dob = "1995-05-05",
                fcmToken = null
            ),
            User(
                name = "Charlie",
                email = "charlie@example.com",
                profileImageUrl = null,
                bio = "I'm Charlie",
                gender = Gender.Male,
                dob = "1985-10-10",
                fcmToken = null
            ),
            User(
                name = "Diana",
                email = "diana@example.com",
                profileImageUrl = null,
                bio = "I'm Diana",
                gender = Gender.Female,
                dob = "1988-09-15",
                fcmToken = null
            ),
            User(
                name = "Eleanor",
                email = "eleanor@example.com",
                profileImageUrl = null,
                bio = "I'm Eleanor",
                gender = Gender.Female,
                dob = "1992-07-20",
                fcmToken = null
            ),
            User(
                name = "Frank",
                email = "frank@example.com",
                profileImageUrl = null,
                bio = "I'm Frank",
                gender = Gender.Male,
                dob = "1980-03-25",
                fcmToken = null
            ),
            User(
                name = "Grace",
                email = "grace@example.com",
                profileImageUrl = null,
                bio = "I'm Grace",
                gender = Gender.Female,
                dob = "1997-11-30",
                fcmToken = null
            ),
            User(
                name = "Henry",
                email = "henry@example.com",
                profileImageUrl = null,
                bio = "I'm Henry",
                gender = Gender.Male,
                dob = "1987-04-12",
                fcmToken = null
            ),
            User(
                name = "Isabel",
                email = "isabel@example.com",
                profileImageUrl = null,
                bio = "I'm Isabel",
                gender = Gender.Female,
                dob = "1983-06-18",
                fcmToken = null
            ),
            User(
                name = "Jack",
                email = "jack@example.com",
                profileImageUrl = null,
                bio = "I'm Jack",
                gender = Gender.Male,
                dob = "1994-02-08",
                fcmToken = null
            )
        )

        val collRef = Firebase.firestore.usersColl()
        val batch = Firebase.firestore.batch()

        userList.forEach{ user ->
            batch.set(
                collRef.document(collRef.document().id),
                user
            )
        }
        batch.commit().await()
    }

    suspend fun saveDummyChannel() {
        val channel = Channel(
            imageUrl = null,
            type = Channel.Type.OneToOne,
            name = "Dummy",
            description = null,
            members = listOf(
                "PLwCv91l1ADl2yAsbTEo",
                "lXOPR9oGZ8KiURQ1xEqt"
            ),
            messages = emptyList()
        )

        Firebase.firestore.channelsColl()
            .add(channel)
            .await()
    }

    suspend fun channelQueryTest(){
        val channel = ChannelRepo().getOneToOneChannel(
            "PLwCv91l1ADl2yAsbTEo",
            "lXOPR9oGZ8KiURQ1xEqt"
        )
        Log.i("ChatAppDD", "channelQueryTest: $channel")
    }
}

