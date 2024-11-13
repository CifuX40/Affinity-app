package org.appaffinity.project

import affinityapp.composeapp.generated.resources.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import org.jetbrains.compose.resources.*
import java.io.File
import java.text.*
import java.util.*

@Serializable
data class Medidas(
    val centimetros: Float,
    val kilogramos: Float,
    val tensionSistolica: Int,
    val tensionDiastolica: Int
)

@Serializable
data class Historial(
    val registros: MutableList<HistorialItem> = mutableListOf()
)

@Serializable
data class HistorialItem(
    val medidas: Medidas,
    val resultado: String,
    val detalleError: String?,
    val fecha: String
)

const val ARCHIVO_MEDIDAS = "Modo_pruebas.json"
const val ARCHIVO_HISTORIAL = "Historial.json"

fun guardarMedidas(medidas: Medidas) {
    if (esAndroidDetectado()) {
        mostrarNotificacionWindows("Esta función solo está disponible en Windows.")
        return
    }
    val jsonMedidas = Json.encodeToString(medidas)
    val archivo = File(ARCHIVO_MEDIDAS)
    archivo.writeText(jsonMedidas)
}

fun cargarMedidas(): Medidas? {
    if (esAndroidDetectado()) {
        mostrarNotificacionWindows("Esta función solo está disponible en Windows.")
        return null
    }
    return try {
        val archivo = File(ARCHIVO_MEDIDAS)
        if (archivo.exists()) {
            val jsonMedidas = archivo.readText()
            Json.decodeFromString<Medidas>(jsonMedidas)
        } else {
            null
        }
    } catch (e: Exception) {
        println("Error al leer el archivo: ${e.message}")
        null
    }
}

fun cargarHistorial(): Historial {
    return try {
        val archivo = File(ARCHIVO_HISTORIAL)
        if (archivo.exists()) {
            val jsonHistorial = archivo.readText()
            Json.decodeFromString<Historial>(jsonHistorial)
        } else {
            Historial()
        }
    } catch (e: Exception) {
        println("Error al leer el archivo de historial: ${e.message}")
        Historial()
    }
}

fun guardarHistorial(historial: Historial) {
    val jsonHistorial = Json.encodeToString(historial)
    val archivo = File(ARCHIVO_HISTORIAL)
    archivo.writeText(jsonHistorial)
}

fun limpiarHistorial() {
    val archivo = File(ARCHIVO_HISTORIAL)
    if (archivo.exists()) {
        archivo.writeText(Json.encodeToString(Historial()))
    }
}

fun esAndroidDetectado(): Boolean {
    return try {
        Class.forName("android.os.Build")
        true
    } catch (e: ClassNotFoundException) {
        false
    }
}

fun mostrarNotificacionWindows(mensaje: String) {
    println(mensaje)
}

@Composable
fun Modo_Pruebas(onAceptarClick: () -> Unit) {
    var centimetros by remember { mutableStateOf("") }
    var kilogramos by remember { mutableStateOf("") }
    var tensionSistolica by remember { mutableStateOf("") }
    var tensionDiastolica by remember { mutableStateOf("") }
    var historial by remember { mutableStateOf(cargarHistorial()) }
    var resultado by remember { mutableStateOf("") }
    var errorDetalle by remember { mutableStateOf("") }
    var mostrarHistorial by remember { mutableStateOf(false) }

    val medidasGuardadas = cargarMedidas()
    val medidasGuardadasValidas = medidasGuardadas ?: Medidas(0f, 0f, 0, 0)

    // Margen de error
    val margenErrorCentimetros = 1
    val margenErrorKilogramos = 0.5
    val margenErrorTension = 3

    // Función de comparación con margen de error
    fun compararMedidas(medidas: Medidas, medidasGuardadas: Medidas): String {
        val diferenciaCentimetros = medidas.centimetros - medidasGuardadas.centimetros
        val diferenciaKilogramos = medidas.kilogramos - medidasGuardadas.kilogramos
        val diferenciaSistolica = medidas.tensionSistolica - medidasGuardadas.tensionSistolica
        val diferenciaDiastolica = medidas.tensionDiastolica - medidasGuardadas.tensionDiastolica

        val detallesErrores = mutableListOf<String>()

        // Verificar si la diferencia está fuera del margen de error
        if (Math.abs(diferenciaCentimetros) > margenErrorCentimetros) detallesErrores.add("Centímetros: $diferenciaCentimetros")
        if (Math.abs(diferenciaKilogramos) > margenErrorKilogramos) detallesErrores.add("Kilogramos: $diferenciaKilogramos")
        if (Math.abs(diferenciaSistolica) > margenErrorTension) detallesErrores.add("Tensión Sistólica: $diferenciaSistolica")
        if (Math.abs(diferenciaDiastolica) > margenErrorTension) detallesErrores.add("Tensión Diastólica: $diferenciaDiastolica")

        return if (detallesErrores.isNotEmpty()) {
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
            val horaError = sdf.format(Date())
            errorDetalle = "Diferencias encontradas:\n" +
                    detallesErrores.joinToString("\n") +
                    "\nHora del error: $horaError"
            "ERROR"
        } else {
            "OK"
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(Res.drawable.fondo_de_pantalla),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Modo Pruebas",
                fontSize = 30.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo para Centímetros
            TextField(
                value = centimetros,
                onValueChange = {
                    // Permite números con decimales
                    if (it.matches(Regex("^[0-9]*\\.?[0-9]+$"))) centimetros = it
                },
                label = { Text("Centímetros") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Campo para Kilogramos
            TextField(
                value = kilogramos,
                onValueChange = {
                    // Permite números con decimales
                    if (it.matches(Regex("^[0-9]*\\.?[0-9]+$"))) kilogramos = it
                },
                label = { Text("Kilogramos") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Campo para Tensión Sistólica
            TextField(
                value = tensionSistolica,
                onValueChange = {
                    // Permite solo números enteros
                    if (it.matches(Regex("^[0-9]+$"))) tensionSistolica = it
                },
                label = { Text("Tensión Sistolica") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Campo para Tensión Diastólica
            TextField(
                value = tensionDiastolica,
                onValueChange = {
                    // Permite solo números enteros
                    if (it.matches(Regex("^[0-9]+$"))) tensionDiastolica = it
                },
                label = { Text("Tensión Diastolica") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (centimetros.isNotEmpty() && kilogramos.isNotEmpty() && tensionSistolica.isNotEmpty() && tensionDiastolica.isNotEmpty()) {
                        // Validar y convertir los valores de entrada a flotantes
                        try {
                            val medidasIngresadas = Medidas(
                                centimetros = centimetros.toFloat(),
                                kilogramos = kilogramos.toFloat(),
                                tensionSistolica = tensionSistolica.toInt(),
                                tensionDiastolica = tensionDiastolica.toInt()
                            )
                            resultado = compararMedidas(medidasIngresadas, medidasGuardadasValidas)

                            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                            val fecha = sdf.format(Date())
                            val historialItem = HistorialItem(
                                medidas = medidasIngresadas,
                                resultado = resultado,
                                detalleError = errorDetalle.takeIf { resultado == "ERROR" },
                                fecha = fecha
                            )
                            historial.registros.add(historialItem)
                            guardarHistorial(historial)
                        } catch (e: Exception) {
                            mostrarNotificacionWindows("Por favor, ingresa valores válidos.")
                        }
                    } else {
                        mostrarNotificacionWindows("Por favor, completa todos los campos.")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Comparar")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = resultado,
                fontSize = 20.sp,
                color = if (resultado == "OK") Color.Green else Color.Red
            )

            if (resultado == "ERROR") {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = errorDetalle,
                    fontSize = 16.sp,
                    color = Color.Red
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { mostrarHistorial = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Ver Historial de Errores")
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (mostrarHistorial) {
                historial.registros.forEach { item ->
                    Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                        Text("Fecha: ${item.fecha}")
                        Text("Resultado: ${item.resultado}")
                        item.detalleError?.let { Text("Detalle: $it") }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        limpiarHistorial()
                        historial = Historial()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                ) {
                    Text(text = "Borrar Historial", color = Color.White)
                }
            }
        }
    }
}