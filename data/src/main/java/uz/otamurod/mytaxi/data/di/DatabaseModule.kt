package uz.otamurod.mytaxi.data.di

import org.koin.dsl.module
import uz.otamurod.mytaxi.data.database.datasource.LiveLocationDataSource
import uz.otamurod.mytaxi.data.database.db.LiveLocationDatabase

val databaseModule = module {
    single {
        LiveLocationDatabase.getInstance(get())
    }
    single {
        get<LiveLocationDatabase>().liveLocationDao()
    }
    single {
        LiveLocationDataSource(get())
    }
}