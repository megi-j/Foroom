package com.example.foroom.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.foroom.presentation.ui.activity.ForoomActivity
import com.example.foroom.steps.ConversationSteps
import com.example.foroom.steps.LoginSteps
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChatConversationTests {

    @get:Rule
    val activityRule = ActivityScenarioRule(ForoomActivity::class.java)

    private val loginSteps = LoginSteps()
    private val conversationSteps = ConversationSteps()

    @Before
    fun setUp() {
        loginSteps.ensureLoggedOut()
    }

    @Test
    fun userA_sendsMessageInJohnWeek_andMessageStaysAfterReopeningChat() {
        val message = "let's go for a drink ${uniqueSuffix()}"

        loginSteps.logInAs(USER_A, PASSWORD_A)
        conversationSteps.openChat(JOHN_WEEK_CHAT)
        conversationSteps.sendMessageAndVerify(message)

        conversationSteps.closeChat()
        conversationSteps.openChat(JOHN_WEEK_CHAT)
        conversationSteps.verifyMessageDisplayed(message)
    }

    @Test
    fun userA_asksFavouriteModuleQuestion_inOwnChat() {
        val question = "Which module do you like most in the Automation Academy? ${uniqueSuffix()}"

        loginSteps.logInAs(USER_A, PASSWORD_A)
        conversationSteps.openChat(OWN_CHAT)
        conversationSteps.sendMessageAndVerify(question)
    }

    @Test
    fun userB_readsOlderGreetingWithSwipe_andUserA_seesReply() {
        val suffix = uniqueSuffix()
        val greeting = "Hello from User A $suffix"
        val reply = "Hi! Reply from User B $suffix"

        loginSteps.logInAs(USER_A, PASSWORD_A)
        conversationSteps.openChat(SHARED_CHAT)
        conversationSteps.sendMessageAndVerify(greeting)
        val lastFiller = conversationSteps.sendFillerMessages(FILLER_MESSAGES, suffix)
        conversationSteps.closeChat()


        loginSteps.logInAs(USER_B, PASSWORD_B)
        conversationSteps.openChat(SHARED_CHAT)
        conversationSteps.verifyMessageDisplayed(lastFiller)
        conversationSteps.verifyMessageNotDisplayed(greeting)
        conversationSteps.swipeToOlderMessage(greeting)
        conversationSteps.verifyMessageSender(greeting, USER_A)

        conversationSteps.sendMessageAndVerify(reply)
        conversationSteps.closeChat()

        loginSteps.logInAs(USER_A, PASSWORD_A)
        conversationSteps.openChat(SHARED_CHAT)
        conversationSteps.verifyMessageSender(reply, USER_B)
    }

    private fun uniqueSuffix(): String = (System.currentTimeMillis() % 1_000_000).toString()

    companion object {
        private const val USER_A = "megi_test"
        private const val PASSWORD_A = "MyPass123!"
        private const val USER_B = "megi_friend"
        private const val PASSWORD_B = "friend1"

        private const val JOHN_WEEK_CHAT = "johnWeek"
        private const val OWN_CHAT = "Megi Jabanashvili"
        private const val SHARED_CHAT = "something"

        private const val FILLER_MESSAGES = 25
    }
}
