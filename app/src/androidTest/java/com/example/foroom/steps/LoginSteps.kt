package com.example.foroom.steps

import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.foroom.Helper.waitUntilVisible
import com.example.foroom.pages.LoginPage

class LoginSteps {

    private val loginPage = LoginPage()

    fun verifyLoginScreenIsDisplayed() {
        loginPage.waitUntilDisplayed()
    }

    fun logIn(userName: String, password: String) {
        loginPage.enterUserName(userName)
        loginPage.enterPassword(password)
        loginPage.tapLogIn()
    }

    fun openRegistration() {
        loginPage.tapSignUp()
    }

    fun verifyUserNameError(expectedMessage: String) {
        loginPage.userNameError()
            .waitUntilVisible(ERROR_TIMEOUT_SEC)
            .check(matches(withText(expectedMessage)))
    }

    fun verifyPasswordError(expectedMessage: String) {
        loginPage.passwordError()
            .waitUntilVisible(ERROR_TIMEOUT_SEC)
            .check(matches(withText(expectedMessage)))
    }

    companion object {
        private const val ERROR_TIMEOUT_SEC = 10L
    }
}