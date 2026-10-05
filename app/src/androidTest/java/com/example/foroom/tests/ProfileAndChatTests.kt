package com.example.foroom.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.foroom.presentation.ui.activity.ForoomActivity
import com.example.foroom.steps.ChatSteps
import com.example.foroom.steps.LoginSteps
import com.example.foroom.steps.ProfileSteps
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProfileAndChatTests {

    @get:Rule
    val activityRule = ActivityScenarioRule(ForoomActivity::class.java)

    private val loginSteps = LoginSteps()
    private val profileSteps = ProfileSteps()
    private val chatSteps = ChatSteps()

    @Before
    fun setUp() {
        loginSteps.ensureLoggedOut()
    }

    @Test
    fun changePassword_thenLoginWithNewPassword() {
        loginSteps.verifyLoginScreenIsDisplayed()
        loginSteps.logIn(USER_NAME, PASSWORD)
        loginSteps.verifyHomeScreenIsDisplayed()

        profileSteps.openProfile()
        profileSteps.changePassword(NEW_PASSWORD)
        loginSteps.verifyLoginScreenIsDisplayed()

        loginSteps.logIn(USER_NAME, NEW_PASSWORD)
        loginSteps.verifyHomeScreenIsDisplayed()

        profileSteps.openProfile()
        profileSteps.changePassword(PASSWORD)
        loginSteps.verifyLoginScreenIsDisplayed()
    }

    @Test
    fun changeLanguage_georgianToEnglishAndBack() {
        loginSteps.verifyLoginScreenIsDisplayed()
        loginSteps.logIn(USER_NAME, PASSWORD)
        loginSteps.verifyHomeScreenIsDisplayed()
        profileSteps.openProfile()

        profileSteps.selectGeorgian()
        profileSteps.verifyGeorgianProfileLabels()

        profileSteps.selectEnglish()
        profileSteps.verifyEnglishProfileLabels()

        profileSteps.selectGeorgian()
        profileSteps.verifyGeorgianProfileLabels()
    }

    @Test
    fun createChat_andFindItInChatList() {
        val chatName = "$STUDENT_FULL_NAME ${System.currentTimeMillis() % 1_000_000}"

        loginSteps.verifyLoginScreenIsDisplayed()
        loginSteps.logIn(USER_NAME, PASSWORD)
        loginSteps.verifyHomeScreenIsDisplayed()

        chatSteps.openCreateChat()
        chatSteps.createChat(chatName)
        chatSteps.verifyCreatedChatOpened(chatName)

        chatSteps.closeChat()
        chatSteps.searchAndVerifyChat(chatName)
    }

    companion object {
        private const val USER_NAME = "megi_test"
        private const val PASSWORD = "MyPass123!"
        private const val NEW_PASSWORD = "MyNewPass456!"
        private const val STUDENT_FULL_NAME = "Megi Jabanashvili"
    }
}
