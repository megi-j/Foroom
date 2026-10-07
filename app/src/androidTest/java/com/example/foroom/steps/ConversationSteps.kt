package com.example.foroom.steps

import com.example.foroom.pages.ChatsPage
import com.example.foroom.pages.ConversationPage
import com.example.foroom.pages.HomePage

class ConversationSteps {
    private val homePage = HomePage()
    private val chatsPage = ChatsPage()
    private val conversationPage = ConversationPage()

    /**
     * Goes to the chat list, searches for [title], opens that chat and checks the right one is open.
     * Typing in the search box reloads the list, so a tap can land on a card that is just being
     * replaced. The tap is therefore retried a few times (bounded) until the chat really opens.
     */
    fun openChat(title: String) {
        homePage.openChats()
        chatsPage.waitUntilDisplayed()
        chatsPage.search(title)

        repeat(OPEN_ATTEMPTS) { attempt ->
            chatsPage.verifyChatCardDisplayed(title)
            if (attempt == 0) chatsPage.tapOpenChat(title) else chatsPage.clickOpenChatDirectly(title)
            if (conversationPage.isOpenedWithin(title, OPEN_TIMEOUT_MS)) {
                conversationPage.verifyOpened(title)
                return
            }
        }
        throw AssertionError("Chat \"$title\" did not open after $OPEN_ATTEMPTS attempts")
    }

    fun sendMessage(text: String) {
        conversationPage.enterMessage(text)
        conversationPage.tapSend()
    }

    fun sendMessageAndVerify(text: String) {
        sendMessage(text)
        conversationPage.verifyMessageDisplayed(text)
    }

    /** Sends [count] numbered messages and checks that the last one arrived. Returns the last text. */
    fun sendFillerMessages(count: Int, suffix: String): String {
        var last = ""
        for (i in 1..count) {
            last = "filler $i $suffix"
            sendMessage(last)
        }
        conversationPage.verifyMessageDisplayed(last)
        return last
    }

    fun verifyMessageDisplayed(text: String) = conversationPage.verifyMessageDisplayed(text)

    fun verifyMessageNotDisplayed(text: String) {
        if (conversationPage.isMessageDisplayedNow(text)) {
            throw AssertionError("\"$text\" should be outside the initially visible area")
        }
    }

    fun verifyMessageSender(text: String, sender: String) {
        conversationPage.verifyMessageDisplayed(text)
        conversationPage.verifySender(text, sender)
    }

    /** Swipes (with the project's swiper helper) through older history until [text] appears. */
    fun swipeToOlderMessage(text: String) {
        conversationPage.swipeUntilMessageDisplayed(text)
    }

    fun closeChat() {
        conversationPage.tapClose()
        chatsPage.waitUntilDisplayed()
    }

    private companion object {
        const val OPEN_ATTEMPTS = 3
        const val OPEN_TIMEOUT_MS = 5_000L
    }
}
