package uz.otamurod.mytaxi.data.interactor

import uz.otamurod.mytaxi.domain.interactor.LiveLocationInteractor
import uz.otamurod.mytaxi.domain.model.LiveLocation
import uz.otamurod.mytaxi.domain.repository.LocalRepository
import uz.otamurod.mytaxi.domain.util.DataState

class LiveLocationInteractorImpl(
    private val localRepository: LocalRepository
) : LiveLocationInteractor {
    override suspend fun getLiveLocation(): DataState<LiveLocation> {
        return localRepository.getLiveLocation()
    }

    override suspend fun saveLiveLocation(liveLocation: LiveLocation) {
        return localRepository.saveLiveLocation(liveLocation)
    }
}