package org.appaffinity.project

import affinityapp.composeapp.generated.resources.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.*
import kotlinx.coroutines.*
import org.jetbrains.compose.resources.*

@Composable
fun OffsetAltura(onBack: () -> Unit) {
    var altura by remember { mutableStateOf("") }
    var calibrandoAltura by remember { mutableStateOf(false) }
    var mensajeAltura by remember { mutableStateOf("") }
    var unidadAltura by remember { mutableStateOf("CM") }
    var expanded by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val unidades = listOf("CM", "Pulgadas")

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
            Text(
                text = "Calibrar Altura",
                fontSize = 24.sp,
                color = Color(0xFF009EE0)
            )

            Text(
                text = "Depositar offset altura",
                fontSize = 16.sp,
                color = Color(0xFF009EE0),
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Desplegable para seleccionar la unidad de altura
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Text("Unidad:", color = Color(0xFF009EE0))
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(onClick = { expanded = true }) {
                    Text(unidadAltura, color = Color(0xFF009EE0))
                }

                // Usamos el DropdownMenu directamente
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    unidades.forEach { unidad ->
                        DropdownMenuItem(onClick = {
                            unidadAltura = unidad
                            expanded = false
                        }) {
                            Text(unidad)
                        }
                    }
                }
            }

            // Campo de texto para ingresar la altura
            BasicTextField(
                value = altura,
                onValueChange = {
                    // Almacena solo caracteres numéricos
                    if (it.all { char -> char.isDigit() }) {
                        altura = it
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .background(Color.LightGray)
                    .padding(8.dp)
                    .width(200.dp),
                singleLine = true
            )

            // Botón para iniciar la calibración
            Button(
                onClick = {
                    if (altura.isBlank() || altura.toIntOrNull() == null) {
                        mensajeAltura = "Error de calibración: Ingrese un número válido"
                    } else {
                        calibrandoAltura = true
                        mensajeAltura = "Calibrando..."
                        keyboardController?.hide() // Ocultar teclado al iniciar calibración
                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFFA500)),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Calibrar", color = Color.White)
            }

            LaunchedEffect(calibrandoAltura) {
                if (calibrandoAltura) {
                    delay(5000) // simula calibración por 5 segundos
                    mensajeAltura = "Calibración completada"
                    calibrandoAltura = false
                }
            }

            if (calibrandoAltura) {
                Text(text = mensajeAltura, color = Color(0xFF009EE0))
            } else if (mensajeAltura.isNotEmpty()) {
                Text(
                    text = mensajeAltura,
                    color = if (mensajeAltura.contains("Error")) Color.Red else Color.Green
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(backgroundColor = Naranja)
            ) {
                Text("Volver", color = Color.White)
            }
        }
    }
}