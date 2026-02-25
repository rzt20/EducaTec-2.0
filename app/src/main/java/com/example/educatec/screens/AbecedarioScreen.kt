package com.example.educatec.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AbecedarioScreen(navController: NavHostController) {
    // Definimos el abecedario en español, incluyendo la 'Ñ'
    val spanishAlphabet = ('A'..'N').toList() + 'Ñ' + ('O'..'Z').toList()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Abecedario") }, navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                }
            }, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFFC5CAE9), // Tono lila de la tarjeta original
                titleContentColor = Color.Black, navigationIconContentColor = Color.Black
            )
            )
        }) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 100.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFEFF6FF)), // Fondo claro
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(spanishAlphabet) { letter ->
                LetterCard(letter = letter)
            }
        }
    }
}

@Composable
fun LetterCard(letter: Char) {
    Card(
        modifier = Modifier
            .aspectRatio(1f) // Tarjetas cuadradas
            .clickable { /* TODO: Añadir acción al pulsar una letra */ },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Text(
                text = letter.toString(),
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3F51B5) // Color índigo para buen contraste
            )
        }
    }
}