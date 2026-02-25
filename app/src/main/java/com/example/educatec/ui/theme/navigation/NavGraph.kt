package com.example.educatec.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.educatec.screens.*

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController, startDestination = Routes.LOGIN
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(navController)
        }
        composable(
            route = Routes.HOME,
            arguments = listOf(navArgument("userName") { type = NavType.StringType })
        ) {
            val userName = it.arguments?.getString("userName") ?: ""
            HomeScreen(navController, userName)
        }
        composable(Routes.REGISTER) {
            RegisterScreen(navController)
        }
        composable(Routes.ABECEDARIO) {
            AbecedarioScreen(navController)
        }
        composable(Routes.NUMEROS) {
            NumerosScreen(navController)
        }
        composable(Routes.SILABAS) {
            SilabasScreen(navController)
        }
        composable(Routes.ANIMALES) {
            AnimalesScreen(navController)
        }
        composable(Routes.COLORES) {
            ColoresScreen(navController)
        }
        composable(Routes.JUEGOS) {
            JuegosScreen(navController)
        }
    }
}