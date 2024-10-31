package org.appaffinity.project

import affinityapp.composeapp.generated.resources.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.graphics.*
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource

@Composable
fun EstadoMaquina(onButtonClick: () -> Unit) {
    // Estado para controlar los mensajes a mostrar
    val mensajes = remember { mutableStateListOf("Cargando pantallas...") }

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
            verticalArrangement = Arrangement.Center // Centrar verticalmente
        ) {
            // Mostrar todos los textos como etiquetas
            for ((index, mensaje) in mensajes.withIndex()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween // Espaciado entre el texto y "Ok"
                ) {
                    Text(
                        text = mensaje,
                        fontSize = 30.sp,
                        color = Color.Black // Cambiar a negro directamente
                    )
                    // Mostrar "Ok" solo si no es el último mensaje
                    if (index < mensajes.size - 1) {
                        Text(
                            text = "Ok",
                            fontSize = 30.sp,
                            color = Color.Black // Cambiar a negro directamente
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp)) // Espacio entre las etiquetas
            }

            // Mostrar el botón solo si el último mensaje es "Proceso completado."
            if (mensajes.last() == Localization.getString("Proceso completado.")) {
                Spacer(modifier = Modifier.height(16.dp)) // Espacio antes del botón
                Button(
                    onClick = onButtonClick,
                    modifier = Modifier.align(Alignment.CenterHorizontally) // Centrar el botón
                ) {
                    Text("Continuar") // Texto del botón
                }
            }
        }

        LaunchedEffect(Unit) {
            // Espera y agrega los mensajes uno por uno
            delay(4000) // Espera 4 segundos antes de añadir el siguiente mensaje
            mensajes.add(Localization.getString("Preparando tinta..."))
            delay(4000)
            mensajes.add(Localization.getString("Leyendo archivos..."))
            delay(4000)
            mensajes.add(Localization.getString("Conectando peso..."))
            delay(4000)
            mensajes.add(Localization.getString("Proceso completado.")) // No tendrá "Ok"
        }
    }
}
