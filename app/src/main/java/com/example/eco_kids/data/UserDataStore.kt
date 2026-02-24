package com.example.eco_kids.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//se crea el archivo para guardar datos
val Context.userDataStore by preferencesDataStore(name = "user_preferences")

class UserDataStore (private val context: Context){
    //llaves aqui
    companion object {
        val USER_NAME_KEY = stringPreferencesKey("user_name")
        val USER_PET_KEY = intPreferencesKey("pet_index")
    }

    val userName: Flow<String?> = context.userDataStore.data
        .map{ preferences ->
            preferences[USER_NAME_KEY] ?: ""
        }

    val userPet: Flow<Int> = context.userDataStore.data
        .map{ preferences ->
            preferences[USER_PET_KEY] ?: 1
        }

    suspend fun saveUser (name: String, pet: Int){
        context.userDataStore.edit { preferences ->
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



