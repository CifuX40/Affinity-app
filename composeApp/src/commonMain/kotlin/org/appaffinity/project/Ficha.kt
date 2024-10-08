package org.appaffinity.project

import affinityapp.composeapp.generated.resources.Res
import affinityapp.composeapp.generated.resources.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.painterResource
import androidx.compose.ui.unit.*

// Pantalla que permite al usuario seleccionar entre varias opciones y muestra un diálogo de confirmación cuando se selecciona una opción.
@Composable
fun FichaScreen(onClose: () -> Unit) {
    var selectedOption by remember { mutableStateOf("") } // Estado que almacena la opción seleccionada por el usuario
    var showDialog by remember { mutableStateOf(false) } // Estado para controlar si se muestra o no el diálogo de confirmación

    // Si `showDialog` es verdadero, se muestra un diálogo de confirmación.
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false }, // Cierra el diálogo si se hace clic fuera del mismo
            title = {
                Text(
                    text = Localization.getString("seleccion_confirmada"), // Título del diálogo de confirmación
                    color = Color(0xFF009EE0) // Color del texto
                )
            },
            text = {
                Text(
                    text = Localization.getString("has_seleccionado", selectedOption), // Texto que muestra la opción seleccionada
                    color = Color(0xFF009EE0)
                )
            },
            confirmButton = {
                // Botón para aceptar la selección
                Button(
                    onClick = {
                        showDialog = false // Cierra el diálogo
                        onClose() // Llama a la función para volver a la pantalla anterior
                    },
                    colors = androidx.compose.material.ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF009EE0) // Color de fondo del botón
                    )
                ) {
                    Text(Localization.getString("aceptar"), color = Color.White) // Texto del botón de confirmación
                }
            },
            dismissButton = {
                // Botón para cancelar y regresar sin confirmar
                Button(
                    onClick = onClose, // Cierra el diálogo y regresa a la pantalla anterior
                    colors = androidx.compose.material.ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF009EE0)
                    )
                ) {
                    Text(Localization.getString("regresar"), color = Color.White) // Texto del botón de regreso
                }
            }
        )
    }

    // Contenedor principal de la pantalla que ocupa todo el tamaño disponible
    Box(
        modifier = Modifier
            .fillMaxSize() // Hace que el Box ocupe todo el espacio de la pantalla
    ) {
        // Imagen de fondo que cubre toda la pantalla
        Image(
            painter = painterResource(Res.drawable.fondo_de_pantalla), // Imagen de fondo
            contentDescription = null, // No requiere descripción accesible
            modifier = Modifier.fillMaxSize() // Escala la imagen para que ocupe todo el espacio
        )

        // Columna que contiene los elementos de la pantalla
        Column(
            modifier = Modifier
                .fillMaxSize() // La columna también ocupa todo el espacio disponible
                .padding(16.dp) // Añade un padding de 16dp alrededor del contenido
                .background(Color.White.copy(alpha = 0.8f), shape = RoundedCornerShape(10.dp)), // Fondo blanco semitransparente con esquinas redondeadas
            horizontalAlignment = Alignment.CenterHorizontally, // Alinea los elementos de la columna en el centro horizontalmente
            verticalArrangement = Arrangement.Center // Coloca los elementos centrados verticalmente
        ) {
            // Título de la pantalla
            Text(
                Localization.getString("selecciona_opcion"), // Texto del título
                fontSize = 24.sp, // Tamaño del texto del título
                color = Color(0xFF1A171B) // Color del texto
            )

            Spacer(modifier = Modifier.height(32.dp)) // Espaciador de 32dp para separar los elementos

            // Botón para seleccionar la opción de "fichas farmacéuticas"
            Button(
                onClick = {
                    selectedOption = Localization.getString("fichas_farmaceuticas") // Asigna la opción seleccionada
                    showDialog = true // Muestra el diálogo de confirmación
                },
                modifier = Modifier.padding(8.dp), // Añade un padding de 8dp alrededor del botón
                colors = androidx.compose.material.ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF009EE0) // Color de fondo del botón
                )
            ) {
                Text(Localization.getString("fichas_farmaceuticas"), color = Color.White) // Texto del botón
            }

            // Botón para seleccionar la opción de "euros"
            Button(
                onClick = {
                    selectedOption = Localization.getString("euros") // Asigna la opción seleccionada
                    showDialog = true // Muestra el diálogo de confirmación
                },
                modifier = Modifier.padding(8.dp), // Añade un padding de 8dp alrededor del botón
                colors = androidx.compose.material.ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF009EE0)
                )
            ) {
                Text(Localization.getString("euros"), color = Color.White) // Texto del botón
            }

            Spacer(modifier = Modifier.height(32.dp)) // Espaciador de 32dp entre los elementos

            // Botón para regresar a la pantalla anterior
            Button(
                onClick = { onClose() }, // Llama a la función para cerrar la pantalla actual
                modifier = Modifier.padding(8.dp), // Añade un padding de 8dp alrededor del botón
                colors = androidx.compose.material.ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF009EE0) // Color de fondo del botón
                )
            ) {
                Text(Localization.getString("regresar"), color = Color.White) // Texto del botón
            }
        }
    }
}
