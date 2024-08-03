package uz.otamurod.mytaxi.domain.interactor

import uz.otamurod.mytaxi.domain.model.LiveLocation
import uz.otamurod.mytaxi.domain.util.DataState

interface LiveLocationInteractor {
    suspend fun getLiveLocation(): DataState<LiveLocation>
    suspend fun saveLiveLocation(liveLocation: LiveLocation)
}