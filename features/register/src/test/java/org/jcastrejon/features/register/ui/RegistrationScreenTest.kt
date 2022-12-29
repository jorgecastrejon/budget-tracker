package org.jcastrejon.features.register.ui

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.*
import org.jcastrejon.features.register.R
import org.jcastrejon.user.CreateUser
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegistrationScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val createUser: CreateUser = mockk()
    private val onSuccess: () -> Unit = mockk()
    private lateinit var viewModel: RegistrationViewModel

    @Before
    fun setUp() {
        viewModel = RegistrationViewModel(createUser = createUser)
        coEvery { createUser.invoke(any(), any()) } just Runs
        every { onSuccess.invoke() } just Runs

        composeTestRule.setContent {
            RegistrationScreen(viewModel = viewModel, onSuccess = onSuccess)
        }
    }

    @Test
    fun `show default state`() {
        launchesScreenRobot(composeTestRule) {
            defaultElementsAreShowed()
        }
    }

    @Test
    fun `when there is only one field filled then the button is still disabled`() {
        launchesScreenRobot(composeTestRule) {
            typePassword("123456")
            isButtonEnabled(false)
        }
    }

    @Test
    fun `when passwords are too short then show error`() {
        launchesScreenRobot(composeTestRule) {
            typePassword("123")
            typeConfirmPassword("12")
            isButtonEnabled(true)
            clickOnButton()
            errorIsShown(R.string.register_error_field_length)
        }
    }

    @Test
    fun `when passwords do not match then show error`() {
        launchesScreenRobot(composeTestRule) {
            typePassword("111111")
            typeConfirmPassword("123456")
            clickOnButton()
            errorIsShown(R.string.register_error_field_match)
        }
    }

    @Test
    fun `when fields are valid then a user is created`() {
        launchesScreenRobot(composeTestRule) {
            typePassword("123456")
            typeConfirmPassword("123456")
            toggleCheckbox()
            clickOnButton()
            coVerify { createUser.invoke("123456", true) }
            verify { onSuccess.invoke() }
        }
    }

    private fun launchesScreenRobot(
        composeTestRule: ComposeContentTestRule,
        func: LaunchesScreenRobot.() -> Unit
    ) = LaunchesScreenRobot(composeTestRule).func()
}
