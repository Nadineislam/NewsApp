package com.example.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.domain.models.News
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsDataStore @Inject constructor(@ApplicationContext context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "news_prefs")
    private val dataStore = context.dataStore

    private object PreferencesKeys {
        val LAST_NEWS = stringPreferencesKey("last_news")
    }

    val lastNewsFlow = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.LAST_NEWS]?.let { json ->
                try {
                    Gson().fromJson(json, News::class.java)
                } catch (e: JsonSyntaxException) {
                    null
                }
            }
        }

    suspend fun saveLastNews(newsResponse: News) {
        val json = Gson().toJson(newsResponse)
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.LAST_NEWS] = json
        }
    }
}