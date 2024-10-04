package org.appaffinity.project

import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Menú Usuario") {
        MenuUsuario()
    }
}
