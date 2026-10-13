package com.example.foroom.pages

import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R

class ChangeLanguagePage : BasePage() {
    private val georgianButton = withId(R.id.languageButtonGeo)
    private val englishButton = withId(R.id.languageButtonEng)

    fun waitUntilDisplayed() = waitForDisplayed(georgianButton)

    fun selectGeorgian() = tap(georgianButton)

    fun selectEnglish() = tap(englishButton)
}
