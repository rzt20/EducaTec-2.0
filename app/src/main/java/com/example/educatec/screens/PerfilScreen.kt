package com.example.educatec.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PerfilScreen(userName: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3E5F5)) // Lila suave
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        
        // Avatar
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color(0xFFBA68C8)),
            contentAlignment = Alignment.Center
        ) {
            Text("😃", fontSize = 64.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = userName,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF4A148C)
        )
        Text(text = "Estudiante Estrella ⭐", fontSize = 16.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(32.dp))

        // Info Cards
        InfoCard(icon = Icons.Default.Person, label = "Usuario", value = userName)
        InfoCard(icon = Icons.Default.Email, label = "Correo", value = "$userName@educatec.com")
        
        Spacer(modifier = Modifier.weight(1f))
        
        Button(
            onClick = { /* Editar perfil */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C27B0)),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
        ) {
            Text("Editar Perfil")
        }
    }
}

@Composable
fun InfoCard(icon: ImageVector, label: String, value: String) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = Color(0xFF9C27B0))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = label, fontSize = 12.sp, color = Color.Gray)
                Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}
