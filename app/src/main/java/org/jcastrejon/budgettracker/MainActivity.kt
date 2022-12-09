package org.jcastrejon.budgettracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jcastrejon.features.register.navigation.RegistrationFeatureRoute
import org.jcastrejon.user.User
import org.jcastrejon.user.UserManager
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userManager: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        var destination: String? = null

        lifecycleScope.launch(Dispatchers.Default) {
            destination = userManager.get().asInitialRoute()
        }

        splashScreen.setKeepOnScreenCondition(condition = { destination == null })
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            BudgetTrackerApp(entryPoint = destination.orEmpty())
        }
    }
}

private fun User?.asInitialRoute(): String =
    when {
        this == null -> RegistrationFeatureRoute
        authenticateEachSession -> ""
        else -> ""
    }
