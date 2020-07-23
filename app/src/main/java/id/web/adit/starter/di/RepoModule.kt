package id.web.adit.starter.di

import id.web.adit.starter.datasource.AppRepository
import org.koin.dsl.module

@JvmField
val repoModule = module {
     factory { AppRepository(get()) }
}
