package com.motionweb.halal.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import android.text.SpannableString
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

inline fun <reified T : Activity> Context.startActivityExt(noinline extra: Intent.() -> Unit = {}) {
    val intent = Intent(this, T::class.java)
    intent.extra()
    startActivity(intent)
}

inline val Fragment.parentActivity get() = (activity as AppCompatActivity)

fun Context.getFormattedTime(
    passedSeconds: Int,
): SpannableString {
    val hours = (passedSeconds / 3600) % 24
    val minutes = (passedSeconds / 60) % 60
    val seconds = passedSeconds % 60

    val formattedTime = formatTime(hours, minutes, seconds)
    return SpannableString(formattedTime)
}

fun formatTime(hours: Int, minutes: Int, seconds: Int): String {
    val hoursFormat = "%02d"
    var format = "$hoursFormat:%02d"

    format += ":%02d"
    return String.format(format, hours, minutes, seconds)
}

// e.g 12:00:00 to 12:00
fun String.removeLastSeconds(): String = this.substring(IntRange(0, this.length - 4))

fun getCurrentDate(): String? {
    val date = SimpleDateFormat("EEE, d MMM ''yy", Locale.getDefault())
    return date.format(Date())
}

fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}

fun Context.toast(@StringRes stringRes: Int, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, stringRes, length).show()
}

fun Context.centeredToast(@StringRes stringRes: Int, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, stringRes, length).also {
        it.setGravity(Gravity.CENTER, 0, 0)
    }.show()
}

fun Context.centeredToast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).also {
        it.setGravity(Gravity.CENTER, 0, 0)
    }.show()
}

fun Context.getAssetsFilePath(fileName: String): String {
    return File(this.cacheDir, fileName).also {
        if (!it.exists()) {
            it.outputStream().use { cache ->
                this.assets.open(fileName).use { inputStream ->
                    inputStream.copyTo(cache)
                }
            }
        }
    }.absolutePath
}

fun AppCompatActivity.getPath(contentUri: Uri?): String? {
    var res: String? = null
    val proj = arrayOf(MediaStore.Images.Media.DATA)
    val cursor: Cursor? =
        contentUri?.let { contentResolver.query(it, proj, null, null, null) }
    if (cursor?.moveToFirst() == true) {
        val columnIndex: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        res = cursor.getString(columnIndex)
    }
    cursor?.close()
    return res
}

fun Context.openSettingsActivityIntent(): Intent {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri = Uri.fromParts("package", packageName, null)
    intent.data = uri
    return intent
}

fun View.snackbarWithoutAction(text: String, length: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, text, length).show()
}

fun View.snackbarWithoutAction(@StringRes text: Int, length: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, text, length).show()
}

fun View.snackbarExt(
    @StringRes text: Int,
    @StringRes message: Int,
    action: (view: View) -> Unit = {}
) {
    Snackbar.make(this, text, Snackbar.LENGTH_LONG)
        .setAction(message) {
            action(it)
        }
        .show()
}

fun View.snackbarExt(text: String, message: String, action: (view: View) -> Unit = {}) {
    Snackbar.make(this, text, Snackbar.LENGTH_LONG)
        .setAction(message) {
            action(it)
        }
        .show()
}

inline fun <reified T> SharedPreferences.get(key: String, defaultValue: T): T {
    when (T::class) {
        Boolean::class -> return this.getBoolean(key, defaultValue as Boolean) as T
        Float::class -> return this.getFloat(key, defaultValue as Float) as T
        Int::class -> return this.getInt(key, defaultValue as Int) as T
        Long::class -> return this.getLong(key, defaultValue as Long) as T
        String::class -> return this.getString(key, defaultValue as String) as T
        else -> {
            if (defaultValue is Set<*>) {
                return this.getStringSet(key, defaultValue as Set<String>) as T
            }
        }
    }

    return defaultValue
}

inline fun <reified T> SharedPreferences.put(key: String, value: T) {
    val editor = this.edit()

    when (T::class) {
        Boolean::class -> editor.putBoolean(key, value as Boolean)
        Float::class -> editor.putFloat(key, value as Float)
        Int::class -> editor.putInt(key, value as Int)
        Long::class -> editor.putLong(key, value as Long)
        String::class -> editor.putString(key, value as String)
        else -> {
            if (value is Set<*>) {
                editor.putStringSet(key, value as Set<String>)
            }
        }
    }

    editor.commit()
}

fun Context.getColorExt(@ColorRes color: Int) =
    ContextCompat.getColor(this, color)