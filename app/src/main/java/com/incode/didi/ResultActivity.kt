package com.incode.didi

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.incode.didi.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set the success/failure message based on how we got here
        val deepLinkUri = intent.data
        if (deepLinkUri != null) {
            // The onboarding/login was done externally.
            val text = if (deepLinkUri.host == "success") {
                "Identity successfully verified"
            } else {
                "Identity verification failed"
            }
            binding.tvMessage.text = text
        } else {
            // We came in through the app itself. Set the text based on what was provided.
            binding.tvMessage.text = intent.getStringExtra(EXTRA_MESSAGE)
        }
    }

    companion object {
        const val EXTRA_MESSAGE = "extra_message"

        @JvmStatic
        fun start(context: Context, message: String) {
            val intent = Intent(context, ResultActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, message)
            }
            context.startActivity(intent)
        }
    }
}