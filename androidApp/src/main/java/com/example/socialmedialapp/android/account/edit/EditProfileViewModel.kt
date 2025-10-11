package com.example.socialmedialapp.android.account.edit

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmedialapp.common.util.Result
import com.example.socialmedialapp.account.domain.usecase.GetProfileUseCase
import com.example.socialmedialapp.account.domain.usecase.UpdateProfileUseCase
import com.example.socialmedialapp.android.common.dummy_data.Profile
import com.example.socialmedialapp.android.common.util.Event
import com.example.socialmedialapp.android.common.util.EventBus
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class EditProfileViewModel(
    private val getProfileUseCase: GetProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val imageBytesReader: ImageBytesReader
): ViewModel() {

    var uiState: EditProfileUiState by mutableStateOf(EditProfileUiState())
        private set

    var bioTextFieldValue: TextFieldValue by mutableStateOf(TextFieldValue(text = ""))
        private set

    private fun fetchProfile(userId: Long) {
        viewModelScope.launch {
            uiState = uiState.copy(
                isLoading = true
            )
            delay(1000)

            when (val result = getProfileUseCase(profileId = userId).first()) {
                is Result.Error -> {
                    uiState = uiState.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )
                }


                is Result.Success -> {
                    uiState = uiState.copy(
                        isLoading = false,
                        profile = result.data
                    )
                }
            }

            bioTextFieldValue = bioTextFieldValue.copy(
                text = uiState.profile?.bio ?: "",
                selection = TextRange(index = uiState.profile?.bio?.length ?: 0)
            )
        }
    }

    private suspend fun uploadProfile(imageBytes: ByteArray?, profile: Profile){
        delay(1000)

        val result = updateProfileUseCase(
            profile = profile.copy(bio = bioTextFieldValue.text),
            imageBytes = imageBytes
        )

        when (result) {
            is Result.Error -> {
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = result.message
                )
            }


            is Result.Success -> {
                EventBus.send(Event.ProfileUpdated(result.data!!))
                uiState = uiState.copy(
                    isLoading = false,
                    uploadSucceed = true
                )
            }
        }
    }

    private fun readImageBytes(imageUri: Uri){
        val profile = uiState.profile ?: return

        uiState = uiState.copy(
            isLoading = true
        )

        viewModelScope.launch {
            if (imageUri == Uri.EMPTY){
                uploadProfile(imageBytes = null, profile = profile)
                return@launch
            }
            val result = imageBytesReader.readImageBytes(imageUri)
            when(result){
                is Result.Error -> {
                    uiState = uiState.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )
                }
                is Result.Success -> {
                    uploadProfile(imageBytes = result.data!!, profile = profile)
                }
            }
        }
    }

    fun onNameChange(inputName: String){
        uiState = uiState.copy(
            profile = uiState.profile?.copy(name = inputName)
        )
    }

    fun onBioChange(inputBio: TextFieldValue){
        bioTextFieldValue = bioTextFieldValue.copy(
            text = inputBio.text,
            selection = TextRange(index = inputBio.text.length)
        )
    }
    fun onUiAction(uiAction: EditProfileUiAction) {
        when (uiAction) {
            is EditProfileUiAction.FetchProfileAction -> fetchProfile(uiAction.userId)
            is EditProfileUiAction.UploadProfileAction -> readImageBytes(imageUri = uiAction.imageUri)
        }
    }
}


data class EditProfileUiState(
    val isLoading: Boolean = false,
    val profile: Profile? = null,
    val uploadSucceed: Boolean = false,
    val errorMessage: String? = null
)

sealed interface EditProfileUiAction{
    data class FetchProfileAction(val userId: Long): EditProfileUiAction
    class UploadProfileAction(val imageUri: Uri = Uri.EMPTY) : EditProfileUiAction
}