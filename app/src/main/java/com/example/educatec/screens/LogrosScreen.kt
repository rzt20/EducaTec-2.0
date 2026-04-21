package com.example.educatec.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Logro(val titulo: String, val descripcion: String, val icono: String, val completado: Boolean)

@Composable
fun LogrosScreen() {
    val listaLogros = listOf(
        Logro("Primeros Pasos", "Completaste tu primera lección", "🐣", true),
        Logro("Maestro de Letras", "Aprendiste todo el abecedario", "📚", true),
        Logro("Matemático Junior", "Contaste hasta el 20", "🔢", false),
        Logro("Explorador Animal", "Conociste 10 animales", "🦁", false)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8F5E9)) // Verde suave
            .padding(16.dp)
    ) {
        Text(
            text = "Mis Logros 🏆",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2E7D32),
            modifier = Modifier.padding(vertical = 16.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(listaLogros) { logro ->
                LogroCard(logro)
            }
        }
    }
}

@Composable
fun LogroCard(logro: Logro) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (logro.completado) Color.White else Color(0xFFF5F5F5)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(logro.icono, fontSize = 32.sp)
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = logro.titulo,
                    fontWeight = FontWeight.Bold,
                    color = if (logro.completado) Color.Black else Color.Gray
                )
                Text(text = logro.descripcion, fontSize = 12.sp, color = Color.Gray)
            }
            if (logro.completado) {
                Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFD600))
            }
        }
    }
}
