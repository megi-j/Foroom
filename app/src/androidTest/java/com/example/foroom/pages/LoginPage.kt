package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasSibling
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import com.example.foroom.Helper.waitUntilVisible
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import com.example.design_system.R as DesignR

class LoginPage {
    private val logInButton: Matcher<View> = withId(R.id.logInButton)
    private val userNameInput: Matcher<View> =
        allOf(withId(R.id.userNameInput), hasSibling(logInButton))
    private val passwordInput: Matcher<View> =
        allOf(withId(R.id.passwordInput), hasSibling(logInButton))
    private val signUpButton: Matcher<View> =
        allOf(withId(R.id.signUpButton), hasSibling(logInButton))

    fun waitUntilDisplayed(timeoutSec: Long = SCREEN_TIMEOUT_SEC) {
        onView(logInButton).waitUntilVisible(timeoutSec)
        onView(userNameInput).check(matches(isDisplayed()))
        onView(passwordInput).check(matches(isDisplayed()))
    }

    fun enterUserName(userName: String) {
        onView(editTextOf(userNameInput)).perform(replaceText(userName), closeSoftKeyboard())
    }

    fun enterPassword(password: String) {
        onView(editTextOf(passwordInput)).perform(replaceText(password), closeSoftKeyboard())
    }

    fun tapLogIn() {
        onView(logInButton).perform(click())
    }

    fun tapSignUp() {
        onView(signUpButton).perform(click())
    }

    fun userNameError(): ViewInteraction = onView(errorOf(userNameInput))

    fun passwordError(): ViewInteraction = onView(errorOf(passwordInput))

    private fun editTextOf(input: Matcher<View>): Matcher<View> =
        allOf(withId(DesignR.id.inputEditText), isDescendantOfA(input))

    private fun errorOf(input: Matcher<View>): Matcher<View> =
        allOf(withId(DesignR.id.descriptionTextView), isDescendantOfA(input))

    companion object {
        const val SCREEN_TIMEOUT_SEC = 10L
    }
}