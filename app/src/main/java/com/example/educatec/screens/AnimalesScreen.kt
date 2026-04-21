package com.example.educatec.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

data class Animal(val nombre: String, val emoji: String, val color: Color)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalesScreen(navController: NavHostController) {
    val animales = listOf(
        Animal("León", "🦁", Color(0xFFFFF3E0)),
        Animal("Elefante", "🐘", Color(0xFFE1F5FE)),
        Animal("Mono", "🐒", Color(0xFFEFEBE9)),
        Animal("Jirafa", "🦒", Color(0xFFFFFDE7)),
        Animal("Cebra", "🦓", Color(0xFFF5F5F5)),
        Animal("Pingüino", "🐧", Color(0xFFECEFF1))
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Animales") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFFCCBC),
                    titleContentColor = Color.Black
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFFBE9E7)),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(animales) { animal ->
                AnimalItem(animal)
            }
        }
    }
}

@Composable
fun AnimalItem(animal: Animal) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = animal.color),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = animal.emoji, fontSize = 48.sp)
            Spacer(modifier = Modifier.width(24.dp))
            Text(
                text = animal.nombre,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}
