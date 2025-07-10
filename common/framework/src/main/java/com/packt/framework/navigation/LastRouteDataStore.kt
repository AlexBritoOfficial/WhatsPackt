package com.packt.framework.navigation

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "navigation_prefs")


object LastRouteDataStore {


    private val LAST_ROUTE_KEY = stringPreferencesKey("last_route")

    fun getLastRoute(context: Context): Flow<String?> {
        return context.dataStore.data.map { prefs ->
            prefs[LAST_ROUTE_KEY]
        }
    }

    suspend fun saveLastRoute(context: Context, route: String) {
        context.dataStore.edit { prefs ->
            prefs[LAST_ROUTE_KEY] = route
        }
    }

    suspend fun clearLastRoute(context: Context) {
        context.dataStore.edit { prefs ->
            prefs.remove(LAST_ROUTE_KEY)
        }
    }
}