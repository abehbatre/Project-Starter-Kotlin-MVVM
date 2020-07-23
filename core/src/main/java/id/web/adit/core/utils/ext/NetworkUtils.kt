package id.web.adit.core.utils.ext


import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by Allan Wang on 2017-07-07.
 */
@Suppress("DEPRECATION")
@Deprecated("Applications should make use of network callbacks instead of individual queries")
inline val Context.isNetworkAvailable: Boolean
    @SuppressLint("MissingPermission")
    get() {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo?.isConnectedOrConnecting ?: false
    }

@Suppress("DEPRECATION")
@Deprecated("Applications should make use of network callbacks instead of individual queries")
inline val Context.isWifiConnected: Boolean
    @SuppressLint("MissingPermission")
    get() {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return (activeNetworkInfo?.type ?: -1) == ConnectivityManager.TYPE_WIFI
    }

@Suppress("DEPRECATION")
@Deprecated("Applications should make use of network callbacks instead of individual queries")
inline val Context.isMobileDataConnected: Boolean
    @SuppressLint("MissingPermission")
    get() {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return (activeNetworkInfo?.type ?: -1) == ConnectivityManager.TYPE_MOBILE
    }
