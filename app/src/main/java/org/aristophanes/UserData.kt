package org.aristophanes

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import org.aristophanes.houses.Gaertnerplatz
import org.aristophanes.houses.Kammerspiele
import org.aristophanes.houses.Residenztheater
import org.aristophanes.houses.Volkstheater
import java.io.FileNotFoundException

class UserData {
    val houses = mutableStateListOf(Volkstheater, Residenztheater, Gaertnerplatz, Kammerspiele)
    val favorites = mutableStateListOf<String>()

    fun load(context: Context) {
        try {
            context.openFileInput("favorites.txt").use {
                favorites.clear()
                favorites.addAll(it.bufferedReader().lineSequence())
            }
        } catch (ex: FileNotFoundException) {
            System.err.println("First use of the Application")
        }
    }

    fun save(context: Context) {
        context.openFileOutput("favorites.txt", Context.MODE_PRIVATE).use {
            favorites.joinTo(it.bufferedWriter(), "\n")
        }
    }
}