package com.example.foroom.steps

import com.example.foroom.pages.ChangeLanguagePage
import com.example.foroom.pages.ChangePasswordPage
import com.example.foroom.pages.HomePage
import com.example.foroom.pages.ProfilePage

class ProfileSteps {
    private val homePage = HomePage()
    private val profilePage = ProfilePage()
    private val changePasswordPage = ChangePasswordPage()
    private val changeLanguagePage = ChangeLanguagePage()

    fun openProfile() {
        homePage.openProfile()
        profilePage.waitUntilDisplayed()
    }

    fun changePassword(newPassword: String) {
        profilePage.tapChangePassword()
        changePasswordPage.waitUntilDisplayed()
        changePasswordPage.enterNewPassword(newPassword)
        changePasswordPage.enterRepeatPassword(newPassword)
        changePasswordPage.tapConfirm()
    }

    /** Selecting a language recreates the activity and reopens the profile tab. */
    fun selectGeorgian() {
        profilePage.tapChangeLanguage()
        changeLanguagePage.waitUntilDisplayed()
        changeLanguagePage.selectGeorgian()
    }

    fun selectEnglish() {
        profilePage.tapChangeLanguage()
        changeLanguagePage.waitUntilDisplayed()
        changeLanguagePage.selectEnglish()
    }

    fun verifyGeorgianProfileLabels() {
        profilePage.verifyChangeLanguageLabel(GEO_CHANGE_LANGUAGE)
        profilePage.verifySignOutLabel(GEO_SIGN_OUT)
    }

    fun verifyEnglishProfileLabels() {
        profilePage.verifyChangeLanguageLabel(ENG_CHANGE_LANGUAGE)
        profilePage.verifySignOutLabel(ENG_SIGN_OUT)
    }

    companion object {
        const val GEO_CHANGE_LANGUAGE = "ენის შეცვლა"
        const val GEO_SIGN_OUT = "გამოსვლა"
        const val ENG_CHANGE_LANGUAGE = "Change Language"
        const val ENG_SIGN_OUT = "Sign Out"
    }
}
