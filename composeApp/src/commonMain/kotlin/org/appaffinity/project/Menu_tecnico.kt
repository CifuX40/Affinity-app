package org.appaffinity.project

import affinityapp.composeapp.generated.resources.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource

@Composable
fun MenuTecnico(onBack: () -> Unit) {
    var accesoPermitido by remember { mutableStateOf(false) } // Variable que controla el acceso

    // Si el acceso está permitido, muestra la pantalla técnica
    if (accesoPermitido) {
        TecnicoScreen(onUsuarioClick = onBack)
    } else {
        // Solicita la contraseña antes de acceder al menú técnico
        SolicitarContrasena(
            onAccesoPermitido = { accesoPermitido = true }, // Permite el acceso si la contraseña es correcta
            onBack = onBack // Permite volver a la pantalla anterior
        )
    }
}

// Composable que solicita una contraseña para el acceso al menú técnico
@Composable
fun SolicitarContrasena(onAccesoPermitido: () -> Unit, onBack: () -> Unit) {
    var contrasena by remember { mutableStateOf("") } // Almacena la contraseña ingresada
    var mostrarError by remember { mutableStateOf(false) } // Controla si se muestra un error de contraseña

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Blanco), // Fondo blanco
        contentAlignment = Alignment.Center // Centra el contenido
    ) {
        // Imagen de fondo
        Image(
            painter = painterResource(Res.drawable.fondo_de_pantalla),
            contentDescription = "Fondo de Pantalla",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Columna que contiene el texto y el teclado numérico
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            // Título de la pantalla
            Text(
                text = "Introduce la contraseña",
                style = MaterialTheme.typography.h4,
                color = Negro,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Muestra la contraseña ingresada como asteriscos
            Text(
                text = "*".repeat(contrasena.length),
                fontSize = 24.sp,
                color = AzulCian
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Teclado numérico para ingresar la contraseña
            TecladoNumerico(
                onNumeroClick = { numero ->
                    contrasena += numero // Agrega el número a la contraseña
                    // Verifica si la contraseña es correcta (en este caso, "9876")
                    if (contrasena.length == 4) {
                        if (contrasena == "9876") {
                            mostrarError = false // No hay error
                            contrasena = ""
                            onAccesoPermitido() // Permite el acceso
                        } else {
                            mostrarError = true // Muestra error si es incorrecta
                            contrasena = "" // Resetea la contraseña
                        }
                    }
                },
                onBorrarClick = { contrasena = "" } // Limpia la contraseña
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para volver a la pantalla de usuario
            Boton_Naranja(onClick = onBack, text = "Volver a Usuario")

            // Muestra un mensaje de error si la contraseña es incorrecta
            if (mostrarError) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Contraseña incorrecta",
                    color = Color.Red,
                    fontSize = 16.sp
                )
            }
        }
    }
}

// Composable que representa un teclado numérico para ingresar la contraseña
@Composable
fun TecladoNumerico(onNumeroClick: (String) -> Unit, onBorrarClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        val filas = listOf(
            listOf("1", "2", "3"), // Primera fila de números
            listOf("4", "5", "6"), // Segunda fila
            listOf("7", "8", "9"), // Tercera fila
            listOf("Borrar", "0") // Cuarta fila con botón de borrar y el número 0
        )

        // Para cada fila, crea un conjunto de botones
        for (fila in filas) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (item in fila) {
                    if (item == "Borrar") {
                        // Botón de borrar
                        Boton_Naranja(
                            onClick = onBorrarClick,
                            text = item,
                            modifier = Modifier.weight(1f)
                        )
                    } else {
                        // Botones numéricos
                        Boton_Naranja(
                            onClick = { onNumeroClick(item) },
                            text = item,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

// Composable que representa la pantalla del menú técnico
@Composable
fun TecnicoScreen(onUsuarioClick: () -> Unit) {
    var showCalibrarPeso by remember { mutableStateOf(false) } // Estado para mostrar la pantalla de calibración de peso
    var showErrorDialog by remember { mutableStateOf(false) } // Estado para mostrar el diálogo de error
    var showReiniciarDialog by remember { mutableStateOf(false) } // Estado para mostrar el diálogo de reinicio

    // Si se está mostrando la pantalla de calibrar peso, reemplaza el contenido
    if (showCalibrarPeso) {
        CalibrarPeso(onBack = { showCalibrarPeso = false }) // Regresa desde calibración
        return
    }

    // Diseño de la pantalla principal del menú técnico
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Blanco)
    ) {
        // Imagen de fondo
        Image(
            painter = painterResource(Res.drawable.fondo_de_pantalla),
            contentDescription = "Fondo de Pantalla",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Columna con los botones de menú técnico
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Título del menú técnico
            Text(
                text = "Menu Técnico",
                style = MaterialTheme.typography.h4,
                color = Negro
            )

            // Grid que organiza los botones en una cuadrícula de 3 columnas
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Botones de menú técnico
                item {
                    BotonConImagenCustom(
                        imagen = painterResource(Res.drawable.calibrar_peso),
                        texto = "Calibrar peso",
                        color = AzulCian,
                        onClick = { showCalibrarPeso = true }
                    )
                }
                item {
                    BotonConImagenCustom(
                        imagen = painterResource(Res.drawable.calibrar_tension),
                        texto = "Calibrar tensión",
                        color = AzulCian,
                        onClick = { showErrorDialog = true } // Muestra diálogo de error
                    )
                }
                item {
                    BotonConImagenCustom(
                        imagen = painterResource(Res.drawable.offset_altura),
                        texto = "Configurar altura",
                        color = AzulCian,
                        onClick = {}
                    )
                }
                item {
                    BotonConImagenCustom(
                        imagen = painterResource(Res.drawable.offset_peso),
                        texto = "Configurar peso",
                        color = AzulCian,
                        onClick = {}
                    )
                }
                item {
                    BotonConImagenCustom(
                        imagen = painterResource(Res.drawable.modo_pruebas),
                        texto = "Modo pruebas",
                        color = AzulCian,
                        onClick = {}
                    )
                }
                item {
                    BotonConImagenCustom(
                        imagen = painterResource(Res.drawable.contrasena),
                        texto = "Contraseña",
                        color = AzulCian,
                        onClick = {}
                    )
                }
                item {
                    BotonConImagenCustom(
                        imagen = painterResource(Res.drawable.reiniciar),
                        texto = "Reiniciar dispositivo",
                        color = AzulCian,
                        onClick = { showReiniciarDialog = true } // Muestra el diálogo de reinicio
                    )
                }
                // Botón de regreso a la pantalla de usuario
                item {
                    BotonConImagenCustom(
                        imagen = painterResource(Res.drawable.usuario),
                        texto = "Usuario",
                        color = AzulCian,
                        onClick = onUsuarioClick
                    )
                }
            }
        }

        // Diálogo que muestra un mensaje de error
        if (showErrorDialog) {
            AlertDialog(
                onDismissRequest = { showErrorDialog = false },
                title = { Text("Error") },
                text = { Text("No se puede calibrar la tensión en este momento.") },
                confirmButton = {
                    Button(onClick = { showErrorDialog = false }) {
                        Text("Aceptar")
                    }
                }
            )
        }

        // Diálogo de reinicio
        if (showReiniciarDialog) {
            Reiniciar(
                onReiniciar = {
                    // Lógica para reiniciar el dispositivo
                    // Aquí podrías agregar la lógica que desees para realizar el reinicio
                    showReiniciarDialog = false // Cerrar diálogo
                },
                onCancel = { showReiniciarDialog = false } // Cerrar diálogo
            )
        }
    }
}