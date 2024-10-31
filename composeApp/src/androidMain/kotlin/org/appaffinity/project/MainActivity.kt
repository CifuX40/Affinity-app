package org.appaffinity.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.material.*
import androidx.compose.ui.graphics.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var currentScreen by remember { mutableStateOf("estado_maquina") }

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
}