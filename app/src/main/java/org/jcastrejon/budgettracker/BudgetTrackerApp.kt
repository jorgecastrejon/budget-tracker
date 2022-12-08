package org.jcastrejon.budgettracker

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import org.jcastrejon.budgettracker.navigation.BudgetTrackerRouter
import org.jcastrejon.designsystem.BudgetTrackerTheme

@Composable
fun BudgetTrackerApp(
    entryPoint: String
) {
    BudgetTrackerTheme(
        dynamicColor = false
    ) {
        val navController = rememberNavController()
        BudgetTrackerRouter(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background),
            navController = navController,
            startDestination = entryPoint
        )
    }
}
