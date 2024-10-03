package org.affinity.project

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.appaffinity.project.AzulCian
import org.appaffinity.project.Blanco
import org.appaffinity.project.Naranja
import org.appaffinity.project.Negro
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun VentanaFechaHora(onClose: () -> Unit) {
    // Obtener la hora actual de la zona horaria local del ordenador
    val zoneLocal = ZoneId.systemDefault()
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
    var fechaHoraActual by remember { mutableStateOf(LocalDateTime.now(zoneLocal)) }

    // Estado para manejar la opción automática o manual
    var isAutomatic by remember { mutableStateOf(true) }

    // Estados para manejar los inputs de fecha y hora
    var fechaInput by remember { mutableStateOf(fechaHoraActual.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))) }
    var horaInput by remember { mutableStateOf(fechaHoraActual.format(DateTimeFormatter.ofPattern("HH:mm"))) }

    // Fondo de pantalla
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
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
                text = Localization.getString("fecha_hora"),
                fontSize = 30.sp,
                color = Naranja,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Selector de modo automático o manual
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = Localization.getString("automático"), color = Blanco, fontSize = 16.sp)
                Switch(
                    checked = isAutomatic,
                    onCheckedChange = { isAutomatic = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Naranja,
                        uncheckedThumbColor = Blanco,
                        checkedTrackColor = AzulCian,
                        uncheckedTrackColor = Negro
                    )
                )
                Text(text = Localization.getString("manual"), color = Blanco, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de texto para cambiar la fecha
            TextField(
                value = fechaInput,
                onValueChange = { input -> fechaInput = input },
                modifier = Modifier
                    .padding(8.dp)
                    .background(Blanco)
                    .fillMaxWidth(),
                enabled = !isAutomatic,
                label = { Text(text = Localization.getString("fecha_input_label")) },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de texto para cambiar la hora
            TextField(
                value = horaInput,
                onValueChange = { input -> horaInput = input },
                modifier = Modifier
                    .padding(8.dp)
                    .background(Blanco)
                    .fillMaxWidth(),
                enabled = !isAutomatic,
                label = { Text(text = Localization.getString("hora_input_label")) },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para guardar cambios
            Button(
                onClick = {
                    if (!isAutomatic) {
                        try {
                            fechaHoraActual = LocalDateTime.parse("$fechaInput $horaInput", formatter)
                            println("Nueva fecha y hora seleccionadas: $fechaHoraActual")
                        } catch (e: Exception) {
                            println("Error al cambiar la fecha y hora: ${e.message}")
                        }
                    } else {
                        fechaHoraActual = LocalDateTime.now(zoneLocal)
                        println("Modo automático activado, usando hora local: $fechaHoraActual")
                    }
                    onClose()
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = AzulCian)
            ) {
                Text(text = Localization.getString("guardar"), color = Blanco)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para cerrar la ventana sin guardar cambios
            Button(
                onClick = { onClose() },
                colors = ButtonDefaults.buttonColors(backgroundColor = Negro)
            ) {
                Text(text = Localization.getString("cancelar"), color = Blanco)
            }
        }
    }
}
