package com.incode.verify.demo

import android.content.Context
import android.content.Intent
import android.content.Intent.CATEGORY_LAUNCHER
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import com.incode.verify.demo.extensions.openInCustomTab
import com.incode.verify.demo.extensions.openUrl
import com.incode.verify.demo.ui.Home
import com.incode.verify.demo.ui.ResultApproved
import com.incode.verify.demo.ui.ResultFailed
import com.incode.verify.demo.ui.theme.IncodeColors
import com.incode.verify.demo.ui.theme.IncodeVerifyAndroidTheme
import java.net.URLEncoder

class MainActivity : ComponentActivity(), ActionHandler {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IncodeVerifyAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (containsResult()) {
                        if (isApproved()) {
                            ResultApproved(innerPadding = innerPadding, actionHandler = this)
                        } else {
                            ResultFailed(innerPadding = innerPadding, actionHandler = this)
                        }
                    } else {
                        Home(
                            innerPadding = innerPadding,
                            actionHandler = this,
                            showIntroButton = intent.hasCategory(CATEGORY_LAUNCHER)
                        )
                    }
                }
            }
        }
    }

    private fun containsResult(): Boolean {
        Log.d(Constants.TAG, "result: ${intent.data?.host}, ${intent.data} ")
        return intent.data?.host?.let { host ->
            host == Constants.PARAM_SUCCESS || host == Constants.PARAM_ERROR
        } ?: intent.hasExtra(EXTRA_SUCCESS)
    }

    private fun isApproved() =
        intent.data?.host == "success" || intent.getBooleanExtra(EXTRA_SUCCESS, false)

    override fun openInstantApp() {
        val encodedDemoUrl = URLEncoder.encode(Constants.DEMO_URL, Charsets.UTF_8.name())
        val success = openUrl(
            url = "${Constants.INCODE_URL}$encodedDemoUrl",
            isInstantAppLink = true
        )
        if (success) {
            finish()
        }
    }

    override fun openWebView() {
        startActivity(Intent(this, WebViewActivity::class.java))
        finish()
    }

    override fun openHome() {
        launch(this)
        finish()
    }

    override fun openHelp() {
        openUrl(Constants.HELP_URL)
    }

    override fun openTerms() {
        openUrl(Constants.TERMS_URL)
    }

    override fun openChromeCustomTabs() {
        openInCustomTab(
            url = Constants.DEMO_URL_NO_REDIRECT,
            toolbarColor = IncodeColors.Purple.toArgb()
        )
    }

    companion object {

        private const val EXTRA_SUCCESS = "extraSuccess"

        fun launch(context: Context, success: Boolean? = null) {
            context.startActivity(Intent(context, MainActivity::class.java).apply {
                success?.let {
                    putExtra(EXTRA_SUCCESS, success)
                }
            })
        }
    }
}
