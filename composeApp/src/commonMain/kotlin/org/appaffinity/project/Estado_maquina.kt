package org.appaffinity.project

import affinityapp.composeapp.generated.resources.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.unit.*
import kotlinx.coroutines.*
import org.jetbrains.compose.resources.painterResource
import androidx.compose.ui.graphics.*


sealed class Estado {
    object CargandoPantallas : Estado()
    object PreparandoTinta : Estado()
    object LeyendoArchivos : Estado()
    object ConectandoPeso : Estado()
    object Completado : Estado()
}

@Composable
fun EstadoMaquina() {
    var estadoActual by remember { mutableStateOf<Estado>(Estado.CargandoPantallas) }
    val historialEstados = remember { mutableStateListOf("Iniciando proceso...") }

    LaunchedEffect(estadoActual) {
        when (estadoActual) {
            is Estado.CargandoPantallas -> {
                delay(4000)
                historialEstados.add("Cargando pantallas...")
                estadoActual = Estado.PreparandoTinta
            }

            is Estado.PreparandoTinta -> {
                delay(4000)
                historialEstados.add("Preparando tinta...")
                estadoActual = Estado.LeyendoArchivos
            }

            is Estado.LeyendoArchivos -> {
                delay(4000)
                historialEstados.add("Leyendo archivos...")
                estadoActual = Estado.ConectandoPeso
            }

            is Estado.ConectandoPeso -> {
                delay(4000)
                historialEstados.add("Conectando peso...")
                estadoActual = Estado.Completado
            }

            is Estado.Completado -> {
                historialEstados.add("Proceso completado.")
            }
        }
    }

    // Contenedor principal que coloca la imagen de fondo y el texto superpuestos
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Imagen de fondo que ocupa toda la pantalla
        Image(
            painter = painterResource(Res.drawable.fondo_de_pantalla),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Texto superpuesto centrado en la pantalla
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            for (linea in historialEstados) {
                Text(
                    text = linea,
                    color = Color.White, // Color del texto en blanco
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}