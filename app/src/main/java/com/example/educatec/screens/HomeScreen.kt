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
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
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

    var selectedItem by remember { mutableStateOf(0) }
    val navBarItems = listOf(
        "Inicio" to Icons.Filled.Home,
        "Logros" to Icons.Filled.Star,
        "Perfil" to Icons.Filled.Person,
        "Ajustes" to Icons.Filled.Settings
    )

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                navBarItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItem == index,
                        onClick = { selectedItem = index },
                        icon = { Icon(item.second, contentDescription = item.first) },
                        label = { Text(item.first) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF00C896),
                            indicatorColor = Color(0xFFE0F7FA)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedItem) {
                0 -> InicioSection(navController, userName)
                1 -> LogrosScreen()
                2 -> PerfilScreen(userName)
                3 -> AjustesSection(navController)
            }
        }
    }
}

@Composable
fun InicioSection(navController: NavHostController, userName: String) {
    val items = listOf(
        HomeItem(title = "Abecedario", subtitle = "Aprende letras", route = Routes.ABECEDARIO, circleText = "A", bgColor = Color(0xFFC5CAE9)),
        HomeItem(title = "Números", subtitle = "Cuenta y suma", route = Routes.NUMEROS, circleText = "1", bgColor = Color(0xFFB2EBF2)),
        HomeItem(title = "Sílabas", subtitle = "Forma palabras", route = Routes.SILABAS, circleText = "Ma", bgColor = Color(0xFFFFF9C4)),
        HomeItem(title = "Animales", subtitle = "Conoce la fauna", route = Routes.ANIMALES, circleIcon = Icons.Filled.Pets, bgColor = Color(0xFFFFCCBC)),
        HomeItem(title = "Colores", subtitle = "Descubre colores", route = Routes.COLORES, circleIcon = Icons.Filled.ColorLens, bgColor = Color(0xFFD7CCC8)),
        HomeItem(title = "Juegos", subtitle = "Diviértete", route = Routes.JUEGOS, circleIcon = Icons.Filled.VideogameAsset, bgColor = Color(0xFFC8E6C9))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0F7FA))
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF00C896)),
                contentAlignment = Alignment.Center
            ) {
                Text("😃", fontSize = 24.sp)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Bienvenido, $userName", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Text("Disfruta aprendiendo 🎉", fontSize = 14.sp, color = Color.Black)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

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

@Composable
fun AjustesSection(navController: NavHostController) {
    val context = LocalContext.current
    val versionName = remember {
        try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName
        } catch (e: Exception) {
            "1.0.0"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFECEFF1))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Configuración",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black, // Color negro para mayor visibilidad
            modifier = Modifier.padding(vertical = 32.dp)
        )

        // Foto de perfil interactiva
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color(0xFF00C896)),
            contentAlignment = Alignment.Center
        ) {
            Text("😃", fontSize = 48.sp)
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(32.dp)
                    .background(Color.White, CircleShape)
                    .clickable { /* Acción para cambiar foto */ },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.PhotoCamera, contentDescription = "Cambiar foto", modifier = Modifier.size(20.dp), tint = Color.Gray)
            }
        }
        
        TextButton(onClick = { /* Acción para cambiar foto */ }) {
            Text("Cambiar foto de perfil", color = Color(0xFF00C896))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Opciones de Ajustes
        SettingsItem(icon = Icons.Filled.Info, label = "Acerca de Educatec") {
            // Aquí podrías mostrar un diálogo con info de la app
        }

        Spacer(modifier = Modifier.height(8.dp))

        SettingsItem(
            icon = Icons.AutoMirrored.Filled.ExitToApp,
            label = "Cerrar sesión",
            color = Color.Red
        ) {
            navController.navigate(Routes.LOGIN) {
                popUpTo(0) { inclusive = true }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Versión de la App
        Text(
            text = "Versión $versionName",
            fontSize = 12.sp,
            color = Color.DarkGray, // Gris oscuro para que sea legible
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@Composable
fun SettingsItem(icon: ImageVector, label: String, color: Color = Color.Black, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = label, tint = color)
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = label, color = color, fontWeight = FontWeight.Medium)
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
