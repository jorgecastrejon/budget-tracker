package org.jcastrejon.features.login.ui

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.*
import org.jcastrejon.features.login.R
import org.jcastrejon.user.AreCredentialsValid
import org.jcastrejon.user.IsFingerprintEnabled
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val isFingerPrintEnabled: IsFingerprintEnabled = mockk()
    private val areCredentialsValid: AreCredentialsValid = mockk()
    private val onSuccess: () -> Unit = mockk()
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        givenFingerprint(enabled = false)
        givenCredentials(valid = false)
        every { onSuccess.invoke() } just Runs
    }

    private fun init() {
        viewModel = LoginViewModel(
            isFingerPrintEnabled = isFingerPrintEnabled,
            areCredentialsValid = areCredentialsValid
        )
        composeTestRule.setContent {
            LoginScreen(viewModel = viewModel, onSuccess = onSuccess)
        }
    }

    @Test
    fun `show default state`() {
        launchesScreenRobot(composeTestRule) {
            defaultElementsAreShowed()
        }
    }

    @Test
    fun `given fingerprint is enabled then show composable`() {
        givenFingerprint(enabled = true)

        launchesScreenRobot(composeTestRule) {
            isFingerprintIconVisible(visible = true)
        }
    }

    @Test
    fun `when password is too short then show error`() {
        launchesScreenRobot(composeTestRule) {
            typePassword("123")
            isButtonEnabled(true)
            clickOnButton()
            errorIsShown(R.string.login_error_field_length)
        }
    }

    @Test
    fun `when password is not valid then show error`() {
        launchesScreenRobot(composeTestRule) {
            typePassword("111111")
            clickOnButton()
            errorIsShown(R.string.login_error)
            coVerify { areCredentialsValid.invoke("111111") }
        }
    }

    @Test
    fun `when password is valid then continue`() {
        givenCredentials(valid = true)

        launchesScreenRobot(composeTestRule) {
            typePassword("123456")
            clickOnButton()
            coVerify { areCredentialsValid.invoke("123456") }
            verify { onSuccess.invoke() }
        }
    }

    private fun givenFingerprint(enabled: Boolean) {
        coEvery { isFingerPrintEnabled.invoke() } returns enabled
    }

    private fun givenCredentials(valid: Boolean) {
        coEvery { areCredentialsValid.invoke(any()) } returns valid
    }

    private fun launchesScreenRobot(
        composeTestRule: ComposeContentTestRule,
        func: LaunchesScreenRobot.() -> Unit
    ) {
        init()
        LaunchesScreenRobot(composeTestRule).func()
    }
}
