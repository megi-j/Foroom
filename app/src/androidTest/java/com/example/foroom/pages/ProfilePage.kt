package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import com.example.design_system.R as DsR

class ProfilePage : BasePage() {
    private val changePasswordItem = withId(R.id.changePasswordItem)
    private val changeLanguageItem = withId(R.id.changeLanguageItem)
    private val signOutItem = withId(R.id.signOutItem)

    private fun titleOf(itemId: Int, text: String): Matcher<View> =
        allOf(withId(DsR.id.listItemTextView), isDescendantOfA(withId(itemId)), withText(text))

    fun waitUntilDisplayed() = waitForDisplayed(signOutItem, LONG_TIMEOUT_MS)

    fun tapChangePassword() = tap(changePasswordItem)

    fun tapChangeLanguage() = tap(changeLanguageItem, LONG_TIMEOUT_MS)

    fun tapSignOut() = tap(signOutItem)

    fun verifyChangeLanguageLabel(text: String) {
        waitForDisplayed(titleOf(R.id.changeLanguageItem, text), LONG_TIMEOUT_MS)
    }

    fun verifySignOutLabel(text: String) {
        waitForDisplayed(titleOf(R.id.signOutItem, text), LONG_TIMEOUT_MS)
    }
}
