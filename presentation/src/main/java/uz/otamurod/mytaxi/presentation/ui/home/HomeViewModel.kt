package uz.otamurod.mytaxi.presentation.ui.home

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.otamurod.mytaxi.domain.interactor.LiveLocationInteractor
import uz.otamurod.mytaxi.domain.model.LiveLocation
import uz.otamurod.mytaxi.presentation.ui.base.BaseViewModel
import uz.otamurod.mytaxi.presentation.ui.home.HomeScreenReducer.HomeEffect
import uz.otamurod.mytaxi.presentation.ui.home.HomeScreenReducer.HomeEvent
import uz.otamurod.mytaxi.presentation.ui.home.HomeScreenReducer.HomeState
import uz.otamurod.mytaxi.specs.buildconfig.MyTaxiAppBuildConfig

class HomeViewModel(
    private val liveLocationInteractor: LiveLocationInteractor,
    private val myTaxiAppBuildConfig: MyTaxiAppBuildConfig
) : BaseViewModel<HomeState, HomeEvent, HomeEffect>(
    initialState = HomeState.initial(),
    reducer = HomeScreenReducer()
) {

    fun saveLocation(location: LiveLocation) {
        viewModelScope.launch(Dispatchers.IO) {
            liveLocationInteractor.saveLiveLocation(
                liveLocation = location
            )
        }
    }

    fun getMapBoxDownloadsToken(): String {
        return myTaxiAppBuildConfig.mapBoxDownloadsToken
    }
}