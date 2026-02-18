package com.example.eco_kids.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.gamesDataStore by preferencesDataStore(name = "games_preferences")


class GamesDataStore (private val context: Context) {

    //funcion para guardar puntaje
    suspend fun saveScore (userName: String,
                           gameName: String,
                           score:Int){
        //llave dinámica para poder usar por usuario
        val key = intPreferencesKey("${gameName}_score_$userName")

        context.gamesDataStore.edit { preferences ->
            val currentScore = preferences[key] ?: 0

            if (score > currentScore){
            preferences[key] = score
            }
        }
    }

    fun getBestScore (
        userName: String,
        gameName: String
    ): Flow<Int>{
        //se obtiene la llave de la misma manera (dinamica)
        val key = intPreferencesKey("${gameName}_score_$userName")

        return context.gamesDataStore.data.map { preferences ->
            preferences[key] ?: 0
        }
    }


}