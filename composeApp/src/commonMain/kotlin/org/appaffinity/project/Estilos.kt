package org.appaffinity.project

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*

// Definición de colores
val Negro = Color(0xFF000000)
val AzulCian = Color(0xFF009EE0)
val Blanco = Color(0xFFFFFFFF)
val Naranja = Color(0xFFFFA500)

@Composable
fun Boton_Naranja(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.padding(8.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Naranja)

    ) {
        Text(text, color = Color.White)
    }
}