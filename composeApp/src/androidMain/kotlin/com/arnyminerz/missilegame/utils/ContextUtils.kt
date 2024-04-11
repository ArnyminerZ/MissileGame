package com.arnyminerz.missilegame.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.IntDef
import androidx.annotation.StringRes

@IntDef(Toast.LENGTH_SHORT, Toast.LENGTH_LONG)
annotation class ToastDuration

/**
 * Shows a toast with the given text resource and duration.
 * @param textRes The text resource to show in the toast.
 * @param duration The duration of the toast. Either [Toast.LENGTH_SHORT] or [Toast.LENGTH_LONG].
 * @see Toast.makeText
 * @return The toast that was shown.
 */
fun Context.toast(
    @StringRes textRes: Int,
    @ToastDuration duration: Int = Toast.LENGTH_SHORT
): Toast = Toast.makeText(this, textRes, duration).also { it.show() }
