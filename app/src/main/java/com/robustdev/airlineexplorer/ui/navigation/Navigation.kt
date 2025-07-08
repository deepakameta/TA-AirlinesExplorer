package com.robustdev.airlineexplorer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.robustdev.airlineexplorer.data.model.Airline
import com.robustdev.airlineexplorer.ui.screens.AirlineDetailScreen
import com.robustdev.airlineexplorer.ui.screens.AirlinesListScreen

sealed class Screen(val route: String) {
    data object List : Screen("airlines_list")
    data object Detail : Screen("airline_detail")
}

@Composable
fun NavigationGraph(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.List.route,
    ) {

        var selectedAirline: Airline? = null

        composable(Screen.List.route) {
            AirlinesListScreen(navigateToDetailScreen = {
                selectedAirline = it
                navController.navigate(Screen.Detail.route)
            })
        }
        composable("airline_detail") {
            AirlineDetailScreen(selectedAirline!!)
        }
    }
}