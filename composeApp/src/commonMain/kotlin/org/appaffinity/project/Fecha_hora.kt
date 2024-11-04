package org.appaffinity.project

import affinityapp.composeapp.generated.resources.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.unit.*
import kotlinx.datetime.*
import org.jetbrains.compose.resources.painterResource

// Composable que permite al usuario configurar la fecha y la hora.
@Composable
fun FechaHora(onBack: () -> Unit) {
    var isAuto by remember { mutableStateOf(true) } // Estado que controla si está activado el modo automático
    var manualDateTime by remember {
        mutableStateOf(
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        )
    } // Estado para la fecha y hora en modo manual

    // Estado que almacena la fecha y hora del sistema en modo automático
    val systemDateTime by remember {
        mutableStateOf(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()))
    }

    // Función que formatea la fecha y la hora en un formato legible
    val formatter: (LocalDateTime) -> String = { dateTime ->
        val timeString = String.format("%02d:%02d:%02d", dateTime.hour, dateTime.minute, dateTime.second) // Formato HH:MM:SS
        "${dateTime.date} $timeString" // Muestra la fecha y la hora en formato "YYYY-MM-DD HH:MM:SS"
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
            contentScale = ContentScale.Crop
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
                Switch(
                    checked = isAuto,
                    onCheckedChange = { isAuto = it }) // Cambia el estado de `isAuto` al pulsar el switch
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Si el modo automático está desactivado, muestra campos de texto para ingresar la fecha y hora manualmente
            if (!isAuto) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Fecha (YYYY-MM-DD):") // Etiqueta para la fecha
                        BasicTextField(
                            value = manualDateTime.date.toString(), // Muestra la fecha actual en formato YYYY-MM-DD
                            onValueChange = { input ->
                                runCatching {
                                    LocalDate.parse(input) // Parsea la parte de la fecha
                                }.onSuccess { newDate ->
                                    manualDateTime = LocalDateTime(newDate, manualDateTime.time) // Actualiza la fecha
                                }.onFailure {
                                    // Aquí puedes manejar el error (ejemplo: mostrar un mensaje de error)
                                }
                            },
                            modifier = Modifier.weight(1f) // Ocupa el espacio restante
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Hora (HH:MM:SS):") // Etiqueta para la hora
                        BasicTextField(
                            value = String.format("%02d:%02d:%02d", manualDateTime.hour, manualDateTime.minute, manualDateTime.second), // Muestra la hora actual
                            onValueChange = { input ->
                                runCatching {
                                    val timeParts = input.split(":") // Separa la hora por ':'
                                    if (timeParts.size == 3) {
                                        val hours = timeParts[0].toInt()
                                        val minutes = timeParts[1].toInt()
                                        val seconds = timeParts[2].toInt()
                                        LocalTime(hours, minutes, seconds) // Crea un nuevo objeto LocalTime
                                    } else {
                                        throw IllegalArgumentException("Formato incorrecto")
                                    }
                                }.onSuccess { newTime ->
                                    manualDateTime = LocalDateTime(manualDateTime.date, newTime) // Actualiza la hora
                                }.onFailure {
                                    // Aquí puedes manejar el error (ejemplo: mostrar un mensaje de error)
                                }
                            },
                            modifier = Modifier.weight(1f) // Ocupa el espacio restante
                        )
                    }
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