package com.incode.verify.demo

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.webkit.ConsoleMessage
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.incode.verify.demo.databinding.ActivityWebViewBinding
import com.incode.verify.demo.extensions.applySystemBarPadding

class WebViewActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var binding: ActivityWebViewBinding
    private var lastWebPermissionRequest: PermissionRequest? = null
    private var lastVisitedUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applySystemBarPadding(R.id.main)

        webView = findViewById(R.id.webview)

        with(webView) {
            @SuppressLint("SetJavaScriptEnabled") // Required for running Incode Verify in a WebView.
            settings.javaScriptEnabled = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            settings.domStorageEnabled = true
            webChromeClient = object : WebChromeClient() {
                override fun onPermissionRequest(request: PermissionRequest) {
                    lastWebPermissionRequest = request
                    runOnUiThread {
                        // Only cover certain permission requests
                        if (request.resources.contains(PermissionRequest.RESOURCE_VIDEO_CAPTURE)) {
                            if (isCameraPermissionGranted()) {
                                Log.d(
                                    Constants.TAG,
                                    String.format(
                                        "PERMISSION REQUEST %s GRANTED",
                                        request.origin.toString()
                                    )
                                )
                                request.grant(arrayOf(PermissionRequest.RESOURCE_VIDEO_CAPTURE))
                            } else {
                                activityResultLauncher.launch(Manifest.permission.CAMERA)
                            }
                        }
                    }
                }

                override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                    consoleMessage?.message()?.let { Log.d(Constants.TAG, it) }
                    return true
                }
            }

            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView,
                    request: WebResourceRequest
                ): Boolean {
                    val urlAsString = request.url.toString()
                    if (urlAsString.startsWith("mailto:")) {
                        // Intercept `mailto` events to open an email client.
                        startActivity(Intent(Intent.ACTION_SENDTO, request.url))
                        return true
                    }
                    return false
                }

                // The host application is notified to update its visited links database anytime the URL loaded changes.
                // This can be used to listen for URL changes corresponding to certain events in the WebView.
                override fun doUpdateVisitedHistory(view: WebView, url: String, isReload: Boolean) {
                    Log.d(Constants.TAG, "doUpdateVisitedHistory(): $url")

                    // Only intercept unique calls.
                    if (url.contentEquals(lastVisitedUrl)) {
                        return
                    }
                    lastVisitedUrl = url

                    // Intercept changes if our verification finished
                    if (url.contains("/verification-result?")) {
                        // Redirect back to this app once onboarding attempt is finished
                        runOnUiThread {
                            MainActivity.launch(
                                this@WebViewActivity,
                                url.toUri().getQueryParameter("token") != null
                            )
                            finish()
                        }
                    }
                    super.doUpdateVisitedHistory(view, url, isReload)
                }

                override fun onLoadResource(view: WebView, url: String) {
                    Log.d(Constants.TAG, "onLoadResource(): $url")
                }

                override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                    Log.d(Constants.TAG, "onPageFinished: $url")
                }

                override fun onPageFinished(view: WebView, url: String) {
                    Log.d(Constants.TAG, "onPageFinished(): $url")
                }
            }

            loadUrl(Constants.DEMO_URL)
        }
    }

    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { _ ->
            // Handle Permission granted/rejected for the permissions we care about
            if (lastWebPermissionRequest?.resources?.contains(PermissionRequest.RESOURCE_VIDEO_CAPTURE) == true) {
                if (!isCameraPermissionGranted()) {
                    lastWebPermissionRequest?.deny()
                } else {
                    lastWebPermissionRequest?.grant(arrayOf(PermissionRequest.RESOURCE_VIDEO_CAPTURE))
                }
            }
        }

    private fun isCameraPermissionGranted() = ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED

    @Suppress("OVERRIDE_DEPRECATION")
    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            @Suppress("DEPRECATION")
            super.onBackPressed()
        }
    }
}