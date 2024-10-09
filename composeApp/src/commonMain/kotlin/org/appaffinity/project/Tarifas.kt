package org.appaffinity.project

import affinityapp.composeapp.generated.resources.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*

// Clase de datos que representa una tarifa, con peso, altura y tensión.
data class Tarifa(val peso: String, val altura: String, val tension: String)

// Composable que representa la pantalla de tarifas, permitiendo al usuario ingresar datos y guardar una tarifa.
@Composable
fun TarifaScreen(onAceptarClick: () -> Unit) {
    // Estado que mantiene el peso, altura y tensión ingresados por el usuario.
    var peso by remember { mutableStateOf("") }
    var altura by remember { mutableStateOf("") }
    var tension by remember { mutableStateOf("") }
    // Estado que mantiene la tarifa guardada.
    var tarifaGuardada by remember { mutableStateOf<Tarifa?>(null) }
    // Ruta del archivo donde se guardarán las tarifas.
    val filePath = "Guardar_tarifas.json"

    // Contenedor principal que ocupa toda la pantalla.
    Box(modifier = Modifier.fillMaxSize()) {
        // Imagen de fondo que ocupa toda la pantalla.
        Image(
            painter = painterResource(Res.drawable.fondo_de_pantalla),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Columna para organizar los elementos en vertical.
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), // Espaciado alrededor de la columna.
            horizontalAlignment = Alignment.CenterHorizontally, // Alineación horizontal al centro.
            verticalArrangement = Arrangement.Center // Alineación vertical al centro.
        ) {
            // Título de la pantalla.
            Text(
                text = Localization.getString("tarifas"),
                fontSize = 30.sp, // Tamaño de la fuente.
                color = MaterialTheme.colors.primary, // Color del texto.
                fontWeight = FontWeight.Bold // Peso de la fuente.
            )

            Spacer(modifier = Modifier.height(16.dp)) // Espaciador vertical.

            // Muestra la tarifa guardada, si existe.
            tarifaGuardada?.let { tarifa ->
                // Muestra cada uno de los atributos de la tarifa guardada.
                Text(text = "Peso: ${tarifa.peso}", fontSize = 20.sp)
                Text(text = "Altura: ${tarifa.altura}", fontSize = 20.sp)
                Text(text = "Tensión: ${tarifa.tension}", fontSize = 20.sp)
            } ?: Text(text = "No hay tarifas disponibles", fontSize = 20.sp) // Mensaje si no hay tarifas.

            Spacer(modifier = Modifier.height(16.dp)) // Espaciador vertical.

            // Campo de texto para ingresar el peso.
            TextField(
                value = peso,
                onValueChange = { if (it.all { char -> char.isDigit() }) peso = it }, // Acepta solo dígitos.
                label = { Text(Localization.getString("precio_peso")) }, // Etiqueta del campo.
                modifier = Modifier.fillMaxWidth(), // Ocupa todo el ancho disponible.
                singleLine = true // Solo una línea.
            )

            Spacer(modifier = Modifier.height(8.dp)) // Espaciador vertical.

            // Campo de texto para ingresar la altura.
            TextField(
                value = altura,
                onValueChange = { if (it.all { char -> char.isDigit() }) altura = it }, // Acepta solo dígitos.
                label = { Text(Localization.getString("precio_altura")) }, // Etiqueta del campo.
                modifier = Modifier.fillMaxWidth(), // Ocupa todo el ancho disponible.
                singleLine = true // Solo una línea.
            )

            Spacer(modifier = Modifier.height(8.dp)) // Espaciador vertical.

            // Campo de texto para ingresar la tensión.
            TextField(
                value = tension,
                onValueChange = { if (it.all { char -> char.isDigit() }) tension = it }, // Acepta solo dígitos.
                label = { Text(Localization.getString("precio_tension")) }, // Etiqueta del campo.
                modifier = Modifier.fillMaxWidth(), // Ocupa todo el ancho disponible.
                singleLine = true // Solo una línea.
            )

            Spacer(modifier = Modifier.height(16.dp)) // Espaciador vertical.

            // Reemplaza el botón estándar por Boton_Naranja para calcular y guardar la tarifa.
            Boton_Naranja(
                onClick = {
                    // Verifica que todos los campos tengan valor antes de proceder.
                    if (peso.isNotEmpty() && altura.isNotEmpty() && tension.isNotEmpty()) {
                        // Crea un objeto Tarifa con los valores ingresados.
                        val tarifa = Tarifa(
                            peso = "${(peso.toInt())} céntimos", // Convertir a entero y formatear como cadena.
                            altura = "${(altura.toInt())} céntimos", // Convertir a entero y formatear como cadena.
                            tension = "${(tension.toInt())} céntimos" // Convertir a entero y formatear como cadena.
                        )
                        tarifaGuardada = tarifa // Guarda la tarifa en el estado.
                        mostrarNotificacion("Tarifa guardada exitosamente.") // Muestra notificación.
                    } else {
                        mostrarNotificacion("Por favor, completa todos los campos.") // Notifica que faltan campos.
                    }
                },
                text = Localization.getString("calcular") // Texto del botón.
            )

            Spacer(modifier = Modifier.height(8.dp)) // Espaciador vertical.

            // Reemplaza el botón estándar por Boton_Naranja para regresar.
            Boton_Naranja(
                onClick = onAceptarClick, // Acción del botón que regresa a la pantalla anterior.
                text = Localization.getString("regresar"), // Texto del botón.
                modifier = Modifier.fillMaxWidth() // Ocupa todo el ancho disponible.
            )
        }
    }
}

// Función que muestra una notificación en la consola.
fun mostrarNotificacion(mensaje: String) {
    println(mensaje) // Imprime el mensaje en la consola.
}
