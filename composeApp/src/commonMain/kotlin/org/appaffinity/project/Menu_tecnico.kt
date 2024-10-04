package org.appaffinity.project

import affinityapp.composeapp.generated.resources.Res
import affinityapp.composeapp.generated.resources.calibrar_peso
import affinityapp.composeapp.generated.resources.calibrar_tension
import affinityapp.composeapp.generated.resources.contrasena
import affinityapp.composeapp.generated.resources.fondo_de_pantalla
import affinityapp.composeapp.generated.resources.modo_pruebas
import affinityapp.composeapp.generated.resources.offset_altura
import affinityapp.composeapp.generated.resources.offset_peso
import affinityapp.composeapp.generated.resources.reiniciar
import affinityapp.composeapp.generated.resources.usuario
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

val Negro = Color(0xFF1A171B)
val AzulCian = Color(0xFF009EE0)
val Blanco = Color(0xFFFFFFFF)
val Naranja = Color(0xFFF5B130)

@Composable
fun MenuTecnico(onBack: () -> Unit, onNavigateToUsuario: () -> Unit) {
    var accesoPermitido by remember { mutableStateOf(false) }

    if (accesoPermitido) {
        TecnicoScreen(onUsuarioClick = onBack)
    } else {
        SolicitarContrasena(
            onAccesoPermitido = { accesoPermitido = true },
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

            Button(onClick = onBack, colors = ButtonDefaults.buttonColors(backgroundColor = Naranja)) {
                Text("Volver a Usuario", color = Blanco)
            }

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
                        Button(
                            onClick = onBorrarClick,
                            colors = ButtonDefaults.buttonColors(backgroundColor = Negro),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(item, color = Blanco, fontSize = 18.sp)
                        }
                    } else {
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

@Composable
fun TecnicoScreen(onUsuarioClick: () -> Unit) {
    var showCalibrarPeso by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }

    if (showCalibrarPeso) {
        CalibrarPeso(onBack = { showCalibrarPeso = false })
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
                color = Naranja
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
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
                        onClick = { showErrorDialog = true }
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
                        texto = "contraseña",
                        onClick = {}
                    )
                }
                item {
                    BotonConImagen(
                        imagen = painterResource(Res.drawable.reiniciar),
                        texto = "Reiniciar dispositivo",
                        onClick = {
                            reiniciarDispositivo()
                            onUsuarioClick()
                        }
                    )
                }
                item {
                    BotonConImagen(
                        imagen = painterResource(Res.drawable.usuario),
                        texto = "Usuario",
                        onClick = onUsuarioClick
                    )
                }
            }

            if (showErrorDialog) {
                ErrorDialog(onDismiss = { showErrorDialog = false })
            }
        }
    }
}

@Composable
fun BotonConImagen(imagen: Painter, texto: String, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clickable(onClick = onClick)
                .background(Negro)
        ) {
            Image(painter = imagen, contentDescription = null, modifier = Modifier.fillMaxSize())
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = texto, color = AzulCian)
    }
}

@Composable
fun ErrorDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Error", color = Negro) },
        text = { Text(text = "Servicio en mantenimiento", color = Negro) },
        confirmButton = {
            Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(backgroundColor = AzulCian)) {
                Text("Aceptar", color = Blanco)
            }
        },
        backgroundColor = Blanco,
        contentColor = Negro
    )
}

fun reiniciarDispositivo() {
    val filePath = "C:\\Users\\Hp\\AndroidStudioProjects\\AppEgaraPlus\\composeApp\\src\\commonMain\\kotlin\\org\\affinity\\project\\Tarifas.json"
    val tarifaCero = Tarifa("0 céntimos", "0 céntimos", "0 céntimos")
    println("Dispositivo reiniciado y tarifas establecidas en cero.")
}
