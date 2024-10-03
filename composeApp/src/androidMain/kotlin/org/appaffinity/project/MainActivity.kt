package org.appaffinity.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(color = MaterialTheme.colors.background) {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "menu_usuario") {
                    composable("menu_usuario") {
                        MenuUsuario(
                            onNavigateToTecnico = { navController.navigate("menu_tecnico") },
                            onNavigateToIdioma = { navController.navigate("idioma_screen") },
                            onNavigateToFechaHora = { navController.navigate("ventana_fecha_hora") },
                            onNavigateToTarifas = { navController.navigate("tarifa_screen") },
                            onNavigateToFicha = { navController.navigate("ficha_screen") }
                        )
                    }
                    composable("menu_tecnico") {
                        MenuTecnico(
                            onBack = { navController.navigateUp() },
                            onNavigateToUsuario = { navController.navigate("menu_usuario") }
                        )
                    }
                    composable("idioma_screen") {
                        IdiomaScreen(
                            onAceptarClick = { navController.navigateUp() }
                        )
                    }
                    composable("ventana_fecha_hora") {
                        VentanaFechaHora(
                            onClose = { navController.navigateUp() }
                        )
                    }
                    composable("tarifa_screen") {
                        TarifaScreen(
                            onClose = { navController.navigateUp() }
                        )
                    }
                    composable("ficha_screen") {
                        FichaScreen(
                            onClose = { navController.navigateUp() }
                        )
                    }
                }
            }
        }
    }
}
