package org.appaffinity.project

import affinityapp.composeapp.generated.resources.Res
import affinityapp.composeapp.generated.resources.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*

// Composable que gestiona el acceso al menú técnico.
// Si la contraseña es correcta, se muestra la pantalla técnica, de lo contrario, se solicita la contraseña.
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
                        Button(
                            onClick = onBorrarClick,
                            colors = ButtonDefaults.buttonColors(backgroundColor = Negro),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(item, color = Blanco, fontSize = 18.sp)
                        }
                    } else {
                        // Botones numéricos
                        Button(
                            onClick = { onNumeroClick(item) },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Negro),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(item, color = Blanco, fontSize = 18.sp)
                        }
                    }
                }
            }
        }
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
            Button(onClick = onBack, colors = ButtonDefaults.buttonColors(backgroundColor = Naranja)) {
                Text("Volver a Usuario", color = Blanco)
            }

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

// Composable que representa la pantalla del menú técnico
@Composable
fun TecnicoScreen(onUsuarioClick: () -> Unit) {
    var showCalibrarPeso by remember { mutableStateOf(false) } // Estado para mostrar la pantalla de calibración de peso
    var showErrorDialog by remember { mutableStateOf(false) } // Estado para mostrar el diálogo de error

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
                color = Naranja
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
                    BotonConImagen(
                        imagen = painterResource(Res.drawable.calibrar_peso),
                        texto = "Calibrar peso",
                        onClick = { showCalibrarPeso = true }
                    )
                }
                item {
                    BotonConImagen(
                        imagen = painterResource(Res.drawable.calibrar_tension),
                        texto = "Calibrar tensión",
                        onClick = { showErrorDialog = true } // Muestra diálogo de error
                    )
                }
                item {
                    BotonConImagen(
                        imagen = painterResource(Res.drawable.offset_altura),
                        texto = "Configurar altura",
                        onClick = {}
                    )
                }
                item {
                    BotonConImagen(
                        imagen = painterResource(Res.drawable.offset_peso),
                        texto = "Configurar peso",
                        onClick = {}
                    )
                }
                item {
                    BotonConImagen(
                        imagen = painterResource(Res.drawable.modo_pruebas),
                        texto = "Modo pruebas",
                        onClick = {}
                    )
                }
                item {
                    BotonConImagen(
                        imagen = painterResource(Res.drawable.contrasena),
                        texto = "Contraseña",
                        onClick = {}
                    )
                }
                item {
                    BotonConImagen(
                        imagen = painterResource(Res.drawable.reiniciar),
                        texto = "Reiniciar dispositivo",
                        onClick = {}
                    )
                }
                // Botón de regreso a la pantalla de usuario
                item {
                    BotonConImagen(
                        imagen = painterResource(Res.drawable.usuario),
                        texto = "Usuario",
                        onClick = onUsuarioClick // Regresa a la pantalla de usuario
                    )
                }
            }
        }

        // Diálogo de error para calibración de tensión
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
    }
}

// Composable para crear botones con imagen y texto
@Composable
fun BotonConImagen(imagen: Painter, texto: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .padding(16.dp)
            .background(Color.LightGray, shape = MaterialTheme.shapes.medium)
            .padding(16.dp)
    ) {
        Image(
            painter = imagen,
            contentDescription = texto,
            modifier = Modifier.size(64.dp),
            contentScale = ContentScale.Fit
        )
        Text(text = texto, fontWeight = FontWeight.Bold, color = Color.Black)
    }
}