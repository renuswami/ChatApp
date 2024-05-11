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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.renu.chatapp.domain.model.Gender
import com.renu.chatapp.domain.model.User
import com.streamliners.compose.comp.select.RadioGroup
import com.streamliners.compose.comp.textInput.TextInputLayout
import com.streamliners.compose.comp.textInput.config.InputConfig
import com.streamliners.compose.comp.textInput.config.text
import com.streamliners.compose.comp.textInput.state.TextInputState
import com.streamliners.compose.comp.textInput.state.allHaveValidInputs
import com.streamliners.compose.comp.textInput.state.value
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    viewModel: EditProfileViewModel, email: String
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Profile") })
    }, snackbarHost = {
        //TODO : SnackBar not visible when keyboard is open
        SnackbarHost(
            hostState = snackbarHostState
        )
    }) { paddingValues ->

        val nameInput = remember {
            mutableStateOf(TextInputState(label = "Name", inputConfig = InputConfig.text {
                minLength = 3
                maxLength = 25
            }))
        }
        val bioInput = remember {
            mutableStateOf(TextInputState(label = "Bio", inputConfig = InputConfig.text {
                minLength = 3
                maxLength = 25
            }))
        }
        val gender = remember { mutableStateOf<Gender?>(null) }
        var genderError by remember { mutableStateOf(false) }

        LaunchedEffect(key1 = gender.value) {
            if (gender.value != null) genderError = false
        }

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            TextInputLayout(state = nameInput)
            OutlinedTextField(modifier = Modifier.fillMaxSize(),
                value = email,
                onValueChange = {},
                readOnly = true,
                label = { Text(text = "Email") })

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
                        labelExtractor = { it.name },
                    )
                    if (genderError) {
                        Text(text = "Requried!")
                    }
                }
            }


            TextInputLayout(state = bioInput)


            Button(modifier = Modifier
                .padding(top = 12.dp)
                .align(Alignment.CenterHorizontally),
                onClick = {
                    if (TextInputState.allHaveValidInputs(
                            nameInput, bioInput
                        ) && gender.value != null
                    ) {
                        gender.value?.let {
                            val user = User(
                                name = nameInput.value(),
                                email = email,
                                bio = nameInput.value(),
                                gender = it
                            )
                            viewModel.saveUser(user) {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Registration Seccussful.")
                                }
                            }
                        }
                    }
                    if (gender.value == null) {
                        genderError = true
                    }
                }

            ) {
                Text(text = "SAVE")
            }
        }
    }
}




