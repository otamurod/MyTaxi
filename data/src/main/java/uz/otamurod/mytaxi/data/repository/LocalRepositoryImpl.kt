package uz.otamurod.mytaxi.data.repository

import android.util.Log
import uz.otamurod.mytaxi.data.database.datasource.LiveLocationDataSource
import uz.otamurod.mytaxi.data.mapper.LiveLocationMapper
import uz.otamurod.mytaxi.domain.model.LiveLocation
import uz.otamurod.mytaxi.domain.repository.LocalRepository
import uz.otamurod.mytaxi.domain.util.DataState

class LocalRepositoryImpl(
    private val liveLocationDataSource: LiveLocationDataSource
) : LocalRepository {
    override suspend fun getLiveLocation(): DataState<LiveLocation> {
        try {
            val liveLocationEntity = liveLocationDataSource.getLiveLocation()
            return if (liveLocationEntity != null) {
                DataState.Success(LiveLocationMapper.LiveLocation(liveLocationEntity).invoke())
            } else {
                DataState.Error("No data found")
            }
        } catch (e: Exception) {
            Log.d(TAG, "getLiveLocation: ${e.printStackTrace()}")
            return DataState.Error(e.message.toString())
        }
    }

    override suspend fun saveLiveLocation(liveLocation: LiveLocation) {
        try {
            liveLocationDataSource.saveLiveLocation(
                LiveLocationMapper.LiveLocationEntity(liveLocation).invoke()
            )
        } catch (e: Exception) {
            Log.d(TAG, "saveLiveLocation: ${e.printStackTrace()}")
        }
    }

    companion object {
        private const val TAG = "LocalRepositoryImpl"
    }
}