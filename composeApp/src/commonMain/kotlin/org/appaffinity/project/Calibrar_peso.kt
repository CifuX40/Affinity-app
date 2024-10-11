package org.appaffinity.project

import affinityapp.composeapp.generated.resources.Res
import affinityapp.composeapp.generated.resources.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.*
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource

// Composable que representa la pantalla para calibrar el peso, permitiendo al usuario ingresar un peso y comenzar la calibración.
@Composable
fun CalibrarPeso(onBack: () -> Unit) {
    var peso by remember { mutableStateOf("") } // Variable que almacena el valor del peso ingresado por el usuario
    var calibrando by remember { mutableStateOf(false) } // Indica si el proceso de calibración está en curso
    var mensaje by remember { mutableStateOf("") } // Mensaje que muestra el estado de la calibración

    // Diseño de la pantalla completa con imagen de fondo
    Box(modifier = Modifier.fillMaxSize()) {
        // Imagen de fondo que cubre toda la pantalla
        Image(
            painter = painterResource(Res.drawable.fondo_de_pantalla),
            contentDescription = "Fondo de Pantalla",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // Escala la imagen para que cubra todo el espacio
        )

        // Contenido principal centrado en la pantalla
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, // Alinea el contenido en el centro horizontalmente
            verticalArrangement = Arrangement.Center, // Coloca los elementos centrados verticalmente
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp) // Añade padding de 16dp alrededor del contenido
                .background(Color.Transparent) // Fondo transparente para el contenido
        ) {
            // Título de la pantalla
            Text(text = "Calibrar Peso", fontSize = 24.sp, color = Color(0xFF009EE0)) // Título en azul cian

            // Campo de texto para que el usuario ingrese el valor del peso
            BasicTextField(
                value = peso, // El valor del campo es la variable `peso`
                onValueChange = { peso = it }, // Actualiza `peso` cuando el usuario ingresa un valor
                modifier = Modifier
                    .background(Color.LightGray) // Fondo gris claro para el campo de texto
                    .padding(8.dp) // Espaciado interno del campo de texto
                    .fillMaxWidth() // El campo de texto ocupa todo el ancho disponible
            )

            // Botón para iniciar el proceso de calibración
            Button(
                onClick = {
                    calibrando = true // Cambia el estado a "calibrando" cuando se pulsa el botón
                    mensaje = "Calibrando..." // Muestra el mensaje de calibración en proceso
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Naranja) // Botón de color naranja
            ) {
                Text("Calibrar", color = Color.Black) // Texto negro en el botón
            }

            // Uso de corutinas para simular una calibración de 5 segundos
            LaunchedEffect(calibrando) {
                if (calibrando) {
                    delay(5000) // Simula un retraso de 5 segundos
                    mensaje = "Calibración completada" // Mensaje que indica que la calibración ha finalizado
                    calibrando = false // Cambia el estado de calibración a "falso"
                }
            }

            // Mostrar el estado de la calibración según el valor de `calibrando` y `mensaje`
            if (calibrando) {
                Text(text = mensaje, color = Color(0xFF009EE0)) // Muestra el mensaje "Calibrando..." en azul cian
            } else if (mensaje.isNotEmpty()) {
                Text(text = mensaje, color = Color.Green) // Muestra el mensaje de "Calibración completada" en verde
            }

            Spacer(modifier = Modifier.height(16.dp)) // Espaciador de 16dp entre los elementos

            // Botón para volver a la pantalla anterior
            Button(
                onClick = onBack, // Acción de volver cuando se presiona el botón
                colors = ButtonDefaults.buttonColors(backgroundColor = Naranja) // Botón de fondo negro
            ) {
                Text("Volver", color = Color.White) // Texto blanco en el botón
            }
        }
    }
}
