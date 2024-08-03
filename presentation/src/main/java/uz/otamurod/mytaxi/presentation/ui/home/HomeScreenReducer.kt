package uz.otamurod.mytaxi.presentation.ui.home

import androidx.compose.runtime.Immutable
import uz.otamurod.mytaxi.domain.model.LiveLocation
import uz.otamurod.mytaxi.presentation.ui.base.Reducer

class HomeScreenReducer :
    Reducer<HomeScreenReducer.HomeState, HomeScreenReducer.HomeEvent, HomeScreenReducer.HomeEffect> {

    @Immutable
    data class HomeState(
        val isLoading: Boolean,
        val isActiveTabOpen: Boolean,
        val isNetworkAvailable: Boolean,
        val isGPSEnabled: Boolean,
        val isLocationPermissionGranted: Boolean,
        val isLocationAccessing: Boolean,
        val userLiveLocation: LiveLocation?,
        val mapZoomLevel: Float,
        val itemsVisible: Boolean,
        val errorMessage: String?
    ) : Reducer.ViewState {

        companion object {
            fun initial(): HomeState {
                return HomeState(
                    isLoading = true,
                    isActiveTabOpen = false,
                    isNetworkAvailable = false,
                    isGPSEnabled = false,
                    isLocationPermissionGranted = false,
                    isLocationAccessing = false,
                    userLiveLocation = null,
                    mapZoomLevel = 1.0f,
                    itemsVisible = true,
                    errorMessage = null
                )
            }
        }
    }

    @Immutable
    sealed class HomeEvent : Reducer.ViewEvent {
        data class SetLoading(val isLoading: Boolean) : HomeEvent()
        data class SwitchTab(val isActiveTab: Boolean) : HomeEvent()
        data class ChangeMapZoom(val zoom: Float) : HomeEvent()
        data class ShowUserLocation(val liveLocation: LiveLocation) : HomeEvent()
        data class ToggleBottomDialog(val shouldDisplay: Boolean) : HomeEvent()
        data class ToggleMapItemsVisibility(val shouldHideItems: Boolean) : HomeEvent()
        data class GrantLocationPermission(val isGranted: Boolean) : HomeEvent()
    }

    @Immutable
    sealed class HomeEffect : Reducer.ViewEffect {
        data class ShowToast(val message: String) : HomeEffect()
        data class ShowSnackBar(val message: String) : HomeEffect()
    }

    override fun reduce(previousState: HomeState, event: HomeEvent): Pair<HomeState, HomeEffect?> {
        val newState = when (event) {
            is HomeEvent.SetLoading -> {
                previousState.copy(isLoading = event.isLoading)
            }

            is HomeEvent.SwitchTab -> {
                previousState.copy(isActiveTabOpen = event.isActiveTab)
            }

            is HomeEvent.ChangeMapZoom -> {
                previousState.copy(mapZoomLevel = event.zoom)
            }

            is HomeEvent.ShowUserLocation -> {
                previousState.copy(userLiveLocation = event.liveLocation)
            }

            is HomeEvent.ToggleBottomDialog -> {
                previousState.copy(itemsVisible = event.shouldDisplay)
            }

            is HomeEvent.ToggleMapItemsVisibility -> {
                previousState.copy(itemsVisible = event.shouldHideItems)
            }

            is HomeEvent.GrantLocationPermission -> {
                previousState.copy(isLocationPermissionGranted = event.isGranted)
            }
        }

        return newState to null
    }
}