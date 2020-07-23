@file:Suppress("KDocUnresolvedReference")

package id.web.adit.starter

import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.androidnetworking.AndroidNetworking
import id.web.adit.starter.di.appModule
import id.web.adit.core.R
import id.web.adit.core.base.MDVKPref
import id.web.adit.starter.networking.clientInterceptor
import id.web.adit.starter.di.repoModule
import id.web.adit.starter.di.vmModule
import id.web.adit.starter.utils.wtF
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber


class AppController : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        // —————————————————————————————————————————————————————————————————
        /** @HTTP_NETWORKING  */
        // —————————————————————————————————————————————————————————————————
        AndroidNetworking.initialize(applicationContext, clientInterceptor())
        when (BuildConfig.DEBUG) {
            true -> AndroidNetworking.enableLogging()
            false -> wtF("Module FAN off, coz you running on release version.")
        }


        // —————————————————————————————————————————————————————————————————
        /** @KOIN */
        // —————————————————————————————————————————————————————————————————
        startKoin {
            wtF("~ INSTALL KOIN ~")
            androidContext(this@AppController)
            androidLogger()
            modules(appModule, repoModule, vmModule)
        }


        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        // —————————————————————————————————————————————————————————————————
        /** @TIMBER */
        // —————————————————————————————————————————————————————————————————
        when (BuildConfig.DEBUG) {
            true -> {
                Timber.plant(object : Timber.DebugTree() {
                    override fun createStackElementTag(element: StackTraceElement): String? {
                        return "(${element.fileName}:${element.lineNumber})#${element.methodName}"
                    }
                })
            }
            false -> wtF("Module TIMBER off, coz you running on release version.")
        }

        // —————————————————————————————————————————————————————————————————
        /** @CALLIGRAPHY */
        // —————————————————————————————————————————————————————————————————
        ViewPump.init(
            ViewPump.builder().addInterceptor(
                CalligraphyInterceptor(
                    CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/GoogleSans-Regular.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
                )
            ).build()
        )


        // —————————————————————————————————————————————————————————————————
        /** @SHARED_PREFERENCE */
        // —————————————————————————————————————————————————————————————————
        MDVKPref.init(applicationContext)
    }


}
