package com.renu.chatapp.feature.editProfile


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.renu.chatapp.domain.model.Gender
import com.renu.chatapp.domain.model.User
import com.renu.chatapp.feature.editProfile.comp.ProfileImage
import com.renu.chatapp.helper.MediaPickerDialogExt
import com.renu.chatapp.helper.navigate
import com.renu.chatapp.ui.Screen
import com.renu.chatapp.ui.comp.AddImageButton
import com.renu.chatapp.ui.comp.ImageState
import com.streamliners.base.taskState.comp.TaskLoadingButton
import com.streamliners.base.taskState.comp.whenError
import com.streamliners.compose.comp.select.RadioGroup
import com.streamliners.compose.comp.textInput.TextInputLayout
import com.streamliners.compose.comp.textInput.config.InputConfig
import com.streamliners.compose.comp.textInput.config.text
import com.streamliners.compose.comp.textInput.state.TextInputState
import com.streamliners.compose.comp.textInput.state.allHaveValidInputs
import com.streamliners.compose.comp.textInput.state.update
import com.streamliners.compose.comp.textInput.state.value
import com.streamliners.pickers.date.DatePickerDialog
import com.streamliners.pickers.date.ShowDatePicker
import com.streamliners.pickers.media.MediaPickerDialog
import com.streamliners.pickers.media.rememberMediaPickerDialogState
import com.streamliners.utils.DateTimeUtils
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    viewModel: EditProfileViewModel,
    email: String?,
    navController: NavHostController,
    showDatePicker: ShowDatePicker,
) {

    val mediaPickerDialogState = rememberMediaPickerDialogState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val userData by viewModel.userState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.loadUser()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Profile") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }, snackbarHost = {
            //TODO : SnackBar not visible when keyboard is open
            SnackbarHost(
                hostState = snackbarHostState
            )
        }) { paddingValues ->

        val imageState = remember {
            mutableStateOf<ImageState>(ImageState.Empty)
        }
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

        LaunchedEffect(userData) {
            userData?.let { user ->
                nameInput.update(user.name)
                bioInput.update(user.bio)
                gender.value = user.gender

            }
        }
        var genderError by remember { mutableStateOf(false) }
        var dob by remember { mutableStateOf<String?>("") }

        LaunchedEffect(key1 = gender.value) {
            if (gender.value != null) genderError = false
        }

        // Prefill values based on current user object
        LaunchedEffect(key1 = Unit){
            viewModel.getCurrentUser(
                onSuccess = {  user ->
                    user.run {
                        nameInput.update(name)
                        bioInput.update(bio)
                    }

                    gender.value = user.gender
                    dob = user.dob

                    user.profileImageUrl?.let { url ->
                        imageState.value = ImageState.Exists(url)
                    }

                    viewModel.email.value = user.email
                },
                onNotFount = {
                    viewModel.email.value = email ?: error("channelId argument not passed")
                }
            )
        }

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            imageState.value.data()?.let {
                ProfileImage(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    data = it,
                    onClick = {
                        MediaPickerDialogExt().launchMediaPickerDialogForImage(mediaPickerDialogState, scope, imageState)
                    }
                )
            } ?: run {
                AddImageButton(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        MediaPickerDialogExt().launchMediaPickerDialogForImage(mediaPickerDialogState, scope, imageState)
                    }
                )
            }


            TextInputLayout(state = nameInput)
            OutlinedTextField(modifier = Modifier.fillMaxSize(),
                value = viewModel.email.value,
                onValueChange = {},
                enabled = false,
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
                        labelExtractor = { it.name }
                    )
                    if (genderError) {
                        Text(text = "Required!")
                    }
                }
            }

            //TODO : Min, Max date
            // TODO : Make DOB compulsary
            // TODO : Change color of OutlinedTextField to enabled when disabled

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        showDatePicker(
                            DatePickerDialog.Params(
                                format = DateTimeUtils.Format.DATE_MONTH_YEAR_2,
                                prefill = dob?.ifBlank { null },
                                onPicked = { date ->
                                    dob = date
                                }
                            )
                        )
                    },
                value = userData?.dob ?: "",
                onValueChange = {},
                enabled = false,
                label = { Text(text = "Date of birth") })

            TextInputLayout(state = bioInput)

            TaskLoadingButton(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .align(Alignment.CenterHorizontally),
                state = viewModel.saveProfileTask,
                label = "Save",
                onClick = {
                    if (TextInputState.allHaveValidInputs(
                            nameInput, bioInput
                        ) && gender.value != null
                    ) {
                        gender.value?.let {
                            val user = User(
                                name = nameInput.value(),
                                email = viewModel.email.value,
                                profileImageUrl = null,
                                bio = bioInput.value(),
                                gender = it,
                                dob = dob,
                                fcmToken = null
                            )
                            viewModel.saveUser(
                                user = user,
                                image = imageState.value,
                                onSuccess = {
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Saved.")
                                    }
                                    navController.navigate(
                                        Screen.Home.route,
                                        Screen.EditProfile.format()
                                    )
                                },
                            )
                        }
                    }
                    if (gender.value == null) {
                        genderError = true
                    }
                }
            )
            viewModel.saveProfileTask.whenError {
                Text(text = "Error: $it")
            }
        }
    }
    MediaPickerDialog(
        state = mediaPickerDialogState,
        authority = "com.renu.chatapp.fileprovider"
    )
}

