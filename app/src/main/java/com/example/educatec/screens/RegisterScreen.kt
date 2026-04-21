package com.example.educatec.screens

import android.widget.Toast
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
import com.example.educatec.network.request.RegisterRequest
import com.example.educatec.ui.theme.navigation.Routes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun RegisterScreen(navController: NavHostController) {

    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    // Configuración de colores forzada a Negro
    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = Color.Black,
        unfocusedTextColor = Color.Black,
        disabledTextColor = Color.Black,
        focusedLabelColor = Color.Black,
        unfocusedLabelColor = Color.Black,
        cursorColor = Color.Black,
        focusedBorderColor = Color.Black,
        unfocusedBorderColor = Color.Gray,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
    )

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEFF6FF)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(32.dp))

            // 📘 Título
            Text(
                text = "Crear cuenta",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Regístrate para comenzar",
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 👤 Nombre
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre", color = Color.Black) },
                singleLine = true,
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                colors = textFieldColors
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 📧 Correo
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico", color = Color.Black) },
                singleLine = true,
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                colors = textFieldColors
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 🔒 Contraseña
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña", color = Color.Black) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                colors = textFieldColors
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 🔒 Confirmar contraseña
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirmar contraseña", color = Color.Black) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                colors = textFieldColors
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 🔘 Botón registrar
            Button(
                onClick = {
                    if (nombre.isBlank() || email.isBlank() || password.isBlank()) {
                        Toast.makeText(context, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    if (password != confirmPassword) {
                        Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    isLoading = true
                    coroutineScope.launch {
                        try {
                            val registerRequest = RegisterRequest(nombre, email, password)
                            val mensaje = RetrofitClient.apiService.register(registerRequest)

                            withContext(Dispatchers.Main) {
                                Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show()
                                if (mensaje.contains("exitosamente")) { 
                                    navController.navigate(Routes.LOGIN)
                                }
                            }
                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                            }
                        } finally {
                            isLoading = false
                        }
                    }
                },
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {
                Text("Registrarse")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 🔙 Volver
            Text(
                text = "¿Ya tienes cuenta? Inicia sesión",
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable(enabled = !isLoading) {
                    navController.navigate(Routes.LOGIN)
                }
            )
        }

        if (isLoading) {
            CircularProgressIndicator()
        }
    }
}
