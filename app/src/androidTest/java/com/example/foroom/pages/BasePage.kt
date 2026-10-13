package com.example.foroom.pages

import android.os.SystemClock
import android.view.View
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import com.example.design_system.R as DsR

abstract class BasePage {


    fun waitForDisplayed(matcher: Matcher<View>, timeoutMs: Long = DEFAULT_TIMEOUT_MS): ViewInteraction {
        val deadline = SystemClock.uptimeMillis() + timeoutMs
        var lastError: Throwable? = null

        while (SystemClock.uptimeMillis() < deadline) {
            try {
                return onView(matcher).check(matches(isDisplayed()))
            } catch (t: Throwable) {
                lastError = t
                SystemClock.sleep(POLL_INTERVAL_MS)
            }
        }
        throw AssertionError("View was not displayed within $timeoutMs ms: $matcher", lastError)
    }

    fun isDisplayedNow(matcher: Matcher<View>): Boolean = try {
        onView(matcher).check(matches(isDisplayed()))
        true
    } catch (_: Throwable) {
        false
    }

    protected fun tap(matcher: Matcher<View>, timeoutMs: Long = DEFAULT_TIMEOUT_MS) {
        waitForDisplayed(matcher, timeoutMs).perform(click())
    }

    protected fun typeInto(matcher: Matcher<View>, text: String) {
        waitForDisplayed(matcher).perform(replaceText(text))
        closeSoftKeyboard()
    }

    protected fun inputFieldOf(parentId: Int): Matcher<View> =
        allOf(withId(DsR.id.inputEditText), isDescendantOfA(withId(parentId)))

    companion object {
        const val DEFAULT_TIMEOUT_MS = 10_000L
        const val LONG_TIMEOUT_MS = 20_000L
        private const val POLL_INTERVAL_MS = 200L
    }
}
