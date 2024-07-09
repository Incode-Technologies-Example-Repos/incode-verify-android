package com.incode.didi

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.content.Intent.CATEGORY_BROWSABLE
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.incode.didi.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding

    @SuppressLint("WrongConstant") // FLAG_ACTIVITY_REQUIRE_NON_BROWSER on API Level 30
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // TODO: Route to webview or instant app depending on menu setting vs separate buttons
        binding.btnVerifyWebView.setOnClickListener {
            startActivity(Intent(this, WebViewActivity::class.java))
        }

        binding.btnVerifyInstantApp.setOnClickListener {
            val url = "https://app.incode.com/?url=https%3A%2F%2Fdemo.incode.id%2F%3Fclient_id%3Dincodeid_demo505_web%26origin%3Dnative"
            try {
                val intent = Intent(ACTION_VIEW,
                    Uri.parse(url)
                ).apply {
                    // The URL should either launch directly in a non-browser app (if it's
                    // the default), or in the disambiguation dialog.
                    addCategory(CATEGORY_BROWSABLE)
                    flags = FLAG_ACTIVITY_NEW_TASK or 0x00000400
                }
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Log.e(Constants.TAG, "Couldn't find an activity to open URL: $url")
            }
        }
    }
}