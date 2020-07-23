@file:Suppress("unused")

package id.web.adit.core.utils.ext


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewPropertyAnimator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import kotlin.math.hypot
import kotlin.math.max

/**
 * Created by Allan Wang on 2017-06-01.
 *
 * Animation extension functions for Views
 */

@SuppressLint("NewApi")

fun View.circularReveal(
    x: Int = 0,
    y: Int = 0,
    offset: Long = 0L,
    radius: Float = -1.0f,
    duration: Long = 500L,
    onStart: (() -> Unit)? = null,
    onFinish: (() -> Unit)? = null
) {
    if (!isAttachedToWindow) {
        onStart?.invoke()
        visible()
        onFinish?.invoke()
        return
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) return fadeIn(
        offset,
        duration,
        onStart,
        onFinish
    )

    val r = if (radius >= 0) radius
    else max(
        hypot(x.toDouble(), y.toDouble()),
        hypot((width - x.toDouble()), (height - y.toDouble()))
    ).toFloat()

    val anim = ViewAnimationUtils.createCircularReveal(this, x, y, 0f, r).setDuration(duration)
    anim.startDelay = offset
    anim.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationStart(animation: Animator?) {
            visible()
            onStart?.invoke()
        }

        override fun onAnimationEnd(animation: Animator?) = onFinish?.invoke() ?: Unit
        override fun onAnimationCancel(animation: Animator?) = onFinish?.invoke() ?: Unit
    })
    anim.start()
}

@SuppressLint("NewApi")

fun View.circularHide(
    x: Int = 0,
    y: Int = 0,
    offset: Long = 0L,
    radius: Float = -1.0f,
    duration: Long = 500L,
    onStart: (() -> Unit)? = null,
    onFinish: (() -> Unit)? = null
) {
    if (!isAttachedToWindow) {
        onStart?.invoke()
        invisible()
        onFinish?.invoke()
        return
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) return fadeOut(
        offset,
        duration,
        onStart,
        onFinish
    )

    val r = if (radius >= 0) radius
    else max(
        hypot(x.toDouble(), y.toDouble()),
        hypot((width - x.toDouble()), (height - y.toDouble()))
    ).toFloat()

    val anim = ViewAnimationUtils.createCircularReveal(this, x, y, r, 0f).setDuration(duration)
    anim.startDelay = offset
    anim.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationStart(animation: Animator?) = onStart?.invoke() ?: Unit

        override fun onAnimationEnd(animation: Animator?) {
            invisible()
            onFinish?.invoke() ?: Unit
        }

        override fun onAnimationCancel(animation: Animator?) = onFinish?.invoke() ?: Unit
    })
    anim.start()
}


@RequiresApi(Build.VERSION_CODES.KITKAT)
fun View.fadeIn(
    offset: Long = 0L,
    duration: Long = 200L,
    onStart: (() -> Unit)? = null,
    onFinish: (() -> Unit)? = null
) {
    if (!isAttachedToWindow) {
        onStart?.invoke()
        visible()
        onFinish?.invoke()
        return
    }
    val anim = AnimationUtils.loadAnimation(context, android.R.anim.fade_in)
    anim.startOffset = offset
    anim.duration = duration
    anim.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(animation: Animation?) {}
        override fun onAnimationEnd(animation: Animation?) = onFinish?.invoke() ?: Unit
        override fun onAnimationStart(animation: Animation?) {
            visible()
            onStart?.invoke()
        }
    })
    startAnimation(anim)
}


@RequiresApi(Build.VERSION_CODES.KITKAT)
fun View.fadeOut(
    offset: Long = 0L,
    duration: Long = 200L,
    onStart: (() -> Unit)? = null,
    onFinish: (() -> Unit)? = null
) {
    if (!isAttachedToWindow) {
        onStart?.invoke()
        invisible()
        onFinish?.invoke()
        return
    }
    val anim = AnimationUtils.loadAnimation(context, android.R.anim.fade_out)
    anim.startOffset = offset
    anim.duration = duration
    anim.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(animation: Animation?) {}
        override fun onAnimationEnd(animation: Animation?) {
            invisible()
            onFinish?.invoke()
        }

        override fun onAnimationStart(animation: Animation?) {
            onStart?.invoke()
        }
    })
    startAnimation(anim)
}


@RequiresApi(Build.VERSION_CODES.KITKAT)
fun TextView.setTextWithFade(text: String, duration: Long = 200, onFinish: (() -> Unit)? = null) {
    fadeOut(duration = duration, onFinish = {
        setText(text)
        fadeIn(duration = duration, onFinish = onFinish)
    })
}


@RequiresApi(Build.VERSION_CODES.KITKAT)
fun TextView.setTextWithFade(
    @StringRes textId: Int,
    duration: Long = 200,
    onFinish: (() -> Unit)? = null
) =
    setTextWithFade(context.getString(textId), duration, onFinish)


fun ViewPropertyAnimator.scaleXY(value: Float): ViewPropertyAnimator = scaleX(value).scaleY(value)
