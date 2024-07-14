package com.binus.online.composematerial.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class AppPrefImpl(context: Context, private val dataStore: DataStore<Preferences>) : AppPref {
    val IS_LOGIN = booleanPreferencesKey("IS_LOGIN")

    override fun setLogin(isLogin: Boolean): Unit = runBlocking {
        dataStore.edit {pref ->
            pref[IS_LOGIN] = isLogin
        }
    }

    override fun getLogin(): Boolean = runBlocking {
        dataStore.data.first()[IS_LOGIN] == true
    }
}