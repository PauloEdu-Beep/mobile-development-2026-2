package com.example.mercadinho.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mercadinho.ui.screens.RecipeSuggestionsScreen
import com.example.mercadinho.ui.screens.ShoppingListScreen
import com.example.mercadinho.ui.screens.WelcomeScreen

object AppDestinations {
    const val WELCOME = "welcome"
    const val SHOPPING_LIST = "shopping_list"
    const val RECIPE_SUGGESTIONS = "recipe_suggestions"
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = AppDestinations.WELCOME
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(AppDestinations.WELCOME) {
            WelcomeScreen(
                onNavigateToShoppingList = {
                    navController.navigate(AppDestinations.SHOPPING_LIST)
                },
                onNavigateToSuggestions = {
                    navController.navigate(AppDestinations.RECIPE_SUGGESTIONS)
                }
            )
        }

        composable(AppDestinations.SHOPPING_LIST) {
            ShoppingListScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onNavigateToSuggestions = {
                    navController.navigate(AppDestinations.RECIPE_SUGGESTIONS)
                }
            )
        }

        composable(AppDestinations.RECIPE_SUGGESTIONS) {
            RecipeSuggestionsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
