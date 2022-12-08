package org.jcastrejon.budgettracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jcastrejon.features.register.navigation.RegistrationFeatureRoute

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        var destination: String? = null

        lifecycleScope.launch(Dispatchers.Default) {
            // todo update once UserManager is introduced
            destination = RegistrationFeatureRoute
        }

        splashScreen.setKeepOnScreenCondition(condition = { destination == null })
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            BudgetTrackerApp(entryPoint = destination.orEmpty())
        }
    }
}
