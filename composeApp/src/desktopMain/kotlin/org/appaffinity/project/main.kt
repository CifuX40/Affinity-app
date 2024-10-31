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
                val awtWindow = Frame.getFrames().find { it.isActive }
                awtWindow?.apply {
                    extendedState = Frame.MAXIMIZED_BOTH
                }
            }
        }
        EstadoMaquina()
    }
}