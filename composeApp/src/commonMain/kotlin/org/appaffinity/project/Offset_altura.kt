package org.appaffinity.project

import affinityapp.composeapp.generated.resources.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.*
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource

@Composable
fun OffsetAltura(onBack: () -> Unit) {
    var altura by remember { mutableStateOf("") }
    var calibrandoAltura by remember { mutableStateOf(false) }
    var mensajeAltura by remember { mutableStateOf("") }
    var unidadAltura by remember { mutableStateOf("KG") } // Estado para la unidad seleccionada

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
                text = "Depositar peso reconocido",
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
                CustomDropdownMenu(selectedUnit = unidadAltura) { unidadAltura = it }
            }

            // Campo de texto para ingresar el peso
            BasicTextField(
                value = altura,
                onValueChange = {
                    // Almacena solo caracteres numéricos
                    if (it.all { char -> char.isDigit() }) {
                        altura = it
                    }
                },
                modifier = Modifier
                    .background(Color.LightGray)
                    .padding(8.dp)
                    .width(200.dp)
            )

            Button(
                onClick = {
                    if (altura.isBlank() || altura.toIntOrNull() == null) { // Verifica si no es un número
                        mensajeAltura = "Error de calibración: Ingrese un número válido"
                    } else {
                        calibrandoAltura = true
                        mensajeAltura = "Calibrando..."
                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Naranja),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Calibrar", color = Color.White)
            }

            LaunchedEffect(calibrandoAltura) {
                if (calibrandoAltura) {
                    delay(5000)
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
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFFA500))
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
    val unidades = listOf("KG", "Libras")

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