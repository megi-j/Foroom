package com.example.foroom.steps

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import com.example.foroom.Helper.waitUntilVisible
import com.example.foroom.pages.RegistrationPage

class RegistrationSteps {

    private val registrationPage = RegistrationPage()

    fun verifyRegistrationScreenIsDisplayed() {
        registrationPage.waitUntilDisplayed()
    }

    fun fillRegistrationForm(userName: String, password: String, repeatPassword: String) {
        registrationPage.enterUserName(userName)
        registrationPage.enterPassword(password)
        registrationPage.enterRepeatPassword(repeatPassword)
    }

    fun selectAvatar(position: Int) {
        registrationPage.waitForAvatarsLoaded()
        registrationPage.selectAvatar(position)
    }

    fun submitRegistration() {
        registrationPage.tapSignUp()
    }

    fun verifyHomeScreenIsDisplayed() {
        onView(withId(R.id.navBar)).waitUntilVisible(HOME_TIMEOUT_SEC)
    }

    companion object {
        private const val HOME_TIMEOUT_SEC = 15L
    }
}