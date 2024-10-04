package org.appaffinity.project

import affinityapp.composeapp.generated.resources.Res
import affinityapp.composeapp.generated.resources.fecha_hora
import affinityapp.composeapp.generated.resources.ficha
import affinityapp.composeapp.generated.resources.fondo_de_pantalla
import affinityapp.composeapp.generated.resources.lenguaje
import affinityapp.composeapp.generated.resources.recaudacion
import affinityapp.composeapp.generated.resources.tarifas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.painterResource

val ColorBotones = Color(0xFF009EE0)

@Composable
fun MenuUsuario() {
    var currentScreen by remember { mutableStateOf("menu_usuario") }

    when (currentScreen) {
        "menu_usuario" -> {
            DisplayMenuUsuario(
                onNavigateToTecnico = {
                    println("Navegando a pantalla técnica")
                    currentScreen = "menu_tecnico"
                },
                onNavigateToIdioma = {
                    println("Navegando a pantalla de idioma")
                    currentScreen = "idioma_screen"
                },
                onNavigateToFechaHora = {
                    println("Navegando a pantalla de fecha y hora")
                    currentScreen = "ventana_fecha_hora"
                },
                onNavigateToTarifas = {
                    println("Navegando a pantalla de tarifas")
                    currentScreen = "tarifa_screen"
                },
                onNavigateToFicha = {
                    println("Navegando a pantalla de ficha")
                    currentScreen = "ficha_screen"
                }
            )
        }
        "idioma_screen" -> IdiomaScreen(onAceptarClick = { currentScreen = "menu_usuario" })
        "ventana_fecha_hora" -> FechaHora(onBack = { currentScreen = "menu_usuario" })
        "tarifa_screen" -> TarifaScreen(onAceptarClick = { currentScreen = "menu_usuario" })
        "ficha_screen" -> FichaScreen(onClose = { currentScreen = "menu_usuario" })
    }
}

@Composable
fun DisplayMenuUsuario(
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
            painter = painterResource(Res.drawable.fondo_de_pantalla),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Botones invisibles en las esquinas para navegación técnica
        Box(modifier = Modifier.size(50.dp).align(Alignment.TopEnd).clickable {
            if (currentStep == 0) {
                currentStep = 1
            }
        })
        Box(modifier = Modifier.size(50.dp).align(Alignment.TopStart).clickable {
            if (currentStep == 1) {
                currentStep = 2
            }
        })
        Box(modifier = Modifier.size(50.dp).align(Alignment.BottomStart).clickable {
            if (currentStep == 2) {
                currentStep = 3
            }
        })
        Box(modifier = Modifier.size(50.dp).align(Alignment.BottomEnd).clickable {
            if (currentStep == 3) {
                // Navegamos a la pantalla técnica
                onNavigateToTecnico()
                // Reiniciar el step después de navegar
                currentStep = 0
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
                        imagen = painterResource(Res.drawable.lenguaje),
                        texto = Localization.getString("idioma"),
                        color = ColorBotones,
                        onClick = { onNavigateToIdioma() }
                    )
                    BotonConImagenCustom(
                        imagen = painterResource(Res.drawable.recaudacion),
                        texto = Localization.getString("recaudacion"),
                        color = ColorBotones,
                        onClick = { /* Implementar la acción */ }
                    )
                    BotonConImagenCustom(
                        imagen = painterResource(Res.drawable.tarifas),
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
                        imagen = painterResource(Res.drawable.fecha_hora),
                        texto = Localization.getString("fecha_hora"),
                        color = ColorBotones,
                        onClick = { onNavigateToFechaHora() }
                    )
                    BotonConImagenCustom(
                        imagen = painterResource(Res.drawable.ficha),
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
