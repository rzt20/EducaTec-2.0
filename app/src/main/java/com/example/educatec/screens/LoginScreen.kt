package com.example.educatec.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
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
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavHostController
import com.example.educatec.network.RetrofitClient
import com.example.educatec.network.request.LoginRequest
import com.example.educatec.ui.theme.navigation.Routes
import com.example.educatec.utils.BiometricHelper
import com.example.educatec.utils.PrefsManager
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
    
    val prefsManager = remember { PrefsManager(context) }
    val biometricHelper = remember { BiometricHelper(context) }

    // Configuración de colores forzada a Negro
    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = Color.Black,
        unfocusedTextColor = Color.Black,
        disabledTextColor = Color.Black,
        focusedLabelColor = Color.Black,
        unfocusedLabelColor = Color.Black,
        cursorColor = Color.Black,
        focusedBorderColor = Color.Black,
        unfocusedBorderColor = Color.Gray
    )

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEFF6FF)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(32.dp))

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
                label = { Text("Usuario", color = Color.Black) },
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
                value = contrasena,
                onValueChange = { contrasena = it },
                label = { Text("Contraseña", color = Color.Black) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                colors = textFieldColors
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        if (usuario.isNotBlank() && contrasena.isNotBlank()) {
                            isLoading = true
                            coroutineScope.launch {
                                try {
                                    val loginRequest = LoginRequest(usuario, contrasena)
                                    val usuarioLogueado = RetrofitClient.apiService.login(loginRequest)
                                    prefsManager.saveUserName(usuarioLogueado.nombre)

                                    withContext(Dispatchers.Main) {
                                        Toast.makeText(context, "Bienvenido ${usuarioLogueado.nombre}", Toast.LENGTH_LONG).show()
                                        navController.navigate("home/${usuarioLogueado.nombre}") {
                                            popUpTo(Routes.LOGIN) { inclusive = true }
                                        }
                                    }
                                } catch (e: Exception) {
                                    withContext(Dispatchers.Main) {
                                        Toast.makeText(context, "Error: Credenciales incorrectas", Toast.LENGTH_LONG).show()
                                    }
                                } finally {
                                    isLoading = false
                                }
                            }
                        }
                    },
                    enabled = !isLoading,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Entrar")
                }

                if (biometricHelper.isBiometricAvailable()) {
                    Spacer(modifier = Modifier.width(16.dp))
                    IconButton(
                        onClick = {
                            val activity = context as? FragmentActivity
                            val nombreGuardado = prefsManager.getUserName()

                            if (nombreGuardado != null) {
                                activity?.let {
                                    biometricHelper.showBiometricPrompt(
                                        activity = it,
                                        onSuccess = {
                                            navController.navigate("home/$nombreGuardado") {
                                                popUpTo(Routes.LOGIN) { inclusive = true }
                                            }
                                        },
                                        onError = { _, msg ->
                                            Toast.makeText(context, "Error: $msg", Toast.LENGTH_SHORT).show()
                                        }
                                    )
                                }
                            } else {
                                Toast.makeText(context, "Inicia sesión manualmente una vez", Toast.LENGTH_LONG).show()
                            }
                        },
                        enabled = !isLoading
                    ) {
                        Icon(Icons.Default.Fingerprint, contentDescription = "Huella", tint = Color(0xFF00C896))
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row {
                Text(text = "¿Aún no tienes cuenta? ", color = Color.Black)
                Text(
                    text = "Regístrate aquí",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { navController.navigate(Routes.REGISTER) }
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
