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
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.*

@Composable
fun OffsetPeso(onBack: () -> Unit) {
    var peso by remember { mutableStateOf("") }
    var calibrandoPeso by remember { mutableStateOf(false) }
    var mensajePeso by remember { mutableStateOf("") }
    var unidadPeso by remember { mutableStateOf("Gramos") }
    val keyboardController = LocalSoftwareKeyboardController.current

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
                text = "Calibrar Peso",
                fontSize = 24.sp,
                color = Color(0xFF009EE0)
            )

            Text(
                text = "Depositar offset peso",
                fontSize = 16.sp,
                color = Color(0xFF009EE0),
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Desplegable para seleccionar la unidad de peso
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Text("Unidad:", color = Color(0xFF009EE0))
                Spacer(modifier = Modifier.width(8.dp))
                // Especificamos el tipo explícitamente al pasar la lambda
                CustomDropdownMenu(selectedUnit = unidadPeso) { unidad ->
                    unidadPeso = unidad
                }
            }

            // Campo de texto para ingresar el peso
            BasicTextField(
                value = peso,
                onValueChange = {
                    // Almacena solo caracteres numéricos
                    if (it.all { char -> char.isDigit() }) {
                        peso = it
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .background(Color.LightGray)
                    .padding(8.dp)
                    .width(200.dp)
            )

            // Botón para iniciar la calibración
            Button(
                onClick = {
                    if (peso.isBlank() || peso.toIntOrNull() == null) { // Verifica si no es un número
                        mensajePeso = "Error de calibración: Ingrese un número válido"
                    } else {
                        calibrandoPeso = true
                        mensajePeso = "Calibrando..."
                        keyboardController?.hide() // Oculta el teclado cuando se hace clic en calibrar
                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Naranja),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Calibrar", color = Color.White)
            }

            LaunchedEffect(calibrandoPeso) {
                if (calibrandoPeso) {
                    delay(5000) // Simula calibración por 5 segundos
                    mensajePeso = "Calibración completada"
                    calibrandoPeso = false
                }
            }

            // Muestra el mensaje de calibración o error
            if (calibrandoPeso) {
                Text(text = mensajePeso, color = Color(0xFF009EE0))
            } else if (mensajePeso.isNotEmpty()) {
                Text(
                    text = mensajePeso,
                    color = if (mensajePeso.contains("Error")) Color.Red else Color.Green
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

// Composable personalizado para el menú desplegable de selección de unidad
@Composable
fun CustomDropdownMenu(selectedUnit: String, onUnitSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val unidades = listOf("Gramos", "Onzas")

    Box {
        TextButton(onClick = { expanded = true }) {
            Text(selectedUnit, color = Color(0xFF009EE0))
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            unidades.forEach { unidad ->
                DropdownMenuItem(onClick = {
                    onUnitSelected(unidad)
                    expanded = false
                }) {
                    Text(unidad)
                }
            }
        }
    }
}