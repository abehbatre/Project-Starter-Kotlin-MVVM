package id.web.adit.starter.ui.__base

import androidx.annotation.LayoutRes
import id.web.adit.core.base.MDVKFragment

abstract class BaseFragment(@LayoutRes layout: Int = 0) : MDVKFragment(layout) {

    fun baseActicity() = requireActivity() as BaseActivity

    fun isLoading(b: Boolean, message: String? = null, dismissable: Boolean = true) {
        baseActicity().isLoading(b, message, dismissable)
    }


}
