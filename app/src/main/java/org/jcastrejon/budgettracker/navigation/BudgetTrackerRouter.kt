package org.jcastrejon.budgettracker.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jcastrejon.budgettracker.navigation.BudgetTrackerScreen.Registration
import org.jcastrejon.budgettracker.navigation.BudgetTrackerScreen.Login
import org.jcastrejon.features.login.navigation.loginRoute
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
        registrationRoute(onSuccess = {
            navController.navigate("temporary") {
                popUpTo(Registration()) { inclusive = true }
            }
        })

        loginRoute(onSuccess = {
            navController.navigate("temporary") {
                popUpTo(Login()) { inclusive = true }
            }
        })

        composable("temporary") {
            Box(
                modifier = Modifier.background(Color.Blue)
                    .fillMaxSize()
            )
        }
    }
}