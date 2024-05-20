package com.renu.chatapp.temp

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.renu.chatapp.data.remote.FirestoreCollections.userColl
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
                dob = "1990-01-01"
            ),
            User(
                name = "Bob",
                email = "bob@example.com",
                profileImageUrl = null,
                bio = "I'm Bob",
                gender = Gender.Male,
                dob = "1995-05-05"
            ),
            User(
                name = "Charlie",
                email = "charlie@example.com",
                profileImageUrl = null,
                bio = "I'm Charlie",
                gender = Gender.Male,
                dob = "1985-10-10"
            ),
            User(
                name = "Diana",
                email = "diana@example.com",
                profileImageUrl = null,
                bio = "I'm Diana",
                gender = Gender.Female,
                dob = "1988-09-15"
            ),
            User(
                name = "Eleanor",
                email = "eleanor@example.com",
                profileImageUrl = null,
                bio = "I'm Eleanor",
                gender = Gender.Female,
                dob = "1992-07-20"
            ),
            User(
                name = "Frank",
                email = "frank@example.com",
                profileImageUrl = null,
                bio = "I'm Frank",
                gender = Gender.Male,
                dob = "1980-03-25"
            ),
            User(
                name = "Grace",
                email = "grace@example.com",
                profileImageUrl = null,
                bio = "I'm Grace",
                gender = Gender.Female,
                dob = "1997-11-30"
            ),
            User(
                name = "Henry",
                email = "henry@example.com",
                profileImageUrl = null,
                bio = "I'm Henry",
                gender = Gender.Male,
                dob = "1987-04-12"
            ),
            User(
                name = "Isabel",
                email = "isabel@example.com",
                profileImageUrl = null,
                bio = "I'm Isabel",
                gender = Gender.Female,
                dob = "1983-06-18"
            ),
            User(
                name = "Jack",
                email = "jack@example.com",
                profileImageUrl = null,
                bio = "I'm Jack",
                gender = Gender.Male,
                dob = "1994-02-08"
            )
        )

        val collRef = Firebase.firestore.userColl()
        val batch = Firebase.firestore.batch()

        userList.forEach{ user ->
            batch.set(
                collRef.document(collRef.document().id),
                user
            )
        }
        batch.commit().await()
    }
}

