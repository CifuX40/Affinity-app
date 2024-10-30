package org.appaffinity.project

import androidx.activity.*
import androidx.activity.compose.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.content.Intent // Asegúrate de importar Intent desde android.content
import com.android.tools.build.bundletool.model.Bundle

class EstadoMaquina : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EstadoMaquinaContent()
        }
    }

    @Composable
    fun EstadoMaquinaContent() {
        val messages = listOf(
            "Cargando pantallas",
            "Preparando tinta",
            "Leyendo archivos",
            "Conectando peso"
        )

        var currentMessage by remember { mutableStateOf("") }
        var showButton by remember { mutableStateOf(false) }
        var isError by remember { mutableStateOf(false) }

        // Muestra el mensaje en la UI
        LaunchedEffect(Unit) {
            for (message in messages) {
                delay(5000) // Espera 5 segundos
                currentMessage = "$message - OK"
                showButton = true // Muestra el botón después de cada mensaje
            }
        }

        // Botón para mostrar el mensaje
        Button(onClick = {
            isError = false
            showButton = false
            currentMessage = ""
            lifecycleScope.launch {
                for (message in messages) {
                    delay(5000) // Espera 5 segundos
                    currentMessage = "$message - OK"
                    showButton = true // Muestra el botón después de cada mensaje
                }
            }
        }) {
            Text("Mostrar OK")
        }

        // Botón para mostrar error
        Button(onClick = {
            isError = true
            showButton = false
            currentMessage = "Error: ha ocurrido un problema."
        }) {
            Text("Mostrar Error")
        }

        // Mensaje actual
        Text(text = currentMessage)

        // Botón para ir a Menu_usuario.kt
        if (showButton && !isError) {
            Button(onClick = {
                // Navegar a Menu_usuario.kt
                val intent = Intent(this@EstadoMaquina, Menu_usuario::class.java)
                startActivity(intent)
            }) {
                Text("Ir a Menú de Usuario")
            }
        }
    }
}