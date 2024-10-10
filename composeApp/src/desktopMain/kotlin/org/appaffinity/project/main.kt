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
        undecorated = true, // Ocultamos la barra de título
        state = windowState // Aplicamos el estado maximizado
    ) {
        val scope = rememberCoroutineScope()

        LaunchedEffect(Unit) {
            scope.launch(Dispatchers.Main) {
                // Acceder a la ventana de AWT para asegurarse de que está maximizada
                val awtWindow = java.awt.Frame.getFrames().find { it.isActive }
                awtWindow?.apply {
                    extendedState = Frame.MAXIMIZED_BOTH // Maximizar la ventana en AWT
                }
            }
        }
        MenuUsuario()
    }
}