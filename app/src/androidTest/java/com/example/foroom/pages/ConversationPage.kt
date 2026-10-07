package com.example.foroom.pages

import android.os.SystemClock
import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.alternator.foroom.R
import com.example.foroom.Helper.swiper
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import com.example.design_system.R as DsR

/** The open chat (conversation) screen. */
class ConversationPage : BasePage() {
    private val messagesList = withId(R.id.messagesRecyclerView)
    private val messageField = inputFieldOf(R.id.messageInput)
    private val sendButton = allOf(withId(R.id.sendMessageButton), isDescendantOfA(withId(R.id.messageInput)))
    private val closeButton = withId(R.id.closeButton)

    private fun chatTitle(title: String) = allOf(
        withId(DsR.id.chatNameTextView),
        isDescendantOfA(withId(R.id.chatHeaderView)),
        withText(title)
    )

    /** A single message bubble (ForoomMessageView) that contains exactly [text]. */
    private fun messageBubble(text: String): Matcher<View> = allOf(
        withId(R.id.messageView),
        isDescendantOfA(messagesList),
        hasDescendant(allOf(withId(DsR.id.messageTextView), withText(text)))
    )

    private fun messageText(text: String): Matcher<View> =
        allOf(withId(DsR.id.messageTextView), withText(text), isDescendantOfA(messagesList))

    private fun senderOf(text: String, sender: String): Matcher<View> = allOf(
        withId(DsR.id.userNameTextView),
        withText(sender),
        isDescendantOfA(messageBubble(text))
    )

    fun verifyOpened(title: String) {
        waitForDisplayed(chatTitle(title), LONG_TIMEOUT_MS)
        waitForDisplayed(messagesList)
    }

    /** Non-failing check: did the chat with [title] open within [timeoutMs]? */
    fun isOpenedWithin(title: String, timeoutMs: Long): Boolean {
        val deadline = SystemClock.uptimeMillis() + timeoutMs
        while (SystemClock.uptimeMillis() < deadline) {
            if (isDisplayedNow(chatTitle(title))) return true
            SystemClock.sleep(200)
        }
        return false
    }

    fun enterMessage(text: String) = typeInto(messageField, text)

    /** The send button is disabled until the chat connects and while a message is being sent. */
    fun tapSend() = tap(allOf(sendButton, isEnabled()), LONG_TIMEOUT_MS)

    fun tapClose() = tap(closeButton)

    fun verifyMessageDisplayed(text: String) {
        waitForDisplayed(messageText(text), LONG_TIMEOUT_MS)
    }

    fun verifySender(text: String, sender: String) {
        waitForDisplayed(senderOf(text, sender))
    }

    fun isMessageDisplayedNow(text: String): Boolean = isDisplayedNow(messageText(text))

    fun swipeTowardsOlderMessages() {
        val (top, height) = screenTopAndHeightOf(messagesList)
        val start = top + (height * SWIPE_START_FRACTION).toInt()
        val end = top + (height * SWIPE_END_FRACTION).toInt()
        swiper(start, end, SWIPE_DURATION_MS)
    }

    fun swipeUntilMessageDisplayed(text: String, maxSwipes: Int = MAX_SWIPES) {
        repeat(maxSwipes) {
            if (isMessageDisplayedNow(text)) return
            swipeTowardsOlderMessages()
            SystemClock.sleep(HISTORY_LOAD_PAUSE_MS) // give the next history page time to load
        }
        if (!isMessageDisplayedNow(text)) {
            throw AssertionError("Message \"$text\" was not found after $maxSwipes swipes")
        }
    }

    private fun screenTopAndHeightOf(matcher: Matcher<View>): Pair<Int, Int> {
        waitForDisplayed(matcher)
        var top = 0
        var height = 0
        onView(matcher).perform(object : ViewAction {
            override fun getConstraints(): Matcher<View> = isDisplayed()
            override fun getDescription() = "read on-screen position"
            override fun perform(uiController: UiController, view: View) {
                val location = IntArray(2)
                view.getLocationOnScreen(location)
                top = location[1]
                height = view.height
            }
        })
        return top to height
    }

    private companion object {
        // Middle part of the list: avoids the header at the top and the input at the bottom.
        const val SWIPE_START_FRACTION = 0.35
        const val SWIPE_END_FRACTION = 0.75
        const val SWIPE_DURATION_MS = 300
        const val MAX_SWIPES = 20
        const val HISTORY_LOAD_PAUSE_MS = 500L
    }
}
