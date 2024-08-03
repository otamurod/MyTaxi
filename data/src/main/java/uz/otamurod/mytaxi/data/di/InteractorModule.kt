package uz.otamurod.mytaxi.data.di

import org.koin.dsl.module
import uz.otamurod.mytaxi.data.interactor.LiveLocationInteractorImpl
import uz.otamurod.mytaxi.domain.interactor.LiveLocationInteractor

val interactorModule = module {
    single<LiveLocationInteractor> {
        LiveLocationInteractorImpl(get())
    }
}