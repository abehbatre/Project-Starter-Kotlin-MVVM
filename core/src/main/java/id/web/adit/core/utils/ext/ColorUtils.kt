@file:Suppress("unused")

package id.web.adit.core.utils.ext


import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.appcompat.widget.Toolbar
import java.util.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.roundToInt

/**
 * Created by Allan Wang on 2017-06-08.
 */

/**
 * Generates a random opaque color
 * Note that this is mainly for testing
 * Should you require this method often, consider
 * rewriting the method and storing the [Random] instance
 * rather than generating one each time
 */
inline val rndColor: Int
    get() {
        val rnd = Random()
        return Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

inline val Int.isColorDark: Boolean
    get() = isColorDark(0.5f)

fun Int.isColorDark(minDarkness: Float): Boolean =
    ((0.299 * Color.red(this) + 0.587 * Color.green(this) + 0.114 * Color.blue(this)) / 255.0) < minDarkness

fun Int.toHexString(withAlpha: Boolean = false, withHexPrefix: Boolean = true): String {
    val hex = if (withAlpha) String.format("#%08X", this)
    else String.format("#%06X", 0xFFFFFF and this)
    return if (withHexPrefix) hex else hex.substring(1)
}

inline val Int.isColorOpaque: Boolean
    get() = Color.alpha(this) == 255

fun FloatArray.toColor(): Int = Color.HSVToColor(this)

fun Int.isColorVisibleOn(
    @ColorInt color: Int,
    @IntRange(from = 0L, to = 255L) delta: Int = 25,
    @IntRange(from = 0L, to = 255L) minAlpha: Int = 50
): Boolean =
    if (Color.alpha(this) < minAlpha) false
    else !(abs(Color.red(this) - Color.red(color)) < delta &&
            abs(Color.green(this) - Color.green(color)) < delta &&
            abs(Color.blue(this) - Color.blue(color)) < delta)

@ColorInt
fun Context.getDisabledColor(): Int {
    val primaryColor = resolveColor(android.R.attr.textColorPrimary)
    val disabledColor = if (primaryColor.isColorDark) Color.BLACK else Color.WHITE
    return disabledColor.adjustAlpha(0.3f)
}

@ColorInt
fun Int.adjustAlpha(factor: Float): Int {
    val alpha = (Color.alpha(this) * factor).roundToInt()
    return Color.argb(alpha, Color.red(this), Color.green(this), Color.blue(this))
}

inline val Int.isColorTransparent: Boolean
    get() = Color.alpha(this) != 255

@ColorInt
fun Int.blendWith(@ColorInt color: Int, @FloatRange(from = 0.0, to = 1.0) ratio: Float): Int {
    val inverseRatio = 1f - ratio
    val a = Color.alpha(this) * inverseRatio + Color.alpha(color) * ratio
    val r = Color.red(this) * inverseRatio + Color.red(color) * ratio
    val g = Color.green(this) * inverseRatio + Color.green(color) * ratio
    val b = Color.blue(this) * inverseRatio + Color.blue(color) * ratio
    return Color.argb(a.toInt(), r.toInt(), g.toInt(), b.toInt())
}

@ColorInt
fun Int.withAlpha(@IntRange(from = 0, to = 0xFF) alpha: Int): Int =
    this and 0x00FFFFFF or (alpha shl 24)

@ColorInt
fun Int.withMinAlpha(@IntRange(from = 0, to = 0xFF) alpha: Int): Int =
    withAlpha(max(alpha, this ushr 24))

@ColorInt
private inline fun Int.colorFactor(rgbFactor: (Int) -> Float): Int {
    val (red, green, blue) = intArrayOf(Color.red(this), Color.green(this), Color.blue(this))
        .map { rgbFactor(it).toInt() }
    return Color.argb(Color.alpha(this), red, green, blue)
}

@ColorInt
fun Int.lighten(@FloatRange(from = 0.0, to = 1.0) factor: Float = 0.1f): Int = colorFactor {
    (it * (1f - factor) + 255f * factor)
}

@ColorInt
fun Int.darken(@FloatRange(from = 0.0, to = 1.0) factor: Float = 0.1f): Int = colorFactor {
    it * (1f - factor)
}

@ColorInt
fun Int.colorToBackground(@FloatRange(from = 0.0, to = 1.0) factor: Float = 0.1f): Int =
    if (isColorDark) darken(factor) else lighten(factor)

@ColorInt
fun Int.colorToForeground(@FloatRange(from = 0.0, to = 1.0) factor: Float = 0.1f): Int =
    if (isColorDark) lighten(factor) else darken(factor)

fun String.toColor(): Int {
    val toParse: String = if (startsWith("#") && length == 4)
        "#${this[1]}${this[1]}${this[2]}${this[2]}${this[3]}${this[3]}"
    else
        this
    return Color.parseColor(toParse)
}

@Suppress("DEPRECATION")
fun ProgressBar.tint(@ColorInt color: Int, skipIndeterminate: Boolean = false) {
    val sl = ColorStateList.valueOf(color)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        progressTintList = sl
        secondaryProgressTintList = sl
        if (!skipIndeterminate) indeterminateTintList = sl
    } else {
        val mode: PorterDuff.Mode = PorterDuff.Mode.SRC_IN
        indeterminateDrawable?.setColorFilter(color, mode)
        progressDrawable?.setColorFilter(color, mode)
    }
}

fun Toolbar.tint(@ColorInt color: Int, tintTitle: Boolean = true) {
    if (tintTitle) {
        setTitleTextColor(color)
        setSubtitleTextColor(color)
    }
    (0 until childCount).asSequence().forEach { (getChildAt(it) as? ImageButton)?.setColorFilter(color) }
}

// Attr retrievers
fun Context.resolveColor(@AttrRes attr: Int, @ColorInt fallback: Int = 0): Int {
    val a = theme.obtainStyledAttributes(intArrayOf(attr))
    try {
        return a.getColor(0, fallback)
    } finally {
        a.recycle()
    }
}
