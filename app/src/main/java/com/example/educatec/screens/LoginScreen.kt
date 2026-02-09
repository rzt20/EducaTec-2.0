package com.example.educatec.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun LoginScreen(navController: NavHostController) {

    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFF6FF)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(32.dp))

        // 🌥️ Nubes
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text("☁️", fontSize = 48.sp)
            Text("☁️", fontSize = 64.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))


        Text(
            text = "Aprende jugando",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 🔐 Iniciar sesión
        Text(
            text = "Iniciar sesión",
            fontSize = 16.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(32.dp))

        // 👤 Usuario
        OutlinedTextField(
            value = usuario,
            onValueChange = { usuario = it },
            label = { Text("Usuario") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🔒 Contraseña
        OutlinedTextField(
            value = contrasena,
            onValueChange = { contrasena = it },
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 🔘 Botón
        Button(
            onClick = {
                navController.navigate("home")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Text("Entrar")
        }

        Spacer(modifier = Modifier.weight(1f))

        // 📝 Registro
        Row {
            Text(text = "¿Aún no tienes cuenta? ")
            Text(
                text = "Regístrate aquí",
                color = Color(0xFF1E88E5),
                modifier = Modifier.clickable {
                    // navController.navigate(Routes.REGISTER)
                }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 🌳 Árbol
        Text("🌳", fontSize = 64.sp)
    }
}