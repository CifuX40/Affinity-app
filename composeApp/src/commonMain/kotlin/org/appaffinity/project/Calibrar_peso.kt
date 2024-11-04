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

@Composable
fun CalibrarPeso(onBack: () -> Unit) {
    var peso by remember { mutableStateOf("") } // Variable que almacena el valor del peso ingresado por el usuario
    var calibrando by remember { mutableStateOf(false) } // Indica si el proceso de calibración está en curso
    var mensaje by remember { mutableStateOf("") } // Mensaje que muestra el estado de la calibración

    // Diseño de la pantalla completa con imagen de fondo
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(Res.drawable.fondo_de_pantalla),
            contentDescription = "Fondo de Pantalla",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.Transparent)
        ) {
            // Título de la pantalla
            Text(
                text = "Calibrar Peso",
                fontSize = 24.sp,
                color = Color(0xFF009EE0)
            )

            // Mensaje que solicita el ingreso del peso
            Text(
                text = "Depositar peso reconocido", // Mensaje agregado
                fontSize = 16.sp,
                color = Color(0xFF009EE0),
                modifier = Modifier.padding(vertical = 8.dp) // Espaciado vertical
            )

            // Campo de texto para que el usuario ingrese el valor del peso
            BasicTextField(
                value = peso,
                onValueChange = {
                    // Solo permitir caracteres numéricos
                    if (it.all { char -> char.isDigit() }) {
                        peso = it
                    }
                },
                modifier = Modifier
                    .background(Color.LightGray)
                    .padding(8.dp)
                    .fillMaxWidth()
            )

            // Botón para iniciar el proceso de calibración
            Button(
                onClick = {
                    calibrando = true
                    mensaje = "Calibrando..."
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Naranja)
            ) {
                Text("Calibrar", color = Color.Black)
            }

            LaunchedEffect(calibrando) {
                if (calibrando) {
                    delay(5000)
                    mensaje = "Calibración completada"
                    calibrando = false
                }
            }

            // Mostrar el estado de la calibración según el valor de `calibrando` y `mensaje`
            if (calibrando) {
                Text(
                    text = mensaje,
                    color = Color(0xFF009EE0)
                )
            } else if (mensaje.isNotEmpty()) {
                Text(
                    text = mensaje,
                    color = Color.Green
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para volver a la pantalla anterior
            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(backgroundColor = Naranja)
            ) {
                Text("Volver", color = Color.White)
            }
        }
    }
}