package org.appaffinity.project

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import org.affinity.project.Localization

val ColorBotones = Color(0xFF009EE0)

@Composable
fun MenuUsuario(
    onNavigateToTecnico: () -> Unit,
    onNavigateToIdioma: () -> Unit,
    onNavigateToFechaHora: () -> Unit,
    onNavigateToTarifas: () -> Unit,
    onNavigateToFicha: () -> Unit
) {
    var currentStep by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource("fondo_de_pantalla.png"),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Botones invisibles en las esquinas para navegación técnica
        Box(modifier = Modifier.size(50.dp).align(Alignment.TopEnd).clickable {
            if (currentStep == 0) currentStep = 1
        })
        Box(modifier = Modifier.size(50.dp).align(Alignment.TopStart).clickable {
            if (currentStep == 1) currentStep = 2
        })
        Box(modifier = Modifier.size(50.dp).align(Alignment.BottomStart).clickable {
            if (currentStep == 2) currentStep = 3
        })
        Box(modifier = Modifier.size(50.dp).align(Alignment.BottomEnd).clickable {
            if (currentStep == 3) {
                currentStep = 0
                onNavigateToTecnico()
            }
        })

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = Localization.getString("menu_usuario"),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF5B130)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    BotonConImagenCustom(
                        imagen = painterResource("lenguaje.png"),
                        texto = Localization.getString("idioma"),
                        color = ColorBotones,
                        onClick = { onNavigateToIdioma() }
                    )
                    BotonConImagenCustom(
                        imagen = painterResource("recaudacion.png"),
                        texto = Localization.getString("recaudacion"),
                        color = ColorBotones,
                        onClick = { /* Implementar la acción */ }
                    )
                    BotonConImagenCustom(
                        imagen = painterResource("tarifas.png"),
                        texto = Localization.getString("tarifas"),
                        color = ColorBotones,
                        onClick = { onNavigateToTarifas() }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    BotonConImagenCustom(
                        imagen = painterResource("fecha_hora.png"),
                        texto = Localization.getString("fecha_hora"),
                        color = ColorBotones,
                        onClick = { onNavigateToFechaHora() }
                    )
                    BotonConImagenCustom(
                        imagen = painterResource("ficha.png"),
                        texto = Localization.getString("ficha"),
                        color = ColorBotones,
                        onClick = { onNavigateToFicha() }
                    )
                }
            }
        }
    }
}

@Composable
fun BotonConImagenCustom(imagen: Painter, texto: String, color: Color, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clickable(onClick = onClick)
        ) {
            Image(painter = imagen, contentDescription = null, modifier = Modifier.fillMaxSize())
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = texto,
            color = color,
            fontSize = 14.sp
        )
    }
}
