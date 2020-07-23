package id.web.adit.starter.ui.dashboard

import id.web.adit.core.scope.BusinessFunction
import id.web.adit.starter.datasource.AppRepository
import id.web.adit.starter.datasource.model.ReposPojo
import id.web.adit.starter.networking.Resource
import id.web.adit.starter.ui.__base.BaseViewModel

@BusinessFunction
class HomeViewModel (private val repo : AppRepository) : BaseViewModel() {

    fun getListCustomersCurrent(): Resource<ReposPojo> {
        return repo.getUserReposList("abehbatre")
    }

}
