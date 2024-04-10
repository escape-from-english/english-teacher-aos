package com.teacher.english.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.teacher.english.data.model.UserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

interface PreferenceStorage {
    suspend fun clear()
    suspend fun clearLogIn()
    suspend fun setAccessToken(accessToken: String)
    suspend fun setUserProfile(userProfile: UserProfile)
    val accessToken: Flow<String>
    val userProfile: Flow<UserProfile>
}

@Singleton
class DataStorePreferenceStorage @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val profileDataStore: DataStore<UserProfile>,
): PreferenceStorage {
    companion object {
        const val PREFS_NAME = "settings"
        const val USER_PROFILE_FILE_NAME = "profile.json"
    }

    object PreferencesConstant {
        val KEY_NAME_DATA = stringPreferencesKey("pref_name_data")
        val KEY_WEEK_DATA = intPreferencesKey("pref_week_data")
        val KEY_TEAM_ID = intPreferencesKey("pref_team_id")
        val KEY_ACCESS_TOKEN = stringPreferencesKey("pref_access_token")
    }

    private suspend fun FlowCollector<Preferences>.handleDefaultException(throwable: Throwable) =
        when (throwable) {
            is IOException -> emit(emptyPreferences())
            else -> throw throwable
        }

    override suspend fun clear() {
        dataStore.edit {
            it.clear()
        }
    }

    override suspend fun clearLogIn() {
        profileDataStore.updateData { UserProfile() }
    }
    override suspend fun setAccessToken(accessToken: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesConstant.KEY_ACCESS_TOKEN] = accessToken
        }
    }
    override suspend fun setUserProfile(userProfile: UserProfile) {
        dataStore.edit { preferences ->
            preferences[PreferencesConstant.KEY_NAME_DATA] = userProfile.name
            preferences[PreferencesConstant.KEY_WEEK_DATA] = userProfile.weekNumber ?: 0
            preferences[PreferencesConstant.KEY_TEAM_ID] = userProfile.selectedTeamId ?: 0
        }
    }
    override val accessToken: Flow<String>
        get() = dataStore.data.catch { handleDefaultException(it) }.map { preferences ->
            preferences[PreferencesConstant.KEY_ACCESS_TOKEN] ?: ""
        }.distinctUntilChanged()
    override val userProfile: Flow<UserProfile>
        get() = dataStore.data.catch { handleDefaultException(it) }.map { preferences ->
            UserProfile(
                name = preferences[PreferencesConstant.KEY_NAME_DATA] ?: "",
                weekNumber = preferences[PreferencesConstant.KEY_WEEK_DATA] ?: 0,
                selectedTeamId = preferences[PreferencesConstant.KEY_TEAM_ID] ?: 0
            )
        }.distinctUntilChanged()
}
