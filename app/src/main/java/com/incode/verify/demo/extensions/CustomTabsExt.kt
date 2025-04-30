package com.incode.verify.demo.extensions

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.ColorInt
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsService

/**
 * Opens [url] in a Chrome Custom Tab if possible, falling back to ACTION_VIEW.
 */
fun Context.openInCustomTab(
    url: String,
    @ColorInt toolbarColor: Int,
    showTitle: Boolean = true
) {
    val intent = CustomTabsIntent.Builder()
        .setShowTitle(showTitle)
        .setShareState(CustomTabsIntent.SHARE_STATE_ON)
        .setDefaultColorSchemeParams(
            CustomTabColorSchemeParams.Builder()
                .setToolbarColor(toolbarColor)
                .build()
        )
        .build()
        .apply { this.intent.setPackage(findCustomTabsBrowserPackage()) }

    try {
        intent.launchUrl(this, Uri.parse(url))
    } catch (e: ActivityNotFoundException) {
        // No Custom-Tabs browser → normal VIEW intent
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
}

/**
 * Returns the package name of an installed browser that supports Custom Tabs,
 * prioritising regular Chrome if available.
 */
private fun Context.findCustomTabsBrowserPackage(): String? {
    val candidates = packageManager.queryIntentActivities(
        Intent(Intent.ACTION_VIEW, Uri.parse("http://")), 0
    )

    val supporting = candidates.mapNotNull { info ->
        val svcIntent = Intent(CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION)
            .setPackage(info.activityInfo.packageName)
        if (packageManager.resolveService(svcIntent, 0) != null)
            info.activityInfo.packageName
        else null
    }

    // Prefer Chrome, else the first supporting package, else null (let Android decide)
    return when {
        "com.android.chrome" in supporting      -> "com.android.chrome"
        supporting.isNotEmpty()                 -> supporting.first()
        else                                    -> null   // let Android choose
    }
}
