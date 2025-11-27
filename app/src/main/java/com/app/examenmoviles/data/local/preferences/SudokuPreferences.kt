package com.app.examenmoviles.data.local.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.app.examenmoviles.data.local.model.SavedSudokuGame
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SudokuPreferences
    @Inject
    constructor(
        @ApplicationContext context: Context,
        private val gson: Gson,
    ) {
        private val prefs: SharedPreferences =
            context.getSharedPreferences(SudokuPreferencesConstants.PREF_NAME, Context.MODE_PRIVATE)

        fun saveGame(game: SavedSudokuGame) {
            prefs
                .edit {
                    putString(SudokuPreferencesConstants.KEY_GAME_STATE, gson.toJson(game))
                }
        }

        fun loadSavedGame(): SavedSudokuGame? {
            val json =
                prefs.getString(SudokuPreferencesConstants.KEY_GAME_STATE, null)
                    ?: return null

            val type = object : TypeToken<SavedSudokuGame>() {}.type
            return gson.fromJson(json, type)
        }

        fun clearSavedGame() {
            prefs
                .edit {
                    remove(SudokuPreferencesConstants.KEY_GAME_STATE)
                }
        }
    }
