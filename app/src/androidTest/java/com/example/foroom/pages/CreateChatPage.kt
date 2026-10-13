package com.example.foroom.pages

import android.view.View
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.util.TreeIterables
import com.alternator.foroom.R
import com.example.design_system.components.image_chooser.ImageChooserItemView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import com.example.design_system.R as DsR

class CreateChatPage : BasePage() {
    private val chatNameField = inputFieldOf(R.id.chatNameInput)
    private val createChatButton = withId(R.id.createChatButton)
    private val closeButton = withId(R.id.closeButton)

    fun waitUntilDisplayed() = waitForDisplayed(createChatButton)

    fun enterChatName(name: String) = typeInto(chatNameField, name)

    fun selectImage(index: Int = 1) = tap(imageChooserItemAt(index))

    fun tapCreateChat() = tap(createChatButton)

    fun verifyOpenedChatTitle(name: String) {
        waitForDisplayed(
            allOf(
                withId(DsR.id.chatNameTextView),
                isDescendantOfA(withId(R.id.chatHeaderView)),
                withText(name)
            ),
            LONG_TIMEOUT_MS
        )
    }

    fun tapClose() = tap(closeButton)
    private fun imageChooserItemAt(index: Int): Matcher<View> = object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("image chooser item at index $index")
        }

        override fun matchesSafely(view: View): Boolean {
            if (view !is ImageChooserItemView) return false
            val chooser = generateSequence(view.parent) { it.parent }
                .filterIsInstance<View>()
                .firstOrNull { it.id == R.id.chatImageChooser } ?: return false
            val items = TreeIterables.breadthFirstViewTraversal(chooser)
                .filterIsInstance<ImageChooserItemView>()
            return items.indexOf(view) == index
        }
    }
}
