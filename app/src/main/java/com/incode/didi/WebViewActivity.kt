package com.incode.didi

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.incode.didi.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var binding: ActivityWebViewBinding
    private var lastWebPermissionRequest: PermissionRequest? = null

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
                        if ("android.webkit.resource.VIDEO_CAPTURE" == request.resources[0]) {
                            if (isCameraPermissionGranted()) {
                                Log.d(Constants.TAG,
                                    String.format(
                                        "PERMISSION REQUEST %s GRANTED",
                                        request.origin.toString()
                                    )
                                )
                                request.grant(request.resources)
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
                    return true // We always handle the url loading, one way or another
                }

                // The host application is notified to update its visited links database anytime the URL loaded changes.
                // This can be used to listen for URL changes corresponding to certain events in the WebView.
                override fun doUpdateVisitedHistory(view: WebView, url: String, isReload: Boolean) {
                    Log.d(Constants.TAG, "doUpdateVisitedHistory(): $url")

                    if (url.contains("/verification-result?")) {
                        // Redirect back to this app once onboarding attempt is finished
                        val urlAsUri = Uri.parse(url)
                        val message = if (urlAsUri.getQueryParameter("error") != null) {
                            // Bad things happened. Route to the error screen
                            "Identity verification failed"
                        } else {
                            // The query was successful. Route to the success screen
                            "Identity successfully verified"
                        }
                        runOnUiThread {
                            ResultActivity.start(this@WebViewActivity, message)
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

            // Hook in binding b/w the JS and native code to pass data from the webview back.
//            addJavascriptInterface(JavascriptInterface(this@MainActivity, this), "NativeJSInterface")

            loadUrl("https://demo.incode.id/?client_id=incodeid_demo505_web")
        }
    }

    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { _ ->
            // Handle Permission granted/rejected
            if (!isCameraPermissionGranted()) {
//                TODO: showCameraPermissionsMandatory()
            } else {
                lastWebPermissionRequest?.grant(lastWebPermissionRequest?.resources)
            }
        }

    private fun isCameraPermissionGranted() = ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED
}