package com.app.examenmoviles.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.examenmoviles.presentation.screens.home.HomeScreen
import com.app.examenmoviles.presentation.screens.sudoku.SudokuScreen

sealed class Screen(
    val route: String,
) {
    data object Home : Screen("home")

    data object Sudoku : Screen("sudoku/{size}/{difficulty}") {
        fun createRoute(
            size: Int,
            difficulty: String,
        ) = "sudoku/$size/$difficulty"
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController, // El controlador que maneja la navegación
        startDestination = Screen.Home.route, // Indica qué pantalla se muestra primero
        modifier = modifier, // Para personalizar el aspecto si es necesario)
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToSudoku = { size, difficulty ->
                    navController.navigate(Screen.Sudoku.createRoute(size, difficulty))
                },
            )
        }

        composable(Screen.Sudoku.route) { backStack ->
            val size = backStack.arguments?.getString("size")?.toInt() ?: 9
            val difficulty = backStack.arguments?.getString("difficulty") ?: "easy"
            SudokuScreen(size = size, difficulty = difficulty)
        }
    }
}
