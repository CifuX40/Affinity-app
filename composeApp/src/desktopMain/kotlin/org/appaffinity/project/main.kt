package org.appaffinity.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        MenuUsuario(
            onNavigateToTecnico = { /* Implementar acción para ir a Técnico */ },
            onNavigateToIdioma = { /* Implementar acción para ir a Idioma */ },
            onNavigateToFechaHora = { /* Implementar acción para ir a Fecha y Hora */ },
            onNavigateToTarifas = { /* Implementar acción para ir a Tarifas */ },
            onNavigateToFicha = { /* Implementar acción para ir a Ficha */ }
        )
    }
}
