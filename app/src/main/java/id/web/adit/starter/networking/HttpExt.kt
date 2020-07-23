@file:Suppress("KDocUnresolvedReference", "unused")

package id.web.adit.starter.networking

import android.content.Context
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLSession

@Suppress("DEPRECATION")
fun Context.clientInterceptor(): OkHttpClient = OkHttpClient()
    .newBuilder()
    .connectTimeout(HttpConfig.CONNECT_TIME_OUT, TimeUnit.SECONDS)
    .writeTimeout(HttpConfig.WRITE_TIME_OUT, TimeUnit.SECONDS)
    .readTimeout(HttpConfig.READ_TIME_OUT, TimeUnit.SECONDS)
    .sslSocketFactory(SslUtils.getSslContextForCertificateFile(this, HttpConfig.SSL_PATH).socketFactory)
    .hostnameVerifier { _: String?, _: SSLSession? -> true }
    .addInterceptor(ChuckInterceptor(this))
    .build()
