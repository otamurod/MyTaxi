package uz.otamurod.mytaxi.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import uz.otamurod.mytaxi.data.di.databaseModule
import uz.otamurod.mytaxi.data.di.interactorModule
import uz.otamurod.mytaxi.data.di.repositoryModule
import uz.otamurod.mytaxi.presentation.ui.di.viewModelModule

class MyTaxiApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(
                databaseModule,
                repositoryModule,
                interactorModule,
                viewModelModule
            )
        }
    }
}