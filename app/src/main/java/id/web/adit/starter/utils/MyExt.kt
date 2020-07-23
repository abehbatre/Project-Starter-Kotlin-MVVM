package id.web.adit.starter.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Build
import android.os.Parcelable
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Button
import android.widget.TextView
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import id.web.adit.core.utils.ext.createDialogManual
import id.web.adit.core.utils.ext.runWithDelay
import id.web.adit.core.utils.ext.second
import id.web.adit.core.utils.ext.visible
import id.web.adit.starter.R
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*


fun Any.wtF(aditSay: String) = Timber.e("aditSay : $aditSay")
fun Any.emptyString() = ""


inline fun <reified T : Parcelable> Activity.getParcelableExtra(key: String) = lazy {
    intent.getParcelableExtra<T>(key)
}


fun Context.vibratePhone(ms: Long = 300) {
    val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT >= 26) {
        vibrator.vibrate(VibrationEffect.createOneShot(ms, VibrationEffect.DEFAULT_AMPLITUDE))
    } else vibrator.vibrate(ms)
}

fun Context.isDarkThemeOn(): Boolean{
    return resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK == UI_MODE_NIGHT_YES
}

fun Activity.dexterPermission(
    permissions: List<String>,
    allPermitInvoke: () -> Unit
) {
    Dexter.withActivity(this)
        .withPermissions(permissions)
        .withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                if (report.areAllPermissionsGranted()) {
                    allPermitInvoke.invoke()
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: MutableList<PermissionRequest>?,
                token: PermissionToken?
            ) {
                wtF("Ups Need Permission ...")
                token?.continuePermissionRequest()
            }
        })
        .withErrorListener {
            wtF("Dexter Error #${it.name}")
        }
        .check()
}


fun Context.showLoading(
    message: String? = null,
    dismissable : Boolean = true,
    timeOut: Long = 10.second()
): Dialog {
    return createDialogManual(R.layout.dialog_custom_loading) {
        setCancelable(dismissable)
        setCanceledOnTouchOutside(dismissable)

        // init
        val tvLoadingText = findViewById<TextView>(R.id.tvLoadingText)
        val btnLoading = findViewById<Button>(R.id.btnLoading)

        message?.let {
            tvLoadingText.visible()
            tvLoadingText.text = message
        }
        runWithDelay(timeOut) { btnLoading.visible() }
    }
}



