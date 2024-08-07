package uz.otamurod.mytaxi.app.data.buildconfig

import uz.otamurod.mytaxi.app.BuildConfig
import uz.otamurod.mytaxi.specs.buildconfig.MyTaxiAppBuildConfig

class MyTaxiAppBuildConfigImpl : MyTaxiAppBuildConfig {
    override val mapBoxDownloadsToken: String
        get() = BuildConfig.MAPBOX_DOWNLOADS_TOKEN
}