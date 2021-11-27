package org.aristophanes

import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.compose.ui.graphics.Color
import org.aristophanes.houses.Volkstheater
import org.aristophanes.ui.AristophanesTheme
import org.aristophanes.ui.MainScreen


class MainActivity : ComponentActivity() {
    private val data = UserData()

    override fun onResume() {
        super.onResume()
        data.load(this)
    }

    override fun onPause() {
        super.onPause()
        data.save(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val events = Volkstheater.getEvents()
        setContent {
            AristophanesTheme {
                Surface(color = Color.DarkGray) {
                    MainScreen(data, events)
                }
            }
        }
    }
}