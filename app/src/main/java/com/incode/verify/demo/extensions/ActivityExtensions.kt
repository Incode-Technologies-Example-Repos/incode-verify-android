package com.incode.verify.demo.extensions

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.content.Intent.CATEGORY_BROWSABLE
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.util.Log
import androidx.annotation.IdRes
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.incode.verify.demo.Constants

fun Activity.openUrl(url: String, isInstantAppLink: Boolean = false): Boolean {
    try {
        startActivity(
            Intent(
                ACTION_VIEW,
                Uri.parse(url)
            ).apply {
                // Adding the flag so that URL is handled in the instant app through PLay store
                // or directly if app was already loaded.
                if (isInstantAppLink) {
                    addCategory(CATEGORY_BROWSABLE)
                    flags = FLAG_ACTIVITY_NEW_TASK or 0x00000800
                }
            })
        return true
    } catch (e: ActivityNotFoundException) {
        Log.e(Constants.TAG, "Couldn't find an activity to open URL: url")
        return false
    }
}

fun Activity.applySystemBarPadding(@IdRes mainView: Int) {
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(mainView)) { v, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
        insets
    }
}
