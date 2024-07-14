package com.binus.online.composematerial.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.binus.online.composematerial.preferences.AppPref
import com.binus.online.composematerial.preferences.AppPrefImpl

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")

class AppModule {
    companion object {
        @Volatile
        private lateinit var appPref: AppPref
        fun getAppPref(context: Context): AppPref {
            synchronized(AppPref::class.java) {
                if (!::appPref.isInitialized) {
                    appPref = AppPrefImpl(context, context.dataStore)
                }
            }
            return appPref
        }
    }
}