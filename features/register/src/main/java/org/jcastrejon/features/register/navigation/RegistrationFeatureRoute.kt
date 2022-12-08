package org.jcastrejon.features.register.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.jcastrejon.features.register.ui.RegistrationScreen

const val RegistrationFeatureRoute = "registration"

fun NavGraphBuilder.registrationRoute(onSuccess: () -> Unit) {
    composable(RegistrationFeatureRoute) {
        RegistrationScreen(onSuccess = onSuccess)
    }
}
