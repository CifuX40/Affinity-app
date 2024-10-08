package org.appaffinity.project

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.material.Typography
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Definición de colores
val Negro = Color(0xFF1A171B)
val AzulCian = Color(0xFF009EE0)
val Blanco = Color(0xFFFFFFFF)
val Naranja = Color(0xFFF5B130)
val ColorBotones = Color(0xFF009EE0)

// Tipografía
val typography = Typography(
    defaultFontFamily = FontFamily.Default,
    h1 = TextStyle(fontWeight = FontWeight.Bold, fontSize = 30.sp),
    // Define otros estilos de texto aquí
)

// Formas
val shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)