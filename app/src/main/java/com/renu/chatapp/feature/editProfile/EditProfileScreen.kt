package com.renu.chatapp.feature.editProfile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.renu.chatapp.model.Gender
import com.streamliners.compose.comp.select.RadioGroup
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    email: String
) {

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Profile") }
            )
        },
        snackbarHost = {
            //TODO : SnackBar not visible when keyboard is open
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { paddingValues ->

        var name by remember { mutableStateOf("") }
        var bio by remember { mutableStateOf("") }
        var nameError by remember { mutableStateOf(false) }
        var bioError by remember { mutableStateOf(false) }
        val gender = remember { mutableStateOf<Gender?>(null) }

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxSize(),
                value = name,
                onValueChange = {
                    name = it
                    nameError = it.isBlank()
                },
                label = { Text(text = "Name") },
                isError = nameError,
                supportingText = if (nameError) {
                    { Text(text = "Requried!") }
                } else {
                    null
                }
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxSize(),
                value = email,
                onValueChange = {},
                readOnly = true,
                label = { Text(text = "Email") }
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxSize(),
                value = bio,
                onValueChange = {
                    bio = it
                    bioError = it.isBlank()
                },
                label = { Text(text = "Bio") },
                isError = bioError,
                supportingText = if (bioError) {
                    { Text(text = "Requried!") }
                } else {
                    null
                }
            )
            Card {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 12.dp, vertical = 8.dp
                        )
                ) {
                    RadioGroup(
                        title = "Gender",
                        state = gender,
                        options = Gender.entries.toList(),
                        labelExtractor = { it.name }
                    )
                }
            }


            val scope = rememberCoroutineScope()
            Button(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    if (name.isBlank() && bio.isBlank()) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Your name is $name and bio is $bio")
                        }
                    } else {
                        scope.launch {
                            snackbarHostState.showSnackbar("Please input your name")
                        }
                    }
                    if (name.isBlank()) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Your name is $name")
                        }
                    } else {
                        scope.launch {
                            snackbarHostState.showSnackbar("Please input your name")
                        }
                    }
                    if (bio.isBlank()) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Bio is $bio")
                        }
                    } else {
                        scope.launch {
                            snackbarHostState.showSnackbar("Please input your bio")
                        }
                    }
                }) {
                Text(text = "SAVE")
            }
        }
    }
}

