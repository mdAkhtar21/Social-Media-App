// SignUpViewModel.kt
package com.example.socialmedialapp.android.auth.signUp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmedialapp.android.common.datastore.UserSettings
import com.example.socialmedialapp.android.common.datastore.toUserSettings
import com.example.socialmedialapp.auth.domain.usecase.SignUpUseCase
import com.example.socialmedialapp.common.util.Result
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val signUpUseCase: SignUpUseCase,
    private val dataStore:DataStore<UserSettings>
) : ViewModel() {
    var uiState by mutableStateOf(SignUpUIState())
        private set

    fun updateUserName(input: String) {
        uiState = uiState.copy(username = input)
    }
    fun signUp(){
        viewModelScope.launch {
            uiState=uiState.copy(isAuthenticating = true)
            val authResultData=signUpUseCase(uiState.email,uiState.username,uiState.password)
            uiState=when(authResultData){
                is Result.Error->{
                    uiState.copy(
                        isAuthenticating = false,
                        authErrorMessage = authResultData.message
                    )
                }
                is Result.Success->{
                    dataStore.updateData {
                        authResultData.data!!.toUserSettings()
                    }
                    uiState.copy(
                        isAuthenticating = false,
                        authenticationSucceed = true
                    )
                }
            }
        }
    }
    fun updateEmail(input: String) {
        uiState = uiState.copy(email = input)
    }

    fun updatePassword(input: String) {
        uiState = uiState.copy(password = input)
    }
}

data class SignUpUIState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val isAuthenticating:Boolean=false,
    val authErrorMessage:String?=null,
    val authenticationSucceed:Boolean=false
)
