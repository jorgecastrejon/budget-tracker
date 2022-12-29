package org.jcastrejon.features.login.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.test.platform.app.InstrumentationRegistry
import org.jcastrejon.features.login.R
import org.jcastrejon.features.login.ui.LoginTestTag.CONTINUE_BUTTON
import org.jcastrejon.features.login.ui.LoginTestTag.FINGERPRINT_ICON
import org.jcastrejon.features.login.ui.LoginTestTag.PASSWORD_FIELD

internal class LaunchesScreenRobot(
    private val composeTestRule: ComposeContentTestRule
) {
    private val context: Context = InstrumentationRegistry.getInstrumentation().context

    private val header by lazy { composeTestRule.onNodeWithText(R.string.login_introduce_password) }

    private val passwordField by lazy { composeTestRule.onNodeWithTag(PASSWORD_FIELD) }

    private val fingerprintIcon by lazy { composeTestRule.onNodeWithTag(FINGERPRINT_ICON) }

    private val button by lazy { composeTestRule.onNodeWithTag(CONTINUE_BUTTON) }


    fun defaultElementsAreShowed() {
        header.assertIsDisplayed()
        passwordField.assertIsDisplayed()
        passwordField.assert(hasText(""))
        fingerprintIcon.assertDoesNotExist()
        button.assertIsDisplayed()
        button.assert(isNotEnabled())
    }

    fun typePassword(password: String) {
        passwordField.performTextReplacement(password)
    }

    fun isFingerprintIconVisible(visible: Boolean) {
        if (visible) fingerprintIcon.assertIsDisplayed() else fingerprintIcon.assertDoesNotExist()
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
