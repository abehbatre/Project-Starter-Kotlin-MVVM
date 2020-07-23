package id.web.adit.starter.di


import id.web.adit.starter.ui.dashboard.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


@JvmField
val vmModule = module {
     viewModel { HomeViewModel(get()) }
}
