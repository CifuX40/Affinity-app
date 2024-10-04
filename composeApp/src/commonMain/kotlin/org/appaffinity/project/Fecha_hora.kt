package org.appaffinity.project

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun FechaHora() {
    var isAuto by remember { mutableStateOf(true) }  // Estado para cambiar entre automático y manual
    var manualDateTime by remember { mutableStateOf(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())) }

    // Obtener la fecha y hora actual del sistema en modo automático
    val systemDateTime by remember {
        mutableStateOf(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()))
    }

    // Formatear la fecha y hora para mostrarla en un formato legible usando kotlinx.datetime
    val formatter: (LocalDateTime) -> String = { dateTime ->
        "${dateTime.date} ${dateTime.time}"
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Configuración de Fecha y Hora", style = MaterialTheme.typography.h5)

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar la fecha y hora actual (automática o manual)
        Text(text = if (isAuto) "Automático: ${formatter(systemDateTime)}"
        else "Manual: ${formatter(manualDateTime)}")

        Spacer(modifier = Modifier.height(16.dp))

        // Switch para activar o desactivar el modo automático
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Modo Automático")
            Switch(checked = isAuto, onCheckedChange = { isAuto = it })
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Campos de texto para cambiar la fecha y hora manualmente si está desactivado el modo automático
        if (!isAuto) {
            Row {
                Text(text = "Fecha y Hora:")
                BasicTextField(
                    value = formatter(manualDateTime),
                    onValueChange = { input ->
                        // Intentar analizar el nuevo valor ingresado por el usuario
                        runCatching {
                            val parts = input.split(" ")
                            val datePart = LocalDate.parse(parts[0])
                            val timePart = LocalTime.parse(parts[1])
                            LocalDateTime(datePart, timePart)
                        }.onSuccess { newDateTime ->
                            manualDateTime = newDateTime
                        }
                    }
                )
            }
        }
    }
}
