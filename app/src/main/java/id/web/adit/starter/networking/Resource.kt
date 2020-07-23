@file:Suppress("KDocUnresolvedReference")

package id.web.adit.starter.networking


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.web.adit.starter.utils.wtF
import io.reactivex.observers.ResourceSingleObserver

/**
 * @author [adit] & [alize]
 * juni 2019
 * */

class Resource<T> : ResourceSingleObserver<T>() {

    private val data = MutableLiveData<Outcome<T>>()
    fun ldata(): LiveData<Outcome<T>> = data

    init {
        wtF("waiting for #response ...")
        data.postValue(Outcome.loading(null))
    }

    override fun onSuccess(t: T) {
        wtF("data #$t success accommodated in the resource")
        data.postValue(Outcome.success(t))
        dispose()
    }

    override fun onError(e: Throwable) {
        wtF("data #$e success accommodated in the resource")
        data.postValue(Outcome.error(Http.httpError(e), null))
        dispose()
    }

}
