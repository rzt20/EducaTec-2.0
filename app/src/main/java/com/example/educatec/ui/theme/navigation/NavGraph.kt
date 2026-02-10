package com.example.educatec.ui.theme.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.educatec.screens.LoginScreen
import com.example.educatec.screens.HomeScreen
import com.example.educatec.screens.RegisterScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(navController)
        }
        composable(Routes.HOME) {
            HomeScreen()
        }
        composable(Routes.REGISTER) {
            RegisterScreen(navController)
        }
    }
}