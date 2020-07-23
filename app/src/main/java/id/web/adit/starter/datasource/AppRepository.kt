package id.web.adit.starter.datasource

import android.app.Application
import id.web.adit.core.scope.Repository
import id.web.adit.starter.datasource.model.ReposPojo
import id.web.adit.starter.datasource.model.ReposPojoItem
import id.web.adit.starter.datasource.remote.Endpoint
import id.web.adit.starter.networking.Http
import id.web.adit.starter.networking.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors

@Repository
class AppRepository (app: Application) {

    // private val db = AppDatabase.getInstance(application)

    // —————————————————————————————————————————————————————————————————
    /** [REMOTE_API_EXAMPLE]
    // —————————————————————————————————————————————————————————————————*/
     fun getUserReposList(username : String): Resource<ReposPojo> {
         return Resource<ReposPojo>().apply {
             Http.get(Endpoint.USER_REPOS)
                 .addPathParameter("username", username)
                 .build()
                 .getObjectSingle(ReposPojo::class.java)
                 .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(this)
         }
     }



    // —————————————————————————————————————————————————————————————————
    /** [PERSISTANCE_EXAMPLE]
    // —————————————————————————————————————————————————————————————————*/
    // fun updateFlagProduct(product: SoProduct) = ioThread {
    //     db.productDao().update(product)
    // }



    companion object {
        private val IO_EXECUTOR = Executors.newSingleThreadExecutor()
        fun ioThread(f: () -> Unit) {
            IO_EXECUTOR.execute(f)
        }
    }
}
