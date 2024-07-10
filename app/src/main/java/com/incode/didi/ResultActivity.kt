package com.incode.didi

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
            if (deepLinkUri.host == "success") {
                setSuccessUI()
            } else {
                setErrorUI()
            }
        } else {
            // We came in through the app itself. Set the UI based on the state.
            val success = intent.getBooleanExtra(EXTRA_SUCCESS, false)
            if (success) {
                setSuccessUI()
            } else {
                setErrorUI()
            }
        }
    }

    private fun setSuccessUI() {
        binding.ivIcon.setImageResource(R.drawable.ic_success)
        binding.tvMessage.text = "Identity successfully verified"
    }

    private fun setErrorUI() {
        binding.ivIcon.setImageResource(R.drawable.ic_error)
        binding.tvMessage.text = "Identity verification failed"
    }

    companion object {
        const val EXTRA_SUCCESS = "extra_success"

        @JvmStatic
        fun start(context: Context, success: Boolean) {
            val intent = Intent(context, ResultActivity::class.java).apply {
                putExtra(EXTRA_SUCCESS, success)
            }
            context.startActivity(intent)
        }
    }
}