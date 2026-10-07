package com.example.foroom.steps

import com.example.foroom.pages.ChatsPage
import com.example.foroom.pages.CreateChatPage
import com.example.foroom.pages.HomePage

class ChatSteps {
    private val homePage = HomePage()
    private val createChatPage = CreateChatPage()
    private val chatsPage = ChatsPage()

    fun openCreateChat() {
        homePage.openCreateChat()
        createChatPage.waitUntilDisplayed()
    }

    fun createChat(name: String, imageIndex: Int = 1) {
        createChatPage.enterChatName(name)
        createChatPage.selectImage(imageIndex)
        createChatPage.tapCreateChat()
    }

    fun verifyCreatedChatOpened(name: String) {
        createChatPage.verifyOpenedChatTitle(name)
    }

    fun closeChat() {
        createChatPage.tapClose()
        chatsPage.waitUntilDisplayed()
    }

    fun searchAndVerifyChat(name: String) {
        chatsPage.search(name)
        chatsPage.verifyChatCardDisplayed(name)
    }
}
