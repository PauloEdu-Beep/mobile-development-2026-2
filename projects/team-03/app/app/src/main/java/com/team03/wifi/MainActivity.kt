package com.team03.wifi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.team03.wifi.ui.theme.WifiTheme

// Rotas de navegação (Sprint 03)
private const val ROUTE_HOME = "home"
private const val ROUTE_DETAILS = "details/{status}"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WifiTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = ROUTE_HOME,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(ROUTE_HOME) {
                            HomeScreen(
                                onSeeDetails = { status ->
                                    navController.navigate("details/${status.name}")
                                }
                            )
                        }

                        composable(
                            route = ROUTE_DETAILS,
                            arguments = listOf(navArgument("status") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val statusName = backStackEntry.arguments?.getString("status") ?: SignalStatus.NONE.name
                            val status = SignalStatus.valueOf(statusName)

                            DetailsScreen(
                                status = status,
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}