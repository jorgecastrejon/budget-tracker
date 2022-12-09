package org.jcastrejon.budgettracker.navigation

import org.jcastrejon.features.login.navigation.LoginFeatureRoute
import org.jcastrejon.features.register.navigation.RegistrationFeatureRoute

sealed class BudgetTrackerScreen {

    object Registration : BudgetTrackerScreen() {
        operator fun invoke(): String = RegistrationFeatureRoute
    }

    object Login : BudgetTrackerScreen() {
        operator fun invoke(): String = LoginFeatureRoute
    }
}
