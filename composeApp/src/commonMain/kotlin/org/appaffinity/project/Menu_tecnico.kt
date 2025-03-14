package org.appaffinity.project

import affinityapp.composeapp.generated.resources.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import org.jetbrains.compose.resources.*

@Composable
fun MenuTecnico(onBack: () -> Unit) {
    var accesoPermitido by remember { mutableStateOf(false) }

    if (accesoPermitido) {
        TecnicoScreen(onUsuarioClick = onBack)
    } else {
        SolicitarContrasena(
            onAccesoPermitido = {
                accesoPermitido = true
            },
            onBack = onBack
        )
    }
}

@Composable
fun SolicitarContrasena(onAccesoPermitido: () -> Unit, onBack: () -> Unit) {
    var contrasena by remember { mutableStateOf("") }
    var mostrarError by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Blanco),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(Res.drawable.fondo_de_pantalla),
            contentDescription = "Fondo de Pantalla",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Introduce la contraseña",
                style = MaterialTheme.typography.h4,
                color = Negro,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "*".repeat(contrasena.length),
                fontSize = 24.sp,
                color = AzulCian
            )

            Spacer(modifier = Modifier.height(16.dp))

            TecladoNumerico(
                onNumeroClick = { numero ->
                    contrasena += numero
                    if (contrasena.length == 4) {
                        if (contrasena == "9876") {
                            mostrarError = false
                            contrasena = ""
                            onAccesoPermitido()
                        } else {
                            mostrarError = true
                            contrasena = ""
                        }
                    }
                },
                onBorrarClick = { contrasena = "" }
            )

            Spacer(modifier = Modifier.height(16.dp))
            Boton_Naranja(onClick = onBack, text = "Volver a Usuario")

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

@Composable
fun TecladoNumerico(onNumeroClick: (String) -> Unit, onBorrarClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        val filas = listOf(
            listOf("1", "2", "3"),
            listOf("4", "5", "6"),
            listOf("7", "8", "9"),
            listOf("Borrar", "0")
        )

        for (fila in filas) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (item in fila) {
                    if (item == "Borrar") {
                        Boton_Naranja(
                            onClick = onBorrarClick,
                            text = item,
                            modifier = Modifier.weight(1f)
                        )
                    } else {
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

@Composable
fun TecnicoScreen(onUsuarioClick: () -> Unit) {
    var showCalibrarPeso by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var showReiniciarDialog by remember { mutableStateOf(false) }
    var showOffsetAltura by remember { mutableStateOf(false) }
    var showOffsetPeso by remember { mutableStateOf(false) }
    var showModoPrueba by remember { mutableStateOf(false) }
    var showCambiarContrasenaScreen by remember { mutableStateOf(false) }

    if (showCalibrarPeso) {
        CalibrarPeso(onBack = { showCalibrarPeso = false })
        return
    }
    if (showOffsetAltura) {
        OffsetAltura(onBack = { showOffsetAltura = false })
        return
    }
    if (showOffsetPeso) {
        OffsetPeso(onBack = { showOffsetPeso = false })
        return
    }
    if (showModoPrueba) {
        Modo_Pruebas(onBack = { showModoPrueba = false })
        return
    }
    if (showCambiarContrasenaScreen) {
        CambiarContrasenaScreen(onBack = { showCambiarContrasenaScreen = false })
        return
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Blanco)
    ) {
        Image(
            painter = painterResource(Res.drawable.fondo_de_pantalla),
            contentDescription = "Fondo de Pantalla",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Menu Técnico",
                style = MaterialTheme.typography.h4,
                color = Negro
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
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
                        onClick = { showErrorDialog = true }
                    )
                }
                item {
                    BotonConImagenCustom(
                        imagen = painterResource(Res.drawable.offset_altura),
                        texto = "Offset altura",
                        color = AzulCian,
                        onClick = { showOffsetAltura = true }
                    )
                }
                item {
                    BotonConImagenCustom(
                        imagen = painterResource(Res.drawable.offset_peso),
                        texto = "Offset peso",
                        color = AzulCian,
                        onClick = { showOffsetPeso = true }
                    )
                }
                item {
                    BotonConImagenCustom(
                        imagen = painterResource(Res.drawable.modo_pruebas),
                        texto = "Modo pruebas",
                        color = AzulCian,
                        onClick = { showModoPrueba = true }
                    )
                }
                item {
                    BotonConImagenCustom(
                        imagen = painterResource(Res.drawable.contrasena),
                        texto = "Contraseña",
                        color = AzulCian,
                        onClick = { showCambiarContrasenaScreen = true }
                    )
                }
                item {
                    BotonConImagenCustom(
                        imagen = painterResource(Res.drawable.reiniciar),
                        texto = "Reiniciar dispositivo",
                        color = AzulCian,
                        onClick = { showReiniciarDialog = true }
                    )
                }
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