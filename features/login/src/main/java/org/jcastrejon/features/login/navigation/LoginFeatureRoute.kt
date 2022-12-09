package org.jcastrejon.features.login.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.jcastrejon.features.login.ui.LoginScreen
import org.jcastrejon.features.login.ui.LoginViewModel

const val LoginFeatureRoute = "login"

fun NavGraphBuilder.loginRoute(onSuccess: () -> Unit) {
    composable(LoginFeatureRoute) {
        val viewModel: LoginViewModel = hiltViewModel()
        LoginScreen(viewModel = viewModel, onSuccess = onSuccess)
    }
}
