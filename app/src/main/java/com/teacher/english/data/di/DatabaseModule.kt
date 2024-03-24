package com.teacher.english.data.di

import android.content.Context
import androidx.datastore.dataStore
import androidx.datastore.preferences.preferencesDataStore
import com.teacher.english.data.datastore.CryptoManager
import com.teacher.english.data.repository.DataStorePreferenceStorage
import com.teacher.english.data.repository.PreferenceStorage
import com.teacher.english.data.serializer.UserProfileSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    private val Context.dataStore by preferencesDataStore(
        name = DataStorePreferenceStorage.PREFS_NAME,
    )

    private val Context.profileDataStore by dataStore(
        fileName = DataStorePreferenceStorage.USER_PROFILE_FILE_NAME,
        serializer = UserProfileSerializer(CryptoManager()),
    )

    @Singleton
    @Provides
    fun providePreferenceStorage(@ApplicationContext context: Context): PreferenceStorage =
        DataStorePreferenceStorage(context.dataStore, context.profileDataStore)
}