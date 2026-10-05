package com.example.foroom.pages

import androidx.test.espresso.matcher.ViewMatchers.withId
import com.alternator.foroom.R
import com.example.design_system.R as DsR

/** Change-password bottom sheet. */
class ChangePasswordPage : BasePage() {
    private val newPasswordField = inputFieldOf(R.id.passwordInput)
    private val repeatPasswordField = inputFieldOf(R.id.repeatPasswordInput)
    private val confirmButton = withId(DsR.id.actionButton)

    fun waitUntilDisplayed() = waitForDisplayed(repeatPasswordField)

    fun enterNewPassword(password: String) = typeInto(newPasswordField, password)

    fun enterRepeatPassword(password: String) = typeInto(repeatPasswordField, password)

    fun tapConfirm() = tap(confirmButton)
}
