package org.appaffinity.project

import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Menú Usuario") {
        MenuUsuario()
    }
}

@Composable
fun MenuUsuario() {
    var currentScreen by remember { mutableStateOf("menu_usuario") }

    when (currentScreen) {
        "menu_usuario" -> MenuUsuario(
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
        "menu_tecnico" -> MenuTecnico(
            onBack = TODO(),
            onNavigateToUsuario = TODO()
        ) 
        "idioma_screen" -> IdiomaScreen(
            onAceptarClick = TODO()
        )
        "ventana_fecha_hora" -> FechaHora() 
        "tarifa_screen" -> TarifaScreen(
            onClose = TODO()
        ) 
        "ficha_screen" -> FichaScreen(
            onClose = TODO()
        ) 
    }
}
