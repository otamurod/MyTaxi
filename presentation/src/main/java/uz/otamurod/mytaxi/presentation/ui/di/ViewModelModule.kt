package uz.otamurod.mytaxi.presentation.ui.di

import org.koin.dsl.module
import uz.otamurod.mytaxi.presentation.ui.home.HomeViewModel

val viewModelModule = module {
    single { HomeViewModel(get()) }
}