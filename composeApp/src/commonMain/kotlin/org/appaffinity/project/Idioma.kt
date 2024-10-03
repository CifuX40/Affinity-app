package org.affinity.project

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import org.appaffinity.project.BotonConImagenCustom
import org.appaffinity.project.ColorBotones
import org.appaffinity.project.Naranja

@Composable
fun IdiomaScreen(onAceptarClick: () -> Unit) {
    var idiomaSeleccionado by remember { mutableStateOf(Localization.currentLanguage) }

    // Usamos Box para permitir que los elementos se superpongan (imagen de fondo + contenido)
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource("fondo_de_pantalla.png"),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Contenido sobre la imagen de fondo
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Text(Localization.getString("selecciona_idioma"), fontSize = 30.sp, color = Naranja, fontWeight = FontWeight.Bold )

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de idiomas
            listOf("Español", "Inglés", "Francés", "Portugués").forEach { idioma ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { idiomaSeleccionado = idioma }
                        .padding(vertical = 8.dp), // Añado un poco de espacio vertical
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = idioma, fontSize = 18.sp, color = Color.Blue)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botón de Aceptar
            BotonConImagenCustom(
                imagen = painterResource("texto.png"),
                texto = Localization.getString("aceptar"),
                color = ColorBotones,
                onClick = {
                    Localization.setLanguage(idiomaSeleccionado)
                    onAceptarClick()
                }
            )
        }
    }
}
