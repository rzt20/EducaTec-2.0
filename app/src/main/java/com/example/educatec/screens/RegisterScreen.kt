
package com.example.educatec.screens

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
import com.example.educatec.ui.theme.navigation.Routes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun RegisterScreen(navController: NavHostController) {

    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var registrationSuccess by remember { mutableStateOf(false) }
    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEFF6FF)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (registrationSuccess) {
                Spacer(modifier = Modifier.height(64.dp))
                Text(
                    text = "¡Registro Exitoso!",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D47A1)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Ahora puedes iniciar sesión con tu nueva cuenta.",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = {
                        navController.navigate(Routes.LOGIN) {
                            popUpTo(Routes.REGISTER) { inclusive = true }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                ) {
                    Text("Ir a Iniciar Sesión")
                }

            } else {
                Spacer(modifier = Modifier.height(32.dp))

                // 📘 Título
                Text(
                    text = "Crear cuenta",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Regístrate para comenzar",
                    color = Color.DarkGray
                )

                Spacer(modifier = Modifier.height(32.dp))

                // 👤 Nombre
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 📧 Correo
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo electrónico") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 🔒 Contraseña
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 🔒 Confirmar contraseña
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirmar contraseña") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                )

                errorMessage?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(horizontal = 32.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 🔘 Botón registrar
                Button(
                    onClick = {
                        errorMessage = null
                        if (nombre.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                            errorMessage = "Por favor, complete todos los campos."
                            return@Button
                        }
                        if (password != confirmPassword) {
                            errorMessage = "Las contraseñas no coinciden."
                            return@Button
                        }
                        if (password.length < 6) {
                            errorMessage = "La contraseña debe tener al menos 6 caracteres."
                            return@Button
                        }

                        isLoading = true
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val user = auth.currentUser
                                    val userMap = hashMapOf(
                                        "name" to nombre,
                                        "email" to email
                                    )
                                    user?.uid?.let {
                                        firestore.collection("users").document(it)
                                            .set(userMap)
                                            .addOnSuccessListener {
                                                isLoading = false
                                                registrationSuccess = true
                                            }
                                            .addOnFailureListener { e ->
                                                errorMessage = e.message ?: "Error al guardar datos."
                                                isLoading = false
                                            }
                                    }
                                } else {
                                    errorMessage = task.exception?.message ?: "Error de registro."
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
                    color = Color(0xFF1E88E5),
                    modifier = Modifier.clickable {
                        navController.navigate(Routes.LOGIN)
                    }
                )
            }
        }

        if (isLoading) {
            CircularProgressIndicator()
        }
    }
}