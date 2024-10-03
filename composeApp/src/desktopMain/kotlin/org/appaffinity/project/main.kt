package org.appaffinity.project

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.window.Window
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun main() {
    Window(title = "Aplicación Affinity", size = IntSize(800, 600)) {
        MaterialTheme {
            AppContent()
        }
    }
}

@Composable
fun AppContent() {
    val navController = rememberNavController()



    NavHost(navController, startDestination = "menu_usuario") {
        composable("menu_usuario") {
            MenuUsuario(
                onNavigateToTecnico = { navController.navigate("menu_tecnico") },
                onNavigateToIdioma = { navController.navigate("idioma_screen") },
                onNavigateToFechaHora = { navController.navigate("ventana_fecha_hora") },
                onNavigateToTarifas = { navController.navigate("tarifa_screen") },
                onNavigateToFicha = { navController.navigate("ficha_screen") }
            )
        }
    }
}
