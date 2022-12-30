package org.jcastrejon.benchmark

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BaselineProfileGenerator {

    @get:Rule
    val rule = BaselineProfileRule()

    @Test
    fun generate() {
        rule.collectBaselineProfile("org.jcastrejon.budgettracker") {
            startApplication()
            fillForm()
            goToOverview()
        }
    }


    private fun MacrobenchmarkScope.startApplication() {
        pressHome()
        startActivityAndWait()
        device.wait(Until.findObject(By.res("password_field")), 5_000)
    }


    private fun MacrobenchmarkScope.fillForm() {
        device.findObject(By.res("password_field")).text = "123456"
        device.findObject(By.res("confirm_password_field")).text = "123456"
        val button = device.findObject(By.res("start_button"))
        button.wait(Until.enabled(true), 5_500)
//        button.click()
    }

    private fun MacrobenchmarkScope.goToOverview() {
        //todo complete after overview screen is finished
    }
}