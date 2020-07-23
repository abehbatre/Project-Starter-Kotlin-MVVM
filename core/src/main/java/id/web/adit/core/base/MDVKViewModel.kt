@file:Suppress("KDocUnresolvedReference", "unused", "SpellCheckingInspection")

package id.web.adit.core.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import id.web.adit.core.utils.ext.logD
import io.reactivex.disposables.CompositeDisposable

// ------------------------------------------------------------------------------------
// @formatter:off ==> don't delete me
/** -----------------------------------------------------------------------------------
 *
 * @author : Aditya Pratama
 * @create : Aug 2019
 *
 * ViewModel is a class that is responsible for preparing and managing the data for
 * an {@link android.app.Activity Activity} or a {@link androidx.fragment.app.Fragment Fragment}.
 * It also handles the communication of the Activity / Fragment with the rest of the application
 * (e.g. calling the business logic classes).
 * <p>
 * A ViewModel is always created in association with a scope (an fragment or an activity) and will
 * be retained as long as the scope is alive. E.g. if it is an Activity, until it is
 * finished.
 * <p>
 * In other words, this means that a ViewModel will not be destroyed if its owner is destroyed for a
 * configuration change (e.g. rotation). The new instance of the owner will just re-connected to the
 * existing ViewModel.
 * <p>
 * The purpose of the ViewModel is to acquire and keep the information that is necessary for an
 * Activity or a Fragment. The Activity or the Fragment should be able to observe changes in the
 * ViewModel. ViewModels usually expose this information via {@link LiveData} or Android Data
 * Binding. You can also use any observability construct from you favorite framework.
 * <p>
 * ViewModel's only responsibility is to manage the data for the UI. It <b>should never</b> access
 * your view hierarchy or hold a reference back to the Activity or the Fragment.
 * ------------------------------------------------------------------------------------
 */
abstract class MDVKViewModel : ViewModel(), LifecycleObserver {

    protected val compositeDisposable by lazy { CompositeDisposable() }

    override fun onCleared() {
        with(compositeDisposable) {
            logD("size : ${this.size()}")
            logD("dispose & clear ~")
            clear()
            dispose()
        }
        super.onCleared()
    }

}

