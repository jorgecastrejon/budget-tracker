package org.jcastrejon.features.register.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.test.platform.app.InstrumentationRegistry
import org.jcastrejon.features.register.R
import org.jcastrejon.features.register.ui.RegisterTestTag.CHECKBOX
import org.jcastrejon.features.register.ui.RegisterTestTag.CONFIRM_PASSWORD_FIELD
import org.jcastrejon.features.register.ui.RegisterTestTag.PASSWORD_FIELD
import org.jcastrejon.features.register.ui.RegisterTestTag.START_BUTTON

internal open class LaunchesScreenRobot(
    private val composeTestRule: ComposeContentTestRule
) {
    private val context: Context = InstrumentationRegistry.getInstrumentation().context

    private val header by lazy { composeTestRule.onNodeWithText(R.string.register_create_password) }

    private val passwordField by lazy { composeTestRule.onNodeWithTag(PASSWORD_FIELD) }

    private val confirmPasswordField by lazy { composeTestRule.onNodeWithTag(CONFIRM_PASSWORD_FIELD) }

    private val button by lazy { composeTestRule.onNodeWithTag(START_BUTTON) }

    private val checkbox by lazy { composeTestRule.onNodeWithTag(CHECKBOX) }


    fun defaultElementsAreShowed() {
        header.assertIsDisplayed()
        passwordField.assertIsDisplayed()
        passwordField.assert(hasText(""))
        confirmPasswordField.assertIsDisplayed()
        confirmPasswordField.assert(hasText(""))
        checkbox.assertIsDisplayed()
        checkbox.assert(isOff())
        button.assertIsDisplayed()
        button.assert(isNotEnabled())
    }

    fun typePassword(password: String) {
        passwordField.performTextReplacement(password)
    }

    fun typeConfirmPassword(confirmPassword: String) {
        confirmPasswordField.performTextReplacement(confirmPassword)
    }

    fun toggleCheckbox() {
        checkbox.performClick()
    }

    fun isButtonEnabled(enabled: Boolean) {
        button.assert(if (enabled) isEnabled() else isNotEnabled())
    }

    fun clickOnButton() {
        button.performClick()
    }

    fun errorIsShown(@StringRes resource: Int) {
        composeTestRule.onNodeWithText(resource).assertIsDisplayed()
    }

    private fun SemanticsNodeInteractionsProvider.onNodeWithText(
        @StringRes resource: Int
    ): SemanticsNodeInteraction = onNode(hasText(context.getString(resource)))
}
