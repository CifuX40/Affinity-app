package org.appaffinity.project

import androidx.compose.ui.window.*
import androidx.compose.runtime.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.awt.Frame

fun main() = application {
    // Usamos WindowState para maximizar la ventana desde el inicio
    val windowState = rememberWindowState(placement = WindowPlacement.Maximized)

    Window(
        onCloseRequest = ::exitApplication,
        undecorated = false, title = ("EGGARA PLUS"),
        state = windowState
    ) {
        val scope = rememberCoroutineScope()

        LaunchedEffect(Unit) {
            scope.launch(Dispatchers.Main) {
                // Maximizar ventana con AWT
                val awtWindow = java.awt.Frame.getFrames().find { it.isActive }
                awtWindow?.apply {
                    extendedState = Frame.MAXIMIZED_BOTH
                }
            }
        }

        // Iniciar la pantalla directamente en MenuUsuario
        MenuUsuario()
    }
}
