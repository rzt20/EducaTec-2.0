package com.example.educatec.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Clase de datos para tarjetas
data class HomeItem(
    val title: String,
    val subtitle: String,
    val circleText: String? = null,
    val circleIcon: ImageVector? = null,
    val bgColor: Color
)

@Composable
fun HomeScreen() {

    // Lista de todas las tarjetas con tonos pastel
    val items = listOf(
        HomeItem(title = "Abecedario", subtitle = "Aprende letras", circleText = "A", bgColor = Color(0xFFC5CAE9)), // pastel lila
        HomeItem(title = "Números", subtitle = "Cuenta y suma", circleText = "1", bgColor = Color(0xFFB2EBF2)), // pastel celeste
        HomeItem(title = "Sílabas", subtitle = "Forma palabras", circleText = "Ma", bgColor = Color(0xFFFFF9C4)), // pastel amarillo
        HomeItem(title = "Animales", subtitle = "Conoce la fauna", circleIcon = Icons.Filled.Pets, bgColor = Color(0xFFFFCCBC)), // pastel naranja
        HomeItem(title = "Colores", subtitle = "Descubre colores", circleIcon = Icons.Filled.ColorLens, bgColor = Color(0xFFD7CCC8)), // pastel gris suave
        HomeItem(title = "Juegos", subtitle = "Diviértete", circleIcon = Icons.Filled.VideogameAsset, bgColor = Color(0xFFC8E6C9)) // pastel verde
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0F7FA))
    ) {

        // Spacer arriba
        Spacer(modifier = Modifier.height(24.dp))

        // Encabezado con foto de perfil, mensaje y ajustes
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Foto de perfil (emoji)
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF00C896)),
                contentAlignment = Alignment.Center
            ) {
                Text("😃", fontSize = 24.sp)
            }

            // Mensaje de bienvenida
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Bienvenido", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("Disfruta aprendiendo 🎉", fontSize = 14.sp, color = Color.DarkGray)
            }

            // Icono de ajustes
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = "Ajustes",
                tint = Color.Black,
                modifier = Modifier
                    .size(32.dp)
                    .clickable { /* Acción de ajustes */ }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Grid de tarjetas con scroll
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(items) { item ->
                HomeCard(
                    title = item.title,
                    subtitle = item.subtitle,
                    circleText = item.circleText,
                    circleIconVector = item.circleIcon,
                    bgColor = item.bgColor
                )
            }
        }

        // Fondo tipo césped
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(Color(0xFF8BC34A))
        )
    }
}

@Composable
fun HomeCard(
    title: String,
    subtitle: String,
    circleText: String? = null,
    circleIconVector: ImageVector? = null,
    bgColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(120.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(bgColor)
            .clickable { }
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            if (circleText != null) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF00C896)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(circleText, color = Color.White, fontWeight = FontWeight.Bold)
                }
            } else if (circleIconVector != null) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFFC107)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(circleIconVector, contentDescription = null, tint = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(subtitle, fontSize = 12.sp, color = Color.DarkGray)
        }
    }
}