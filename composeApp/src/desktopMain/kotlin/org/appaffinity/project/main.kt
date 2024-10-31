package org.appaffinity.project

import androidx.compose.material.Text
import androidx.compose.ui.window.*
import androidx.compose.runtime.*
import kotlinx.coroutines.*
import java.awt.*
import androidx.compose.ui.graphics.*

fun main() = application {
    var currentScreen by remember { mutableStateOf("estado_maquina") }

    // Usamos WindowState para maximizar la ventana desde el inicio
    val windowState = rememberWindowState(placement = WindowPlacement.Maximized)

    Window(
        onCloseRequest = ::exitApplication,
        undecorated = false,
        title = ("EGGARA PLUS"),
        state = windowState
    ) {
        val scope = rememberCoroutineScope()

        LaunchedEffect(Unit) {
            scope.launch(Dispatchers.Main) {
                // Maximizar ventana con AWT
                val awtWindow = Frame.getFrames().find { it.isActive }
                awtWindow?.apply {
                    extendedState = Frame.MAXIMIZED_BOTH
                }
            }
        }

        // Navegación basada en la pantalla actual
        when (currentScreen) {
            "estado_maquina" -> EstadoMaquina(onButtonClick = {
                // Cambia a la pantalla de MenuUsuario cuando se hace clic en el botón
                currentScreen = "menu_usuario"
            })
            "menu_usuario" -> MenuUsuario()
            else -> {
                // Manejar un estado desconocido, si es necesario
                Text(text = "Estado desconocido", color = Color.Red)
            }
        }
    }
}
