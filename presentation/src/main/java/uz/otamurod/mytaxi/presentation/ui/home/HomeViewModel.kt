package uz.otamurod.mytaxi.presentation.ui.home

import uz.otamurod.mytaxi.domain.interactor.LiveLocationInteractor
import uz.otamurod.mytaxi.presentation.ui.base.BaseViewModel
import uz.otamurod.mytaxi.presentation.ui.home.HomeScreenReducer.HomeEffect
import uz.otamurod.mytaxi.presentation.ui.home.HomeScreenReducer.HomeEvent
import uz.otamurod.mytaxi.presentation.ui.home.HomeScreenReducer.HomeState

class HomeViewModel(
    private val liveLocationInteractor: LiveLocationInteractor
) : BaseViewModel<HomeState, HomeEvent, HomeEffect>(
    initialState = HomeState.initial(),
    reducer = HomeScreenReducer()
) {

}