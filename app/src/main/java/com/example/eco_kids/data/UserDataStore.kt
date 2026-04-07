package com.example.eco_kids.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

//se crea el archivo para guardar datos
val Context.userDataStore by preferencesDataStore(name = "user_preferences")

class UserDataStore (private val context: Context){
    //llaves aqui
    companion object {
        val USER_ID_KEY = stringPreferencesKey("user_id")
        val USER_NAME_KEY = stringPreferencesKey("user_name")
        val USER_PET_KEY = intPreferencesKey("pet_index")
    }

    val userId: Flow<String?> = context.userDataStore.data
        .map { preferences ->
            preferences[USER_ID_KEY]
        }

    val userName: Flow<String?> = context.userDataStore.data
        .map{ preferences ->
            preferences[USER_NAME_KEY] ?: ""
        }

    val userPet: Flow<Int> = context.userDataStore.data
        .map{ preferences ->
            preferences[USER_PET_KEY] ?: 1
        }

    suspend fun saveUser(name: String, pet: Int) {
        context.userDataStore.edit { preferences ->

            // Crea ID en caso de que no exista
            if (preferences[USER_ID_KEY] == null) {
                preferences[USER_ID_KEY] = UUID.randomUUID().toString()
            }

            preferences[USER_NAME_KEY] = name
            preferences[USER_PET_KEY] = pet
        }
    }


    suspend fun setName (newName: String){
        context.userDataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = newName
        }
    }

    suspend fun setPet (newPet: Int){
        context.userDataStore.edit { preferences ->
            preferences[USER_PET_KEY] = newPet
        }
    }
}



