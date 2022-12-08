package org.jcastrejon.budgettracker.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.jcastrejon.budgettracker.navigation.BudgetTrackerScreen.Registration
import org.jcastrejon.features.register.navigation.registrationRoute

@Composable
fun BudgetTrackerRouter(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Registration()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        registrationRoute(onSuccess = {})
    }
}