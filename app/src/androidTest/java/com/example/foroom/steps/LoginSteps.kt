package com.example.foroom.steps

import android.os.SystemClock
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.foroom.Helper.waitUntilVisible
import com.example.foroom.pages.HomePage
import com.example.foroom.pages.LoginPage
import com.example.foroom.pages.ProfilePage

class LoginSteps {

    private val loginPage = LoginPage()
    private val homePage = HomePage()
    private val profilePage = ProfilePage()

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

    fun verifyHomeScreenIsDisplayed() {
        homePage.waitUntilDisplayed()
    }

    fun ensureLoggedOut(timeoutMs: Long = LOGGED_OUT_TIMEOUT_MS) {
        val deadline = SystemClock.uptimeMillis() + timeoutMs
        while (SystemClock.uptimeMillis() < deadline) {
            if (loginPage.isOnScreen()) return
            if (homePage.isDisplayed()) {
                homePage.openProfile()
                profilePage.tapSignOut()
                loginPage.waitUntilDisplayed()
                return
            }
            SystemClock.sleep(POLL_INTERVAL_MS)
        }
        throw AssertionError("Neither the login screen nor the home screen appeared within $timeoutMs ms")
    }

    fun logInAs(userName: String, password: String) {
        ensureLoggedOut()
        verifyLoginScreenIsDisplayed()
        logIn(userName, password)
        verifyHomeScreenIsDisplayed()
    }

    companion object {
        private const val ERROR_TIMEOUT_SEC = 10L
        private const val LOGGED_OUT_TIMEOUT_MS = 20_000L
        private const val POLL_INTERVAL_MS = 250L
    }
}
