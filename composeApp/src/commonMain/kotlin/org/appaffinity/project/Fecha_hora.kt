package org.appaffinity.project

import affinityapp.composeapp.generated.resources.Res
import affinityapp.composeapp.generated.resources.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.*
import kotlinx.datetime.*
import org.jetbrains.compose.resources.painterResource

// Composable que permite al usuario configurar la fecha y la hora.
@Composable
fun FechaHora(onBack: () -> Unit) {
    var isAuto by remember { mutableStateOf(true) } // Estado que controla si está activado el modo automático
    var manualDateTime by remember { mutableStateOf(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())) } // Estado para la fecha y hora en modo manual

    // Estado que almacena la fecha y hora del sistema en modo automático
    val systemDateTime by remember {
        mutableStateOf(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()))
    }

    // Función que formatea la fecha y la hora en un formato legible
    val formatter: (LocalDateTime) -> String = { dateTime ->
        "${dateTime.date} ${dateTime.time}" // Muestra la fecha y la hora en formato "YYYY-MM-DD HH:MM:SS"
    }

    // Contenedor principal que ocupa toda la pantalla
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Imagen de fondo
        Image(
            painter = painterResource(Res.drawable.fondo_de_pantalla),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // Escala la imagen para que ocupe toda la pantalla
        )

        // Columna que organiza los elementos de forma vertical
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), // Añade padding de 16dp alrededor del contenido
            horizontalAlignment = Alignment.CenterHorizontally, // Alinea el contenido horizontalmente en el centro
            verticalArrangement = Arrangement.Center // Coloca el contenido verticalmente en el centro
        ) {
            // Título de la pantalla
            Text(text = "Configuración de Fecha y Hora", style = MaterialTheme.typography.h5)

            Spacer(modifier = Modifier.height(16.dp)) // Espaciador de 16dp entre el título y el contenido

            // Muestra la fecha y hora en modo automático o manual según el estado de `isAuto`
            Text(
                text = if (isAuto) "Automático: ${formatter(systemDateTime)}"
                else "Manual: ${formatter(manualDateTime)}",
                color = AzulCian // Establecer el color del texto a AzulCian
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Fila con el texto "Modo Automático" y un switch para activar o desactivar el modo automático
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Modo Automático", color = AzulCian)
                Switch(checked = isAuto, onCheckedChange = { isAuto = it }) // Cambia el estado de `isAuto` al pulsar el switch
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Si el modo automático está desactivado, muestra campos de texto para ingresar la fecha y hora manualmente
            if (!isAuto) {
                Row {
                    Text(text = "Fecha y Hora:") // Etiqueta para los campos de texto

                    // Campo de texto básico donde el usuario puede ingresar la fecha y hora
                    BasicTextField(
                        value = formatter(manualDateTime), // Muestra el valor actual de la fecha y hora manual
                        onValueChange = { input ->
                            // Divide el input en partes para extraer la fecha y la hora
                            runCatching {
                                val parts = input.split(" ") // Separa la fecha y la hora por espacios
                                val datePart = LocalDate.parse(parts[0]) // Parsea la parte de la fecha
                                val timePart = LocalTime.parse(parts[1]) // Parsea la parte de la hora
                                LocalDateTime(datePart, timePart) // Crea un nuevo objeto LocalDateTime con la fecha y hora ingresada
                            }.onSuccess { newDateTime ->
                                manualDateTime = newDateTime // Actualiza el valor de la fecha y hora manual si el formato es correcto
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Boton_Naranja(
                onClick = {
                    // Imprime la configuración guardada en la consola, dependiendo de si es automática o manual
                    println("Configuración guardada: ${if (isAuto) formatter(systemDateTime) else formatter(manualDateTime)}")
                    onBack() // Llama a la función `onBack` para volver a la pantalla anterior
                },
                text = "Guardar"
            )
        }
    }
}