@file:Suppress("KDocUnresolvedReference", "unused")

package id.web.adit.core.utils.ext

import android.app.Activity
import android.content.Intent
import android.os.Parcelable
import androidx.annotation.IntRange
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import es.dmoral.toasty.Toasty

inline fun <reified T : Parcelable> Fragment.getParcelableExtra(key: String) = lazy {
    requireActivity().getParcelableExtra<T>(key)
}


inline fun <reified T : Activity> Fragment.openActivity(block: Intent.() -> Unit = {}) {
    val intent = Intent(requireActivity(), T::class.java)
    block(intent)
    startActivity(intent)
}


fun <T : Fragment> T.withArguments(vararg params: Pair<String, Any?>): T {
    arguments = bundleOf(*params)
    return this
}


fun Fragment.toast(
    message: CharSequence,
    @IntRange(from = 0L, to = 3L) level: Int = 1,
    @IntRange(from = 0L, to = 1L) duration: Int = Toasty.LENGTH_LONG,
    log: Boolean = true
) {
    requireActivity().toast(message, level, duration, log)
}

