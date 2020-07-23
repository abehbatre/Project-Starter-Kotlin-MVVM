@file:Suppress("KDocUnresolvedReference", "unused")

package id.web.adit.core.utils.ext

import android.app.Activity
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Handler
import android.provider.Settings
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.annotation.*
import androidx.annotation.IntRange
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import coil.api.load
import com.airbnb.lottie.LottieDrawable
import es.dmoral.toasty.Toasty
import id.web.adit.core.R
import id.web.adit.core.utils.MaterialColor
import kotlinx.android.synthetic.main.dialog_custom.*
import smartdevelop.ir.eram.showcaseviewlib.GuideView
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType

/** [copyright]
 *
 * @author  Aditya Pratama
 * @since   2019
 *
 * @receiver Context
 */


fun runWithDelay(delayMilis: Long = 0, block: () -> Unit) {
    Handler().postDelayed({ block.invoke() }, delayMilis)
}


/** [openActivity]
 *
 * @param T  target Activity
 * @sample openActivity<SecondActivity>
 *
 * */
inline fun <reified T : Activity> Context.openActivity(block: Intent.() -> Unit = {}) {
    val intent = Intent(this, T::class.java)
    block(intent)
    startActivity(intent)
}
fun Context.openPlayStoreLink(@StringRes packageIdRes: Int) = openPlayStoreLink(getString(packageIdRes))
fun Context.openPlayStoreLink(packageId: String) {
    val intent = Intent(ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageId"))
    when {
        intent.resolveActivity(packageManager) != null -> startActivity(intent)
        else -> toast("Cannot resolve play store")
    }
}
fun Context.openAppInfo(packageName: String) {
    try {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:$packageName")
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
        // Open the generic Apps page:
        val intent = Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS)
        startActivity(intent)
    }
}

/** [ResourceRetrievers]
 *
 * @param   id    resource target
 */
const val INVALID_ID = 0
fun Context.string(@StringRes id: Int): String = getString(id)
fun Context.string(@StringRes id: Int, fallback: String?): String? = if (id != INVALID_ID) string(id) else fallback
inline fun Context.string(@StringRes id: Int, fallback: () -> String?): String? = if (id != INVALID_ID) string(id) else fallback()
fun Context.color(@ColorRes id: Int): Int = ContextCompat.getColor(this, id)
fun Context.boolean(@BoolRes id: Int): Boolean = resources.getBoolean(id)
fun Context.integer(@IntegerRes id: Int): Int = resources.getInteger(id)
fun Context.dimen(@DimenRes id: Int): Float = resources.getDimension(id)
fun Context.dimenPixelSize(@DimenRes id: Int): Int = resources.getDimensionPixelSize(id)
fun Context.drawable(@DrawableRes id: Int): Drawable = ContextCompat.getDrawable(this, id) ?: throw Throwable("Drawable with id $id not found")
fun Context.drawable(@DrawableRes id: Int, fallback: Drawable?): Drawable? = if (id != INVALID_ID) drawable(id) else fallback
inline fun Context.drawable(@DrawableRes id: Int, fallback: () -> Drawable?): Drawable? = if (id != INVALID_ID) drawable(id) else fallback()
fun Context.interpolator(@InterpolatorRes id: Int) = AnimationUtils.loadInterpolator(this, id)!!
fun Context.animation(@AnimRes id: Int) = AnimationUtils.loadAnimation(this, id)!!
fun Context.plural(@PluralsRes id: Int, quantity: Number) = resources.getQuantityString(id, quantity.toInt(), quantity.toInt())


object ToastFlag {
    const val SUCCESS = 0
    const val INFO = 1
    const val WARNING = 2
    const val ERROR = 3
}
/** [Toasty]
 *
 * @param message       isi pesan
 * @param level         level menentukan warna dari toast (0-3)
 * @param duration      durasi toast (0-1)
 */
fun Context.toast(
    message: CharSequence,
    @IntRange(from = 0L, to = 3L) level: Int = 1,
    @IntRange(from = 0L, to = 1L) duration: Int = Toasty.LENGTH_LONG,
    log: Boolean = true
) {
    if (log) logD(message.toString())

    return when (level) {
        0 -> Toasty.success(this, message, duration).show()
        1 -> Toasty.info(this, message, duration).show()
        2 -> Toasty.warning(this, message, duration).show()
        3 -> Toasty.error(this, message, duration).show()
        else -> Toasty.info(this, message, duration).show()
    }
}


/** [CustomDialog]
 *
 * @param title                 judul pesan
 * @param message               isi pesan
 * @param lottieFile            resource lottie yang akan digunakan
 * @param isDismissable         dismiss jika di sentuh diluar context area dialog
 * @param lottieRepeat          mau direpeat berapa kali? default = -1
 * @param positiveButtonClick   panggilan balik aksi ketika di klik btnYes
 * */
fun Context.customDialog(
    title: String = "",
    message: String = "",

    icon : Int? = null,
    isDismissable: Boolean = true,

    lottieFile: Int? = null,
    lottieRepeat: Int = LottieDrawable.INFINITE,
    imageBackground: Int? = null,

    positiveButtonText: String= "",
    negativeButtonText: String= "",
    neutralButtonText: String= "",
    positiveButtonClick: () -> Unit? = { },
    negativeButtonClick: () -> Unit? = { },
    neutralButtonClick: () -> Unit? = { },

    cornerRadius : Float = 10f
) {
    Dialog(this).apply {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_custom)

        // THEME CONFIG
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(window?.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT



        // —————————————————————————————————————————————————————————————————
        //  setupComponent
        // —————————————————————————————————————————————————————————————————*/
        setCancelable(isDismissable)
        setCanceledOnTouchOutside(isDismissable)
        // RADIUS
        lyt_root.radius = cornerRadius

        // LOTTIE
        lottieAnimationView.apply {
            lottieFile?.let {
                setAnimation(it)
                lottieAnimationView.visible()
                playAnimation()
                repeatCount = lottieRepeat
            }
        }

        // IMAGE BACKGROUND
        imageBackground?.let {
            if (lottieFile == null) {
                ivBackgound.visible()
                ivBackgound.load(it)
            }
        }

        // TITLE & MESSAGE
        tvTitle.htmlFormat(title)
        tvMessage.htmlFormat(message)

        // ICON
        icon?.let {
            ivIcon.visible()
            ivIcon.imageDrawable(icon)
        }

        // BUTTON NEGATIVE
        if (negativeButtonText.isNotBlank()) {
            btnNo.visible()
            btnNo.text = negativeButtonText
            btnNo.click {
                negativeButtonClick.invoke()
                dismiss()
            }
        }

        // BUTTON POSITIVE
        if (positiveButtonText.isNotBlank()) {
            btnYes.visible()
            btnYes.text = positiveButtonText
            btnYes.click {
                positiveButtonClick.invoke()
                dismiss()
            }
        }

        // BUTTON NEUTRAL
        if (positiveButtonText.isNotBlank()) {
            btnNeutral.visible()
            btnNeutral.text = neutralButtonText
            btnNeutral.click {
                neutralButtonClick.invoke()
                dismiss()
            }
        }

        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = WindowManager.LayoutParams.WRAP_CONTENT
        window!!.setLayout(width, height)
        window!!.setGravity(Gravity.CENTER)
    }.also {
        it.show()
    }
}

fun Context.createDialogManual(layoutRes: Int, invoke: Dialog.() -> Unit): Dialog {
    val d = Dialog(this).apply {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(layoutRes)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val lp = WindowManager.LayoutParams()
        lp.copyFrom(window?.attributes)
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        window!!.attributes = lp

    }
    invoke.invoke(d)
    return d
}





/** [GuideView]
 *
 * @param view      targetView
 * @param title     judul pesan
 * @param desc      isi pesan
 *
 * */
fun Context.guide(
    view: View,
    title: String,
    desc: String = "",
    block : () -> Unit = { }
) {
    GuideView.Builder(this)
        .setTitle(title)
        .setContentText(desc)
        .setDismissType(DismissType.outside)
        .setTargetView(view)
        .setGuideListener { block.invoke() }
        .build()
        .show()
}

fun Context.openInBrowser(url: String) {
    try {
        val uri = Uri.parse(url)
        val intent = CustomTabsIntent.Builder()
            .setToolbarColor(MaterialColor.WHITE)
            .build()
        intent.launchUrl(this, uri)
    } catch (e: Exception) { toast(e.toString()) }
}


fun Context.dial(tel: String?) = startActivity(Intent(ACTION_DIAL, Uri.parse("tel:$tel")))

fun Context.sms(phone: String?, body: String = "") {
    val smsToUri = Uri.parse("smsto:$phone")
    val intent = Intent(ACTION_SENDTO, smsToUri)
    intent.putExtra("sms_body", body)
    startActivity(intent)
}

fun Context.shareText(text: String?) {
    text ?: return toast("Share text is null")
    val intent = Intent(ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(EXTRA_TEXT, text)
    val chooserIntent = createChooser(intent, "Share Via")
    if (chooserIntent.resolveActivity(packageManager) != null) {
        startActivity(chooserIntent)
    } else {
        toast("Cannot resolve activity to share text", log = true)
    }
}

/**
 * Check if given context is finishing.
 * This is a wrapper to check if it's both an activity and finishing
 * As of now, it is only checked when tied to an activity
 */
inline val Context.isFinishing: Boolean
    get() = (this as? Activity)?.isFinishing ?: false
