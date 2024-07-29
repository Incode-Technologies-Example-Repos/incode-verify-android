package com.incode.didi

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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.incode.didi.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var binding: ActivityWebViewBinding
    private var lastWebPermissionRequest: PermissionRequest? = null
    private var lastVisitedUrl: String? = null

    companion object {
        private const val VIDEO_CAPTURE_WEB_PERMISSION = "android.webkit.resource.VIDEO_CAPTURE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

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
                        if (request.resources.contains(VIDEO_CAPTURE_WEB_PERMISSION)) {
                            if (isCameraPermissionGranted()) {
                                Log.d(Constants.TAG,
                                    String.format(
                                        "PERMISSION REQUEST %s GRANTED",
                                        request.origin.toString()
                                    )
                                )
                                request.grant(arrayOf(VIDEO_CAPTURE_WEB_PERMISSION))
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
                            ResultActivity.start(
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

            loadUrl("https://demo.incode.id/?client_id=incodeid_demo505_web&redirect_url=dididemo://home&origin=native")
        }
    }

    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { _ ->
            // Handle Permission granted/rejected for the permissions we care about
            if (lastWebPermissionRequest?.resources?.contains(VIDEO_CAPTURE_WEB_PERMISSION) == true) {
                if (!isCameraPermissionGranted()) {
                    lastWebPermissionRequest?.deny() // TODO: The screen in the web flow for this case doesn't work in the context of a WebView.
                } else {
                    lastWebPermissionRequest?.grant(arrayOf(VIDEO_CAPTURE_WEB_PERMISSION))
                }
            }
        }

    private fun isCameraPermissionGranted() = ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED

    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}