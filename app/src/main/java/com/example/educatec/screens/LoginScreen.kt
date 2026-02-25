package com.example.educatec.screens

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.educatec.network.RetrofitClient
import com.example.educatec.network.request.LoginRequest
import com.example.educatec.ui.theme.navigation.Routes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LoginScreen(navController: NavHostController) {

    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),


            )

            Spacer(modifier = Modifier.height(16.dp))

            // 🔒 Contraseña
            OutlinedTextField(
                value = contrasena,
                onValueChange = { contrasena = it },
                label = { Text("Contraseña") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 🔘 Botón
            Button(
                onClick = {
                    if (usuario.isNotBlank() && contrasena.isNotBlank()) {
                        isLoading = true
                        coroutineScope.launch {
                            try {
                                val loginRequest = LoginRequest(usuario, contrasena)
                                val usuarioLogueado = RetrofitClient.apiService.login(loginRequest)

                                withContext(Dispatchers.Main) {
                                    Toast.makeText(context, "Bienvenido ${usuarioLogueado.nombre}", Toast.LENGTH_LONG).show()
                                    navController.navigate("home/${usuarioLogueado.nombre}") {
                                        // Limpia la pila de navegación para que no se pueda volver al login
                                        popUpTo(Routes.LOGIN) { inclusive = true }
                                    }
                                }
                            } catch (e: Exception) {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(context, "Error: Credenciales incorrectas o problema del servidor", Toast.LENGTH_LONG).show()
                                }
                            } finally {
                                isLoading = false
                            }
                        }
                    } else {
                        Toast.makeText(context, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                    }
                },
                enabled = !isLoading,
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
                    modifier = Modifier.clickable(enabled = !isLoading) {
                        navController.navigate(Routes.REGISTER)
                    }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 🌳 Árbol
            Text("🌳", fontSize = 64.sp)
        }
        if (isLoading) {
            CircularProgressIndicator()
        }
    }
}
