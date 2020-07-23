package id.web.adit.starter.ui

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import id.web.adit.core.scope.View
import id.web.adit.core.utils.ext.enableFullScreen
import id.web.adit.core.utils.ext.isNetworkAvailable
import id.web.adit.core.utils.ext.openActivity
import id.web.adit.starter.BuildConfig
import id.web.adit.starter.R
import id.web.adit.starter.utils.wtF

@View("" +
        "without layout | replace default loading screen (Splash Screen on Android : Right Way)" +
        "reference : https://s.id/mESSL" +
        "")
class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableFullScreen(true)
        super.onCreate(savedInstanceState)
        checkConnection()
    }

    private fun checkConnection() {
        when (isNetworkAvailable) {
            true -> startMyApplication()

            false -> {
                MaterialDialog(this).show {
                    icon(R.drawable.ic_wifi_disconnected)
                    cancelOnTouchOutside(false)
                    title(text = getString(R.string.txt_no_connection_title))
                    message(text = getString(R.string.txt_no_connection_desc))
                    positiveButton(text = getString(R.string.txt_yes)) { startMyApplication() }
                    negativeButton(text = getString(R.string.txt_no)) { finishAffinity() }
                }
            }
        }
    }


    private fun startMyApplication() {
        when (BuildConfig.DEBUG) {
            true -> openActivity<MainActivity>()    // MainActivity
            false -> openActivity<MainActivity>()   // LoginActivity
        }

        checkPhoneScreenSize {
            when(it) {
                1 -> { /*smallPhone*/ }
                2 -> { /*normalPhone*/ }
                3 -> { /*largePhone*/ }
                else -> { /*unknownPhone*/ }
            }
        }

        finish()
    }

    private fun checkPhoneScreenSize(
        callback : (Int) -> Unit = {}
    ) {
        when (resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) {
            Configuration.SCREENLAYOUT_SIZE_SMALL  -> callback(1)
            Configuration.SCREENLAYOUT_SIZE_NORMAL -> callback(2)
            Configuration.SCREENLAYOUT_SIZE_LARGE  -> callback(3)
            else -> wtF("UNKNOWN SIZE , MAYBE YOUR PHONE FROM PLANET NAMEX")
        }
    }


    /** initialization */
    init {
        wtF("-----------------------------------------------------------")
        wtF("💎 ADIT HERE 👿 ")
        wtF("-----------------------------------------------------------")
        wtF("APP ID             -> ${BuildConfig.APPLICATION_ID}")
        wtF("APP VER            -> ${BuildConfig.VERSION_NAME}")
        wtF("APP CODE           -> ${BuildConfig.VERSION_CODE}")
        wtF("-----------------------------------------------------------")
        wtF("DEVICE NAME        -> ${Build.MODEL}")
        wtF("DEVICE SDK         -> ${Build.VERSION.SDK_INT}")
        wtF("-----------------------------------------------------------")
        wtF("BASE_URL           -> ${BuildConfig.BUILD_TYPE}")
        wtF("DEVMODE?           -> ${BuildConfig.DEBUG}")
        wtF("-----------------------------------------------------------")
    }
}
