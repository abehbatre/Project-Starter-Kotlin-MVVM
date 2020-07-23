@file:Suppress("KDocUnresolvedReference")

package id.web.adit.starter.networking

import android.content.Context
import id.web.adit.starter.R
import id.web.adit.starter.utils.wtF
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

/**
 * @author [MateuszKlimek]
 * @modify [adit]
 * @source https://gist.github.com/mklimek/f9d197362c1f2db8c1b76f76ace75859
 *
 * Adit Say :
 * I wrote a simple helper which allows loading specific certificate into SSLContext.
 * You can use it to support untrusted certificate HTTPS connections.
 * By untrusted certificate I mean this one, which server is certified but system denies it (doesn’t trust it) for some reason.
 * I found it very useful to load particular certificate dynamically.

 * For example:
 * - Older Android devices don’t support some new CA providers. If you want to ship an app with support to such CA and don’t want to force a user to install it himself you can add that CA to the app at runtime. Totally transparent to the user.
 * - Security reasons. No need to install third party certs on the system directly. Eg. during development phase server might be certified by temporarily ssh-development-only-certificate.cer. No one should trust it except development-phase client app. The second case: you want to use the web proxy. It’s also risky to install proxy certificate for the whole system.
 * - You have no rights to add proper CA to the system. You told about it your administrator but you’re still waiting or worse, he refuses.

 * */


object SslUtils {

    fun getSslContextForCertificateFile(context: Context, fileName: String): SSLContext {
        try {
            val keyStore = getKeyStore(context, fileName)
            val sslContext = SSLContext.getInstance("SSL")
            val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            trustManagerFactory.init(keyStore)
            sslContext.init(null, trustManagerFactory.trustManagers, SecureRandom())
            return sslContext
        } catch (e: Exception) {
            val msg = context.getString(R.string.ssl_msg_error)
            e.printStackTrace()
            throw RuntimeException(msg)
        }
    }

    private fun getKeyStore(context: Context, fileName: String): KeyStore? {
        var keyStore: KeyStore? = null
        try {
            val assetManager = context.assets
            val cf = CertificateFactory.getInstance("X.509")
            val caInput = assetManager.open(fileName)
            val ca: Certificate
            try {
                ca = cf.generateCertificate(caInput)
                wtF("ca=" + (ca as X509Certificate).subjectDN)
            } finally {
                caInput.close()
            }

            val keyStoreType = KeyStore.getDefaultType()
            keyStore = KeyStore.getInstance(keyStoreType)
            keyStore!!.load(null, null)
            keyStore.setCertificateEntry("ca", ca)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return keyStore
    }
}
