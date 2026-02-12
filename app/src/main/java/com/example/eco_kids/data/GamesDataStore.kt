package com.example.eco_kids.data

import android.R
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.eco_kids.data.UserDataStore.Companion.USER_NAME_KEY
import com.example.eco_kids.data.UserDataStore.Companion.USER_PET_KEY

val Context.gamesDataStore by preferencesDataStore(name = "games_preferences")


class GamesDataStore (private val context: Context) {
    //llaves aqui
    companion object {
        val MEMORAMA_SCORE_KEY = intPreferencesKey("memorama_score")
        val RECOLECTOR_SCORE_KEY = intPreferencesKey("recolector_score")
    }

    suspend fun saveScoreMemorama(score: int) {
        context.userDataStore.edit { preferences ->
            preferences[MEMORAMA_SCORE_KEY] = score
        }
    }

}