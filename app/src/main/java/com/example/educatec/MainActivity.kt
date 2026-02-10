package com.example.educatec

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.educatec.ui.theme.EducatecTheme
import com.example.educatec.ui.theme.navigation.NavGraph
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            EducatecTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}