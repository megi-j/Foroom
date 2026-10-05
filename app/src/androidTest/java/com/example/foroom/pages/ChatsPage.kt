package com.example.foroom.pages

import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
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

    fun waitUntilDisplayed() = waitForDisplayed(searchField, LONG_TIMEOUT_MS)

    fun search(text: String) = typeInto(searchField, text)

    fun verifyChatCardDisplayed(name: String) {
        waitForDisplayed(chatsRecyclerView)
        waitForDisplayed(chatCardTitle(name), LONG_TIMEOUT_MS)
    }
}
