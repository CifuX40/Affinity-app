package org.affinity.project

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.io.File
import java.io.FileWriter
import java.io.IOException

// Clase de datos para almacenar las tarifas
data class Tarifa(val peso: String, val altura: String, val tension: String)

@Composable
fun TarifaScreen(onClose: () -> Unit) {
    var peso by remember { mutableStateOf("") }
    var altura by remember { mutableStateOf("") }
    var tension by remember { mutableStateOf("") }
    var tarifaGuardada by remember { mutableStateOf<Tarifa?>(null) }
    val filePath = "C:\\Users\\Hp\\AndroidStudioProjects\\AppEgaraPlus\\composeApp\\src\\commonMain\\kotlin\\org\\affinity\\project\\Tarifas.json"

    // Cargar tarifas al iniciar
    LaunchedEffect(Unit) {
        tarifaGuardada = cargarTarifaDesdeArchivo(filePath)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource("fondo_de_pantalla.png"),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = Localization.getString("tarifas"),
                fontSize = 30.sp,
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Mostrar tarifas cargadas
            tarifaGuardada?.let { tarifa ->
                Text(text = "Peso: ${tarifa.peso}", fontSize = 20.sp)
                Text(text = "Altura: ${tarifa.altura}", fontSize = 20.sp)
                Text(text = "Tensión: ${tarifa.tension}", fontSize = 20.sp)
            } ?: Text(text = "No hay tarifas disponibles", fontSize = 20.sp)

            Spacer(modifier = Modifier.height(16.dp))

            // Campo para precio basado en peso (solo numérico)
            TextField(
                value = peso,
                onValueChange = { if (it.all { char -> char.isDigit() }) peso = it },
                label = { Text(Localization.getString("precio_peso")) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.surface,
                    focusedIndicatorColor = MaterialTheme.colors.primary,
                    unfocusedIndicatorColor = MaterialTheme.colors.onSurface
                ),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Campo para precio basado en altura (solo numérico)
            TextField(
                value = altura,
                onValueChange = { if (it.all { char -> char.isDigit() }) altura = it },
                label = { Text(Localization.getString("precio_altura")) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.surface,
                    focusedIndicatorColor = MaterialTheme.colors.primary,
                    unfocusedIndicatorColor = MaterialTheme.colors.onSurface
                ),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Campo para precio basado en tensión (solo numérico)
            TextField(
                value = tension,
                onValueChange = { if (it.all { char -> char.isDigit() }) tension = it },
                label = { Text(Localization.getString("precio_tension")) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.surface,
                    focusedIndicatorColor = MaterialTheme.colors.primary,
                    unfocusedIndicatorColor = MaterialTheme.colors.onSurface
                ),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    // Crear una instancia de Tarifa con los valores ingresados
                    val tarifa = Tarifa(
                        peso = "${(peso.toInt())} céntimos",
                        altura = "${(altura.toInt())} céntimos",
                        tension = "${(tension.toInt())} céntimos"
                    )

                    // Guardar las tarifas en el archivo JSON
                    guardarTarifaEnArchivo(filePath, tarifa)

                    // Limpiar campos de entrada
                    peso = ""
                    altura = ""
                    tension = ""

                    // Cargar tarifas nuevamente
                    tarifaGuardada = cargarTarifaDesdeArchivo(filePath)

                    // Mostrar notificación
                    mostrarNotificacion("Tarifa guardada exitosamente.")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = Localization.getString("calcular"))
            }

            Button(
                onClick = onClose,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = Localization.getString("regresar"))
            }
        }
    }
}

// Función para guardar las tarifas en un archivo JSON
fun guardarTarifaEnArchivo(filePath: String, tarifa: Tarifa) {
    try {
        val file = File(filePath)

        // Crear el archivo si no existe
        if (!file.exists()) {
            file.createNewFile()
        }

        // Crear manualmente el formato JSON
        val jsonTarifa = """
            {
                "peso": "${tarifa.peso}",
                "altura": "${tarifa.altura}",
                "tension": "${tarifa.tension}"
            }
        """.trimIndent()

        // Escribir en el archivo (sobrescribiendo)
        FileWriter(file).use { it.write(jsonTarifa) }

        println("Tarifa guardada exitosamente.")
    } catch (e: IOException) {
        println("Error al guardar la tarifa: ${e.message}")
    }
}

// Función para cargar tarifas desde el archivo JSON
fun cargarTarifaDesdeArchivo(filePath: String): Tarifa? {
    return try {
        val file = File(filePath)
        if (file.exists()) {
            val jsonContent = file.readText()
            val parts = jsonContent.replace("{", "").replace("}", "").split(",").map { it.split(":").last().trim().replace("\"", "") }
            Tarifa(parts[0], parts[1], parts[2])
        } else {
            null
        }
    } catch (e: Exception) {
        println("Error al cargar tarifas: ${e.message}")
        null
    }
}

// Función para mostrar notificaciones
fun mostrarNotificacion(mensaje: String) {
    println(mensaje)
}
