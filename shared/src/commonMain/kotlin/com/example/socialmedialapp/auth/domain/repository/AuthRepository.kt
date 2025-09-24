package com.example.socialmedialapp.auth.domain.repository

import com.example.socialmedialapp.auth.domain.model.AuthResultData
import com.example.socialmedialapp.common.util.Result


internal interface AuthRepository {
    suspend fun signUp(
        name:String,
        email:String,
        password:String
    ): Result<AuthResultData>

    suspend fun singIn(email: String,password: String): Result<AuthResultData>
}