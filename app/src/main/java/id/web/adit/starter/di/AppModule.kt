package id.web.adit.starter.di

import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.dsl.module

@JvmField
val appModule = module {
    factory { LinearLayoutManager(get()) }
}


