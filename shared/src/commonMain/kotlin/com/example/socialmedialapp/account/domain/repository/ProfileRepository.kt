package com.example.socialmedialapp.account.domain.repository

import com.example.socialmedialapp.account.domain.model.Profile
import com.example.socialmedialapp.common.util.Result
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun getProfile(profileId: Long): Flow<Result<Profile>>
    suspend fun updateProfile(profile: Profile, imageBytes: ByteArray?):Result<Profile>
}