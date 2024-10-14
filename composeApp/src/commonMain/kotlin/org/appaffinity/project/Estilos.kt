package org.appaffinity.project

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*

// Definición de colores
val Negro = Color(0xFF1A171B)
val AzulCian = Color(0xFF009EE0)
val Blanco = Color(0xFFFFFFFF)
val Naranja = Color(0xFFF5B130)
val ColorBotones = Color(0xFF009EE0)

@Composable
fun Boton_Naranja(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier // Permite modificar el botón si es necesario
) {
    androidx.compose.material.Button(
        onClick = onClick,
        modifier = modifier.padding(8.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Naranja) // Fondo color naranja
    ) {
        Text(text, color = Color.White) // Color del texto del botón
    }
}