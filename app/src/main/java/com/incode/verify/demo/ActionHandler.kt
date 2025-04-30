package com.incode.verify.demo

interface ActionHandler {
    fun openInstantApp()
    fun openWebView()
    fun openHome()
    fun openHelp()
    fun openTerms()
    fun openChromeCustomTabs()
}

class ActionHandlerAdapter : ActionHandler {
    override fun openInstantApp() = Unit
    override fun openWebView() = Unit
    override fun openHome() = Unit
    override fun openHelp() = Unit
    override fun openTerms() = Unit
    override fun openChromeCustomTabs() = Unit
}
