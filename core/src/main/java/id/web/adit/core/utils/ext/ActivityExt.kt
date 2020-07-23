@file:Suppress( "KDocUnresolvedReference", "unused", "SpellCheckingInspection", "NOTHING_TO_INLINE",
    "DEPRECATION"
)

package id.web.adit.core.utils.ext

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.snackbar.Snackbar
import id.web.adit.core.R
import kotlin.system.exitProcess


@DslMarker
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE, AnnotationTarget.FUNCTION)
annotation class MDVKActivity




inline fun <reified T : Parcelable> Activity.getParcelableExtra(key: String) = lazy {
    intent.getParcelableExtra<T>(key)
}



// -----------------------------------------------------------------
/** [ViewPager]
// ---------------------------------------------------------------*/
fun AppCompatActivity.prepareViewPager(
        viewPager: ViewPager,
        limit: Int = 3,
        block: SectionsPagerAdapter.() -> Unit
) {
    SectionsPagerAdapter(supportFragmentManager).apply {
        block(this)
        viewPager.offscreenPageLimit = limit
        viewPager.currentItem = 0
        viewPager.adapter = this
    }
}


fun ViewPager.pageChange(block: (Int) -> Unit) {
    this.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) { }
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) { }
        override fun onPageSelected(position: Int) {
            block.invoke(position)
        }
    })
}

class SectionsPagerAdapter constructor(manager: FragmentManager) : FragmentPagerAdapter(manager) {

    private val mFragmentList: MutableList<Fragment> = mutableListOf()
    private val mFragmentTitleList: MutableList<String> = mutableListOf()

    override fun getItem(position: Int): Fragment { return mFragmentList[position] }
    override fun getCount(): Int { return mFragmentList.size }
    override fun getPageTitle(position: Int): CharSequence { return mFragmentTitleList[position] }

    fun addFragment(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }
}



/**
 * Helper class to launch an activity for result
 * Counterpart of [Activity.openActivityForResult]
 * For starting activities without result, see [openActivity]
 */
@Suppress("DEPRECATION")
inline fun <reified T : Activity> Activity.openActivityForResult(
    requestCode: Int,
    bundleBuilder: Bundle.() -> Unit = {},
    intentBuilder: Intent.() -> Unit = {}
) = openActivityForResult(T::class.java, requestCode, bundleBuilder, intentBuilder)

@Deprecated(
    "Use reified generic instead of passing class",
    ReplaceWith("startActivityForResult<T>(requestCode, bundleBuilder, intentBuilder)"),
    DeprecationLevel.WARNING
)
inline fun <T : Activity> Activity.openActivityForResult(
    clazz: Class<T>,
    requestCode: Int,
    bundleBuilder: Bundle.() -> Unit = {},
    intentBuilder: Intent.() -> Unit = {}
) {
    val intent = Intent(this, clazz)
    intent.intentBuilder()
    val bundle = Bundle()
    bundle.bundleBuilder()
    startActivityForResult(intent, requestCode, if (bundle.isEmpty) null else bundle)
}

/**
 * Restarts an activity from itself with a fade animation
 * Keeps its existing extra bundles and has a intentBuilder to accept other parameters
 */
inline fun Activity.restart(intentBuilder: Intent.() -> Unit = {}) {
    val i = Intent(this, this::class.java)
    val oldExtras = intent.extras
    if (oldExtras != null)
        i.putExtras(oldExtras)
    i.intentBuilder()
    startActivity(i)
    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    finish()
    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
}

/**
 * Force restart an entire application
 */
@RequiresApi(Build.VERSION_CODES.M)
inline fun Activity.restartApplication() {
    val intent = packageManager.getLaunchIntentForPackage(packageName)!!
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
    val pending = PendingIntent.getActivity(this, 666, intent, PendingIntent.FLAG_CANCEL_CURRENT)
    val alarm = getSystemService(Context.ALARM_SERVICE) as AlarmManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        alarm.setExactAndAllowWhileIdle(AlarmManager.RTC, System.currentTimeMillis() + 100, pending)
    else
        alarm.setExact(AlarmManager.RTC, System.currentTimeMillis() + 100, pending)
    finish()
    exitProcess(0)
}



inline var Activity.navigationBarColor: Int
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) window.navigationBarColor else Color.BLACK
    set(value) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return
        }
        window.navigationBarColor = value
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return
        }
        var prevSystemUiVisibility = window.decorView.systemUiVisibility
        prevSystemUiVisibility = if (value.isColorDark) {
            prevSystemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
        } else {
            prevSystemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
        window.decorView.systemUiVisibility = prevSystemUiVisibility
    }

inline var Activity.statusBarColor: Int
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) window.statusBarColor else Color.BLACK
    set(value) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return
        }
        window.statusBarColor = value
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return
        }
        var prevSystemUiVisibility = window.decorView.systemUiVisibility
        prevSystemUiVisibility = if (value.isColorDark) {
            prevSystemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        } else {
            prevSystemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        window.decorView.systemUiVisibility = prevSystemUiVisibility
    }


fun Activity.hideKeyboard() {
    currentFocus?.hideKeyboard()
}

fun Activity.showKeyboard() {
    currentFocus?.showKeyboard()
}

/**
 * Gets the view set by [Activity.setContentView] if it exists.
 *
 * Taken courtesy of <a href="https://github.com/Kotlin/anko">Anko</a>
 *
 * Previously, Anko was a dependency in KAU, but has been removed on 12/24/2018
 * as most of the methods weren't used
 */
inline val Activity.contentView: View?
    get() = (findViewById(android.R.id.content) as? ViewGroup)?.getChildAt(0)

inline fun Activity.snackbar(
    text: String,
    duration: Int = Snackbar.LENGTH_LONG,
    noinline builder: Snackbar.() -> Unit = {}
) = contentView!!.snackbar(text, duration, builder)

inline fun Activity.snackbar(
    @StringRes textId: Int,
    duration: Int = Snackbar.LENGTH_LONG,
    noinline builder: Snackbar.() -> Unit = {}
) = contentView!!.snackbar(textId, duration, builder)

fun Activity.isPortrait() = requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
fun Activity.getDeviceWidth() = with(this) {
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    displayMetrics.widthPixels
}

fun Activity.getDeviceHeight() = with(this) {
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    displayMetrics.heightPixels
}

fun Activity.enableFullScreen(isEnabled: Boolean) {
    if (isEnabled) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

    } else {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
    }
}



fun Activity.changeSystemBarColor(@ColorRes color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val window = window
        window.apply {
           addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
           clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
           statusBarColor = color(color)
        }
    }
}


