package org.appaffinity.project

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Definición de colores
val Negro = Color(0xFF1A171B)
val AzulCian = Color(0xFF009EE0)
val Blanco = Color(0xFFFFFFFF)
val Naranja = Color(0xFFF5B130)

// Tipografía
val typography = Typography(
    defaultFontFamily = FontFamily.Default,
    h1 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        color = Blanco // Color del encabezado
    ),
    body1 = TextStyle(
        color = Blanco,
        fontSize = 16.sp
    ),
    button = TextStyle(
        color = Blanco,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    )
)

// Formas
val shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(16.dp)
)

// Tema personalizado
@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = lightColors(
            primary = AzulCian,
            secondary = Naranja,
            background = Negro,
            surface = Negro,
            onPrimary = Blanco,
            onSecondary = Blanco,
            onBackground = Blanco,
            onSurface = Blanco
        ),
        typography = typography,
        shapes = shapes,
        content = content
    )
}
