package com.example.foroom.pages

import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R

/** Bottom navigation bar shown on the home screen after login. */
class HomePage : BasePage() {
    private val navBar = withId(R.id.navBar)
    private val chatsTab = withId(R.id.homeNavigationChats)
    private val createChatTab = withId(R.id.homeNavigationCreateChat)
    private val profileTab = withId(R.id.homeNavigationProfile)

    fun waitUntilDisplayed() = waitForDisplayed(navBar, LONG_TIMEOUT_MS)

    fun isDisplayed(): Boolean = isDisplayedNow(navBar)

    fun openChats() = tap(chatsTab)

    fun openCreateChat() = tap(createChatTab)

    fun openProfile() = tap(profileTab)
}
