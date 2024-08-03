package uz.otamurod.mytaxi.domain.repository

import uz.otamurod.mytaxi.domain.model.LiveLocation
import uz.otamurod.mytaxi.domain.util.DataState

interface LocalRepository {
    suspend fun getLiveLocation(): DataState<LiveLocation>
    suspend fun saveLiveLocation(liveLocation: LiveLocation)
}