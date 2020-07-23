@file:Suppress("SpellCheckingInspection", "KDocUnresolvedReference", "unused")

package id.web.adit.starter.networking

import android.content.res.Resources
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.rx2androidnetworking.Rx2ANRequest
import id.web.adit.starter.R
import org.json.JSONObject

object Http {

    fun get(endpoint: String): Rx2ANRequest.GetRequestBuilder {
        return Rx2ANRequest.GetRequestBuilder(HttpConfig.BASE_URL.plus(endpoint))
    }

    fun post(endpoint: String): Rx2ANRequest.PostRequestBuilder {
        val rx2ANRequest = Rx2ANRequest.PostRequestBuilder(HttpConfig.BASE_URL.plus(endpoint))
        rx2ANRequest.setPriority(Priority.HIGH)
        return rx2ANRequest
    }

    fun put(endpoint: String): Rx2ANRequest.PostRequestBuilder {
        return Rx2ANRequest.PutRequestBuilder(HttpConfig.BASE_URL.plus(endpoint))
    }

    fun delete(endpoint: String): Rx2ANRequest.PostRequestBuilder {
        return Rx2ANRequest.DeleteRequestBuilder(HttpConfig.BASE_URL.plus(endpoint))
    }


    // —————————————————————————————————————————————————————————————————
    /** [ERROR]
    // —————————————————————————————————————————————————————————————————*/
    fun httpError(e: Throwable): String {
        val anError = e as ANError
        val r = Resources.getSystem()
        return when (anError.errorCode) {
            // ERROR 400
            HttpCode.BAD_REQUEST -> {
                val jsonObj = JSONObject(anError.errorBody)
                jsonObj.getJSONArray("errors").getJSONObject(0).getString("code")
            }
            // COMMON ERROR
            HttpCode.NOT_FOUND -> "Path not found / Invalid URL"
            HttpCode.WRONG_METHOD -> "Metode Salah <-> Tidak Diizinkan"
            HttpCode.INTERNAL_SERVER_ERROR -> "Kesalahan didalam server"
            HttpCode.NO_CONNECTION -> "Tidak dapat terhubung ke server | Request time out"
            HttpCode.SERVER_MAINTENANCE -> "Server sedang maintenance"
            else -> "${anError.errorCode} | Unexpected Error"
        }
    }
}
