package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasSibling
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withParentIndex
import com.alternator.foroom.R
import com.example.design_system.components.image_chooser.ImageChooserItemView
import com.example.design_system.components.image_chooser.ImageChooserListView
import com.example.foroom.Helper.waitUntilVisible
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import com.example.design_system.R as DesignR

class RegistrationPage {
    private val repeatPasswordInput: Matcher<View> = withId(R.id.repeatPasswordInput)
    private val avatarList: Matcher<View> = withId(R.id.listView)
    private val userNameInput: Matcher<View> =
        allOf(withId(R.id.userNameInput), hasSibling(repeatPasswordInput))
    private val passwordInput: Matcher<View> =
        allOf(withId(R.id.passwordInput), hasSibling(repeatPasswordInput))
    private val signUpButton: Matcher<View> =
        allOf(withId(R.id.signUpButton), hasSibling(avatarList))

    fun waitUntilDisplayed(timeoutSec: Long = SCREEN_TIMEOUT_SEC) {
        onView(repeatPasswordInput).waitUntilVisible(timeoutSec)
        onView(userNameInput).check(matches(isDisplayed()))
        onView(passwordInput).check(matches(isDisplayed()))
        onView(avatarList).check(matches(isDisplayed()))
    }

    fun enterUserName(userName: String) {
        onView(editTextOf(userNameInput)).perform(replaceText(userName), closeSoftKeyboard())
    }

    fun enterPassword(password: String) {
        onView(editTextOf(passwordInput)).perform(replaceText(password), closeSoftKeyboard())
    }

    fun enterRepeatPassword(password: String) {
        onView(editTextOf(repeatPasswordInput)).perform(replaceText(password), closeSoftKeyboard())
    }

    fun waitForAvatarsLoaded(timeoutSec: Long = SCREEN_TIMEOUT_SEC) {
        val endTime = System.currentTimeMillis() + timeoutSec * 1000
        while (System.currentTimeMillis() < endTime) {
            var loaded = false
            onView(avatarList).check(ViewAssertion { view, noViewFoundException ->
                if (noViewFoundException == null) {
                    loaded = (view as ImageChooserListView).isChoosingEnabled
                }
            })
            if (loaded) return
            Thread.sleep(POLL_INTERVAL_MS)
        }
        throw AssertionError("Avatars were not loaded within $timeoutSec seconds")
    }

    fun selectAvatar(position: Int) {
        onView(avatarAt(position)).perform(click())
    }

    fun tapSignUp() {
        onView(signUpButton).perform(click())
    }

    private fun avatarAt(position: Int): Matcher<View> = allOf(
        isAssignableFrom(ImageChooserItemView::class.java),
        isDescendantOfA(avatarList),
        withParent(withParentIndex(0)),
        withParentIndex(position * 2)
    )

    private fun editTextOf(input: Matcher<View>): Matcher<View> =
        allOf(withId(DesignR.id.inputEditText), isDescendantOfA(input))

    companion object {
        const val SCREEN_TIMEOUT_SEC = 10L
        private const val POLL_INTERVAL_MS = 200L
    }
}