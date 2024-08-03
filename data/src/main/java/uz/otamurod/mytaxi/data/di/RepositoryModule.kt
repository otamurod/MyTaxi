package uz.otamurod.mytaxi.data.di

import org.koin.dsl.module
import uz.otamurod.mytaxi.data.repository.LocalRepositoryImpl
import uz.otamurod.mytaxi.domain.repository.LocalRepository

val repositoryModule = module {
    single<LocalRepository> {
        LocalRepositoryImpl(get())
    }
}