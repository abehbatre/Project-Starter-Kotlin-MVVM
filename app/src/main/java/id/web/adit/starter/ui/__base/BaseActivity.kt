package id.web.adit.starter.ui.__base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.annotation.LayoutRes
import id.web.adit.core.base.MDVKActivity
import id.web.adit.core.utils.ext.changeSystemBarColor
import id.web.adit.starter.BuildConfig
import id.web.adit.starter.R
import id.web.adit.starter.utils.showLoading
import io.github.inflationx.viewpump.ViewPumpContextWrapper

abstract class BaseActivity(@LayoutRes private val layout: Int = 0) : MDVKActivity(layout) {
    protected var back2Exit = false

    companion object {
        private var dialog: Dialog? = null
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // prod vs dev
        when(BuildConfig.DEBUG) {
            false -> {
                // : do someshit here ~
            }
            true -> {
                // : do someshit here ~
            }
        }
    }


    // —————————————————————————————————————————————————————————————————
    /** [Life Cycler]
    // —————————————————————————————————————————————————————————————————*/
    override fun onStart() {
        changeSystemBarColor(R.color.colorPrimary)
        super.onStart()
    }

    override fun onResume() {
        isLoading(false)
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
    }
    override fun onDestroy(){
        isLoading(false)
        super.onDestroy()
        dialog?.let { dialog = null }

    }


    // —————————————————————————————————————————————————————————————————
    /** [Common Function]
    // —————————————————————————————————————————————————————————————————*/
    open fun isLoading(b: Boolean, message: String? = null, dismissable : Boolean = true) {
        when (b) {
            true -> {
                dialog = null
                if (dialog == null) {
                    dialog = showLoading(message, dismissable)
                    dialog?.setCancelable(dismissable)
                    dialog?.show()
                }
            }
            false -> {
                if (dialog != null && dialog!!.isShowing) {
                    dialog!!.cancel()
                    dialog!!.dismiss()
                    dialog = null
                }
            }
        }
    }

}
