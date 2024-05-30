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
import com.renu.chatapp.feature.editProfile.comp.AddImageButton
import com.renu.chatapp.feature.editProfile.comp.ProfileImage
import com.renu.chatapp.helper.navigate
import com.renu.chatapp.ui.Screen
import com.streamliners.base.taskState.comp.TaskLoadingButton
import com.streamliners.base.taskState.comp.whenError
import com.streamliners.compose.comp.select.RadioGroup
import com.streamliners.compose.comp.textInput.TextInputLayout
import com.streamliners.compose.comp.textInput.config.InputConfig
import com.streamliners.compose.comp.textInput.config.text
import com.streamliners.compose.comp.textInput.state.TextInputState
import com.streamliners.compose.comp.textInput.state.allHaveValidInputs
import com.streamliners.compose.comp.textInput.state.value
import com.streamliners.pickers.date.DatePickerDialog
import com.streamliners.pickers.date.ShowDatePicker
import com.streamliners.pickers.media.FromGalleryType
import com.streamliners.pickers.media.MediaPickerDialog
import com.streamliners.pickers.media.MediaPickerDialogState
import com.streamliners.pickers.media.MediaType
import com.streamliners.pickers.media.PickedMedia
import com.streamliners.pickers.media.rememberMediaPickerDialogState
import com.streamliners.utils.DateTimeUtils.Format.DATE_MONTH_YEAR_2
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    viewModel: EditProfileViewModel,
    email: String,
    navController: NavHostController,
    showDatePicker: ShowDatePicker,
) {

    val mediaPickerDialogState = rememberMediaPickerDialogState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    Scaffold(topBar = {
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

        val image = remember {
            mutableStateOf<PickedMedia?>(null)
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

        var genderError by remember { mutableStateOf(false) }

        var dob by remember { mutableStateOf<String?>("") }

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

            val initImagePicker = {
                mediaPickerDialogState.value = MediaPickerDialogState.Visible(
                    type = MediaType.Image,
                    allowMultiple = false,
                    fromGalleryType = FromGalleryType.VisualMediaPicker
                ) { getList ->
                    scope.launch {
                        val list = getList()
                        list.firstOrNull()?.let {
                            image.value = it
                        }
                    }
                }
            }

            image.value?.let {
                ProfileImage(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    pickedMedia = it,
                    onClick = initImagePicker
                )
            } ?: run {
                AddImageButton(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = initImagePicker
                )
            }

            TextInputLayout(state = nameInput)
            OutlinedTextField(modifier = Modifier.fillMaxSize(),
                value = email,
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
                        labelExtractor = { it.name },
                    )
                    if (genderError) {
                        Text(text = "Requried!")
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
                                format = DATE_MONTH_YEAR_2,
                                prefill = dob?.ifBlank { null },
                                onPicked = { date ->
                                    dob = date
                                }
                            )
                        )
                    },
                value = dob ?: "",
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
                                email = email,
                                profileImageUrl = null,
                                bio = bioInput.value(),
                                gender = it,
                                dob = dob
                            )
                            viewModel.saveUser(
                                user = user,
                                image = image.value,
                                onSuccess = {
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Registration Successful.")
                                    }
                                    navController.navigate(Screen.Home.route, Screen.EditProfile.format())
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