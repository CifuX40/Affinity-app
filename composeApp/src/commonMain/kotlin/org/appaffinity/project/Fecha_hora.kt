package org.appaffinity.project

import affinityapp.composeapp.generated.resources.Res
import affinityapp.composeapp.generated.resources.fondo_de_pantalla
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import kotlinx.datetime.*
import org.jetbrains.compose.resources.painterResource

@Composable
fun FechaHora(onBack: () -> Unit) {
    var isAuto by remember { mutableStateOf(true) }
    var manualDateTime by remember { mutableStateOf(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())) }

    // Obtener la fecha y hora actual del sistema en modo automático
    val systemDateTime by remember {
        mutableStateOf(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()))
    }

    // Formatear la fecha y hora para mostrarla en un formato legible usando kotlinx.datetime
    val formatter: (LocalDateTime) -> String = { dateTime ->
        "${dateTime.date} ${dateTime.time}"
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(Res.drawable.fondo_de_pantalla),
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

            Spacer(modifier = Modifier.height(16.dp))

            // Botón de guardar para regresar a MenuUsuario
            Button(onClick = {
                // Aquí puedes agregar la lógica para guardar la fecha y hora
                println("Configuración guardada: ${if (isAuto) formatter(systemDateTime) else formatter(manualDateTime)}")
                onBack()
            }) {
                Text("Guardar")
            }
        }
    }
}
