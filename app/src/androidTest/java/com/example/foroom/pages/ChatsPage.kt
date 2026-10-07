package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import com.example.design_system.components.chat.ForoomChatCardView
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import com.example.design_system.R as DsR

class ChatsPage : BasePage() {
    private val searchField = inputFieldOf(R.id.searchChatInput)
    private val chatsRecyclerView = withId(R.id.chatsRecyclerView)

    private fun chatCardTitle(name: String) = allOf(
        withId(DsR.id.chatTitleTextView),
        isDescendantOfA(withId(R.id.chatsRecyclerView)),
        withText(name)
    )

    private fun chatCard(name: String): Matcher<View> = allOf(
        isAssignableFrom(ForoomChatCardView::class.java),
        isDescendantOfA(withId(R.id.chatsRecyclerView)),
        hasDescendant(allOf(withId(DsR.id.chatTitleTextView), withText(name)))
    )

    private fun openButtonOf(name: String): Matcher<View> =
        allOf(withId(DsR.id.sendMessageButton), isDescendantOfA(chatCard(name)))

    fun waitUntilDisplayed() = waitForDisplayed(searchField, LONG_TIMEOUT_MS)

    fun search(text: String) = typeInto(searchField, text)

    fun verifyChatCardDisplayed(name: String) {
        waitForDisplayed(chatsRecyclerView)
        waitForDisplayed(chatCardTitle(name), LONG_TIMEOUT_MS)
    }

    fun tapOpenChat(name: String) = tap(openButtonOf(name), LONG_TIMEOUT_MS)

    fun clickOpenChatDirectly(name: String) {
        waitForDisplayed(openButtonOf(name), LONG_TIMEOUT_MS).perform(object : ViewAction {
            override fun getConstraints(): Matcher<View> = isDisplayed()
            override fun getDescription() = "performClick on the open-chat button"
            override fun perform(uiController: UiController, view: View) {
                view.performClick()
                uiController.loopMainThreadUntilIdle()
            }
        })
    }
}
