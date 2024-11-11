package org.appaffinity.project

import affinityapp.composeapp.generated.resources.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.unit.*
import kotlinx.datetime.*
import org.jetbrains.compose.resources.*

// Composable que permite al usuario configurar la fecha y la hora.
@Composable
fun FechaHora(onBack: () -> Unit) {
    var isAuto by remember { mutableStateOf(true) }
    var manualDateTime by remember {
        mutableStateOf(
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        )
    }

    // Estado que almacena la fecha y hora del sistema en modo automático
    val systemDateTime by remember {
        mutableStateOf(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()))
    }

    // Función que formatea la fecha y la hora
    val formatter: (LocalDateTime) -> String = { dateTime ->
        val timeString =
            String.format("%02d:%02d:%02d", dateTime.hour, dateTime.minute, dateTime.second)
        "${dateTime.date} $timeString" // Muestra la fecha y la hora en formato "YYYY-MM-DD HH:MM:SS"
    }

    // Contenedor principal que ocupa toda la pantalla
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Título de la pantalla
            Text(text = "Configuración de Fecha y Hora", style = MaterialTheme.typography.h5)

            Spacer(modifier = Modifier.height(16.dp))

            // Muestra la fecha y hora en modo automático o manual según el estado de `isAuto`
            Text(
                text = if (isAuto) "Automático: ${formatter(systemDateTime)}"
                else "Manual: ${formatter(manualDateTime)}",
                color = AzulCian
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Fila con el texto "Modo Automático" y un switch para activar o desactivar el modo automático
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Modo Automático", color = AzulCian)
                Switch(
                    checked = isAuto,
                    onCheckedChange = { isAuto = it })
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Si el modo automático está desactivado, muestra campos de texto para ingresar la fecha y hora manualmente
            if (!isAuto) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Fecha (YYYY-MM-DD):")
                        BasicTextField(
                            value = manualDateTime.date.toString(),
                            onValueChange = { input ->
                                runCatching {
                                    LocalDate.parse(input) // Parsea la parte de la fecha
                                }.onSuccess { newDate ->
                                    manualDateTime = LocalDateTime(
                                        newDate,
                                        manualDateTime.time
                                    ) // Actualiza la fecha
                                }.onFailure {
                                }
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Hora (HH:MM:SS):")
                        BasicTextField(
                            value = String.format(
                                "%02d:%02d:%02d",
                                manualDateTime.hour,
                                manualDateTime.minute,
                                manualDateTime.second
                            ), // Muestra la hora actual
                            onValueChange = { input ->
                                runCatching {
                                    val timeParts = input.split(":")
                                    if (timeParts.size == 3) {
                                        val hours = timeParts[0].toInt()
                                        val minutes = timeParts[1].toInt()
                                        val seconds = timeParts[2].toInt()
                                        LocalTime(hours, minutes, seconds)
                                    } else {
                                        throw IllegalArgumentException("Formato incorrecto")
                                    }
                                }.onSuccess { newTime ->
                                    manualDateTime = LocalDateTime(
                                        manualDateTime.date,
                                        newTime
                                    ) // Actualiza la hora
                                }.onFailure {
                                }
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Boton_Naranja(
                onClick = {
                    // Imprime la configuración guardada en la consola, dependiendo de si es automática o manual
                    println(
                        "Configuración guardada: ${
                            if (isAuto) formatter(systemDateTime) else formatter(
                                manualDateTime
                            )
                        }"
                    )
                    onBack()
                },
                text = "Guardar"
            )
        }
    }
}