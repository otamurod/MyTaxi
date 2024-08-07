package uz.otamurod.mytaxi.app.domain.di

import org.koin.dsl.module
import uz.otamurod.mytaxi.app.data.buildconfig.MyTaxiAppBuildConfigImpl
import uz.otamurod.mytaxi.specs.buildconfig.MyTaxiAppBuildConfig

val buildModule = module {
    single<MyTaxiAppBuildConfig> { MyTaxiAppBuildConfigImpl() }
}