package org.jcastrejon.features.register.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.jcastrejon.features.register.ui.RegistrationScreen
import org.jcastrejon.features.register.ui.RegistrationViewModel

const val RegistrationFeatureRoute = "registration"

fun NavGraphBuilder.registrationRoute(onSuccess: () -> Unit) {
    composable(RegistrationFeatureRoute) {
        val viewModel: RegistrationViewModel = hiltViewModel()
        RegistrationScreen(viewModel = viewModel, onSuccess = onSuccess)
    }
}
