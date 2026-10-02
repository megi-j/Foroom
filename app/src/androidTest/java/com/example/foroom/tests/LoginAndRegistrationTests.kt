package com.example.foroom.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.foroom.presentation.ui.activity.ForoomActivity
import com.example.foroom.presentation.ui.util.datastore.user.ForoomUserDataStore
import com.example.foroom.steps.LoginSteps
import com.example.foroom.steps.RegistrationSteps
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExternalResource
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import org.koin.core.context.GlobalContext

@RunWith(AndroidJUnit4::class)
@LargeTest
class LoginAndRegistrationTests {
    private val loginSteps = LoginSteps()
    private val registrationSteps = RegistrationSteps()
    private val clearSavedSessionRule = object : ExternalResource() {
        override fun before() {
            runBlocking {
                GlobalContext.get().get<ForoomUserDataStore>().clearUserData()
            }
        }
    }
    private val activityScenarioRule = ActivityScenarioRule(ForoomActivity::class.java)
    @get:Rule
    val ruleChain: RuleChain = RuleChain
        .outerRule(clearSavedSessionRule)
        .around(activityScenarioRule)
    @Test
    fun logIn_withValidUserNameAndInvalidPassword_showsPasswordError() {
        loginSteps.verifyLoginScreenIsDisplayed()
        loginSteps.logIn(EXISTING_USER_NAME, WRONG_PASSWORD)
        loginSteps.verifyPasswordError(INCORRECT_PASSWORD_ERROR)
    }

    @Test
    fun logIn_withNonExistentUserNameAndInvalidPassword_showsUserNameAndPasswordErrors() {
        loginSteps.verifyLoginScreenIsDisplayed()
        loginSteps.logIn(uniqueUserName(NON_EXISTENT_USER_PREFIX), WRONG_PASSWORD)
        loginSteps.verifyUserNameError(USER_NAME_NOT_FOUND_ERROR)
        loginSteps.verifyPasswordError(INCORRECT_PASSWORD_ERROR)
    }

    @Test
    fun register_withUniqueUserNameAndValidPassword_opensHomeScreen() {
        val newUserName = uniqueUserName(NEW_USER_PREFIX)

        loginSteps.verifyLoginScreenIsDisplayed()
        loginSteps.openRegistration()
        registrationSteps.verifyRegistrationScreenIsDisplayed()

        registrationSteps.fillRegistrationForm(newUserName, VALID_PASSWORD, VALID_PASSWORD)
        registrationSteps.selectAvatar(SECOND_AVATAR_POSITION)
        registrationSteps.submitRegistration()

        registrationSteps.verifyHomeScreenIsDisplayed()
    }
    private fun uniqueUserName(prefix: String) = "$prefix${System.currentTimeMillis()}"
    companion object {
        private const val EXISTING_USER_NAME = "student"
        private const val WRONG_PASSWORD = "WrongPass123!"
        private const val VALID_PASSWORD = "Test1234!"
        private const val NON_EXISTENT_USER_PREFIX = "ghost_"
        private const val NEW_USER_PREFIX = "user_"
        private const val SECOND_AVATAR_POSITION = 1
        private const val USER_NAME_NOT_FOUND_ERROR = "Username does not exist"
        private const val INCORRECT_PASSWORD_ERROR = "Incorrect password"
    }
}