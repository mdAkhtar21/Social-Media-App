package com.example.socialmedialapp.di

import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.example.socialmedialapp.common.data.AndroidUserPreferences
import com.example.socialmedialapp.common.data.UserSettingsSerializer
import com.example.socialmedialapp.common.data.local.PREFERENCES_FILE_NAME
import com.example.socialmedialapp.common.data.local.UserPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val platformModule= module {
    single<UserPreferences> { AndroidUserPreferences(get()) }
    single{
        DataStoreFactory.create(
            serializer = UserSettingsSerializer,
            produceFile = {
                androidContext().dataStoreFile(
                    fileName = PREFERENCES_FILE_NAME
                )
            }
        )
    }
}