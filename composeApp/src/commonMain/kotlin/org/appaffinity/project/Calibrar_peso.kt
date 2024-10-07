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
    var peso by remember { mutableStateOf("") }
    var calibrando by remember { mutableStateOf(false) }
    var mensaje by remember { mutableStateOf("") }

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
            Text(text = "Calibrar Peso", fontSize = 24.sp, color = Color(0xFF009EE0))

            // Campo de texto para que el usuario ingrese el peso
            BasicTextField(
                value = peso,
                onValueChange = { peso = it },
                modifier = Modifier
                    .background(Color.LightGray)
                    .padding(8.dp)
                    .fillMaxWidth()
            )

            Button(
                onClick = {
                    calibrando = true
                    mensaje = "Calibrando..."
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFF5B130))
            ) {
                Text("Calibrar", color = Color.Black)
            }

            // Iniciar una corutina para simular el tiempo de calibración de 5 segundos
            LaunchedEffect(calibrando) {
                if (calibrando) {
                    delay(5000)
                    mensaje = "Calibración completada"
                    calibrando = false
                }
            }

            if (calibrando) {
                Text(text = mensaje, color = Color(0xFF009EE0))
            } else if (mensaje.isNotEmpty()) {
                Text(text = mensaje, color = Color.Green)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1A171B))
            ) {
                Text("Volver", color = Color.White)
            }
        }
    }
}
