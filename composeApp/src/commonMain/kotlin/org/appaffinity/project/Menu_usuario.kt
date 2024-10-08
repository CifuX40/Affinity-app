package org.appaffinity.project

import affinityapp.composeapp.generated.resources.Res
import affinityapp.composeapp.generated.resources.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.painterResource

// Composable principal que representa el menú de usuario y maneja la navegación entre diferentes pantallas.
@Composable
fun MenuUsuario() {
    // Estado mutable que rastrea la pantalla actual.
    var currentScreen by remember { mutableStateOf("menu_usuario") }

    // Cambia la pantalla en función del valor de `currentScreen`.
    when (currentScreen) {
        "menu_usuario" -> {
            // Muestra el menú de usuario y define acciones para navegar a otras pantallas.
            DisplayMenuUsuario(
                onNavigateToTecnico = {
                    println("Navegando a pantalla técnica") // Imprime un mensaje de navegación a consola.
                    currentScreen = "MenuTecnico" // Cambia la pantalla actual.
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
        "MenuTecnico" -> MenuTecnico(onBack = { currentScreen = "menu_usuario" }) // Navega a la pantalla técnica.
        "idioma_screen" -> IdiomaScreen(onAceptarClick = { currentScreen = "menu_usuario" }) // Pantalla para seleccionar idioma.
        "ventana_fecha_hora" -> FechaHora(onBack = { currentScreen = "menu_usuario" }) // Pantalla para cambiar fecha y hora.
        "tarifa_screen" -> TarifaScreen(onAceptarClick = { currentScreen = "menu_usuario" }) // Pantalla para gestionar tarifas.
        "ficha_screen" -> FichaScreen(onClose = { currentScreen = "menu_usuario" }) // Pantalla para gestión de fichas.
    }
}

// Composable que muestra el menú de usuario con opciones para navegar a diferentes funciones.
@Composable
fun DisplayMenuUsuario(
    onNavigateToTecnico: () -> Unit, // Acción para navegar a la pantalla técnica.
    onNavigateToIdioma: () -> Unit, // Acción para navegar a la pantalla de idioma.
    onNavigateToFechaHora: () -> Unit, // Acción para navegar a la pantalla de fecha y hora.
    onNavigateToTarifas: () -> Unit, // Acción para navegar a la pantalla de tarifas.
    onNavigateToFicha: () -> Unit // Acción para navegar a la pantalla de ficha.
) {
    var currentStep by remember { mutableStateOf(0) } // Estado para gestionar los pasos de navegación secreta a la pantalla técnica.

    // Contenedor de la interfaz de usuario.
    Box(
        modifier = Modifier.fillMaxSize() // El box ocupa todo el tamaño de la pantalla.
    ) {
        // Imagen de fondo que llena la pantalla.
        Image(
            painter = painterResource(Res.drawable.fondo_de_pantalla),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // Escala la imagen para que cubra toda el área de la pantalla.
        )

        // Botones invisibles en las esquinas para una secuencia de clics que desbloquea la navegación técnica.
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
                onNavigateToTecnico() // Navega a la pantalla técnica después de completar la secuencia de clics.
                currentStep = 0 // Reinicia el estado de pasos.
            }
        })

        // Layout para los botones principales del menú.
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Título del menú de usuario.
            Text(
                text = Localization.getString("menu_usuario"),
                fontSize = 30.sp, // Tamaño de fuente grande.
                fontWeight = FontWeight.Bold, // Texto en negrita.
                color = Color(0xFFF5B130) // Color personalizado para el título.
            )

            Spacer(modifier = Modifier.height(32.dp)) // Espaciador entre el título y los botones.

            Column {
                // Fila de botones de la primera sección.
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly // Espacio equitativo entre los botones.
                ) {
                    // Botón para la pantalla de idioma.
                    BotonConImagenCustom(
                        imagen = painterResource(Res.drawable.lenguaje),
                        texto = Localization.getString("idioma"),
                        color = ColorBotones,
                        onClick = { onNavigateToIdioma() }
                    )
                    // Botón para la pantalla de recaudación (aún no implementado).
                    BotonConImagenCustom(
                        imagen = painterResource(Res.drawable.recaudacion),
                        texto = Localization.getString("recaudacion"),
                        color = ColorBotones,
                        onClick = { /* Implementar la acción */ }
                    )
                    // Botón para la pantalla de tarifas.
                    BotonConImagenCustom(
                        imagen = painterResource(Res.drawable.tarifas),
                        texto = Localization.getString("tarifas"),
                        color = ColorBotones,
                        onClick = { onNavigateToTarifas() }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp)) // Espaciador entre las filas de botones.

                // Fila de botones de la segunda sección.
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Botón para la pantalla de fecha y hora.
                    BotonConImagenCustom(
                        imagen = painterResource(Res.drawable.fecha_hora),
                        texto = Localization.getString("fecha_hora"),
                        color = ColorBotones,
                        onClick = { onNavigateToFechaHora() }
                    )
                    // Botón para la pantalla de fichas.
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

// Composable que representa un botón personalizado con imagen y texto.
@Composable
fun BotonConImagenCustom(imagen: Painter, texto: String, color: Color, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Caja contenedora del botón con imagen, que se hace clicable.
        Box(
            modifier = Modifier
                .size(100.dp) // Tamaño fijo para la imagen.
                .clickable(onClick = onClick) // Acción que se ejecuta al hacer clic.
        ) {
            // Imagen del botón.
            Image(painter = imagen, contentDescription = null, modifier = Modifier.fillMaxSize())
        }
        Spacer(modifier = Modifier.height(4.dp)) // Espacio entre la imagen y el texto.
        // Texto del botón.
        Text(
            text = texto,
            color = color, // Color personalizado para el texto.
            fontSize = 14.sp // Tamaño de fuente para el texto.
        )
    }
}
