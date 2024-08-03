package uz.otamurod.mytaxi.data.database.datasource

import uz.otamurod.mytaxi.data.database.dao.LiveLocationDao
import uz.otamurod.mytaxi.data.database.entity.LiveLocationEntity

class LiveLocationDataSource(
    private val liveLocationDao: LiveLocationDao
) {
    suspend fun getLiveLocation(): LiveLocationEntity? {
        return liveLocationDao.getLiveLocation()
    }

    suspend fun saveLiveLocation(liveLocation: LiveLocationEntity) {
        liveLocationDao.saveLiveLocation(liveLocation)
    }
}