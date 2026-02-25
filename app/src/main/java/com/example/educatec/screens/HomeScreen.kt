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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.educatec.ui.theme.navigation.Routes

// Clase de datos para tarjetas
data class HomeItem(
    val title: String,
    val subtitle: String,
    val route: String,
    val circleText: String? = null,
    val circleIcon: ImageVector? = null,
    val bgColor: Color
)

@Composable
fun HomeScreen(navController: NavHostController, userName: String) {

    var showMenu by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(0) }
    val navBarItems = listOf(
        "Inicio" to Icons.Filled.Home,
        "Logros" to Icons.Filled.VideogameAsset,
        "Perfil" to Icons.Filled.Person,
        "Ajustes" to Icons.Filled.Settings
    )

    // Lista de todas las tarjetas con tonos pastel
    val items = listOf(
        HomeItem(title = "Abecedario", subtitle = "Aprende letras", route = Routes.ABECEDARIO, circleText = "A", bgColor = Color(0xFFC5CAE9)), // pastel lila
        HomeItem(title = "Números", subtitle = "Cuenta y suma", route = Routes.NUMEROS, circleText = "1", bgColor = Color(0xFFB2EBF2)), // pastel celeste
        HomeItem(title = "Sílabas", subtitle = "Forma palabras", route = Routes.SILABAS, circleText = "Ma", bgColor = Color(0xFFFFF9C4)), // pastel amarillo
        HomeItem(title = "Animales", subtitle = "Conoce la fauna", route = Routes.ANIMALES, circleIcon = Icons.Filled.Pets, bgColor = Color(0xFFFFCCBC)), // pastel naranja
        HomeItem(title = "Colores", subtitle = "Descubre colores", route = Routes.COLORES, circleIcon = Icons.Filled.ColorLens, bgColor = Color(0xFFD7CCC8)), // pastel gris suave
        HomeItem(title = "Juegos", subtitle = "Diviértete", route = Routes.JUEGOS, circleIcon = Icons.Filled.VideogameAsset, bgColor = Color(0xFFC8E6C9)) // pastel verde
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                navBarItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItem == index,
                        onClick = { selectedItem = index },
                        icon = { Icon(item.second, contentDescription = item.first) },
                        label = { Text(item.first) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE0F7FA))
                .padding(innerPadding)
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
                    Text("Bienvenido, $userName", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    Text("Disfruta aprendiendo 🎉", fontSize = 14.sp, color = Color.Black)
                }

                // Icono de ajustes con menú desplegable
                Box {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "Ajustes",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(32.dp)
                            .clickable { showMenu = true }
                    )
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Cerrar sesión") },
                            onClick = {
                                showMenu = false
                                navController.navigate(Routes.LOGIN) {
                                    popUpTo(0) { inclusive = true }
                                }
                            }
                        )
                    }
                }
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
                        bgColor = item.bgColor,
                        onClick = { navController.navigate(item.route) }
                    )
                }
            }
        }
    }
}

@Composable
fun HomeCard(
    title: String,
    subtitle: String,
    circleText: String? = null,
    circleIconVector: ImageVector? = null,
    bgColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(120.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(bgColor)
            .clickable(onClick = onClick)
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
            Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
            Text(subtitle, fontSize = 12.sp, color = Color.Black)
        }
    }
}
