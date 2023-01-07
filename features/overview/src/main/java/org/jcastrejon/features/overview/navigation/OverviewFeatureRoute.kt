package org.jcastrejon.features.overview.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.jcastrejon.features.overview.ui.OverviewScreen

const val OverviewFeatureRoute = "Overview"

fun NavGraphBuilder.overviewRoute(
    onAddTransaction: () -> Unit,
    onTransactionClicked: (Int) -> Unit,
    onSearchActionClicked: () -> Unit,
) {
    composable(OverviewFeatureRoute) {

        OverviewScreen(
            onAddTransactionClicked = onAddTransaction,
            onTransactionClicked = onTransactionClicked,
            onSearchActionClicked = onSearchActionClicked
        )
    }
}
