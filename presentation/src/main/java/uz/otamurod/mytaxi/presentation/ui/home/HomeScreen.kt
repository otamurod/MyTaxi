package uz.otamurod.mytaxi.presentation.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.NoOpUpdate
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import com.mapbox.common.MapboxOptions
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import uz.otamurod.mytaxi.presentation.R
import uz.otamurod.mytaxi.presentation.ui.home.HomeScreenReducer.HomeEvent
import uz.otamurod.mytaxi.presentation.ui.home.components.BottomSheetContent
import uz.otamurod.mytaxi.presentation.ui.home.components.HamburgerIcon
import uz.otamurod.mytaxi.presentation.ui.home.components.MiddleRightIcons
import uz.otamurod.mytaxi.presentation.ui.home.components.MiddleUpArrowIcon
import uz.otamurod.mytaxi.presentation.ui.home.components.SpeedIndicator
import uz.otamurod.mytaxi.presentation.ui.home.components.TabViewWithAnimation
import uz.otamurod.mytaxi.presentation.util.compose.rememberFlowWithLifecycle

@SuppressLint("UseCompatLoadingForDrawables")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel, onMapReady: (Boolean) -> Unit
) {
    MapboxOptions.accessToken = viewModel.getMapBoxDownloadsToken()
    val context = LocalContext.current
    val state = viewModel.state.collectAsStateWithLifecycle()
    val effect = rememberFlowWithLifecycle(viewModel.effect)

    val mapBoxMap = remember { mutableStateOf<MapView?>(null) }
    var pointAnnotationManager: PointAnnotationManager? by remember { mutableStateOf(null) }

    val scaffoldState = rememberBottomSheetScaffoldState()
    val isExpanded = remember { mutableStateOf(false) }
    val isMiddleItemsVisible = remember { mutableStateOf(true) }

    val mapZoomLevel = remember { mutableStateOf(15.0) }
    val point = Point.fromLngLat(
        state.value.userLiveLocation?.longitude ?: 39.0,
        state.value.userLiveLocation?.latitude ?: 41.0
    )
    val marker = remember(context) { context.getDrawable(R.drawable.car_marker)!!.toBitmap() }

    if (state.value.isMapReady) {
        LaunchedEffect(state.value.isMapReady) {
            onMapReady(true)
        }
    }

    state.value.userLiveLocation?.let { location ->
        viewModel.saveLocation(location)
    }

    BottomSheetNavigator {
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetContent = { BottomSheetContent(isMiddleItemsVisible.value) },
            sheetPeekHeight = if (isMiddleItemsVisible.value) 110.dp else 210.dp,
            sheetShape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp)
        ) {
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (map, hamburger, tabs, speed, upArrow, plusMinusNav) = createRefs()

                // MapBox Implementation
                AndroidView(factory = {
                    MapView(it).also { mapView ->
                        mapBoxMap.value = mapView
                        mapView.mapboxMap.loadStyle(Style.MAPBOX_STREETS)
                        val annotationApi = mapView.annotations

                        pointAnnotationManager = annotationApi.createPointAnnotationManager()

                        mapView.mapboxMap.subscribeMapLoaded(mapLoadedCallback = {
                            sendMapReady(viewModel = viewModel, isMapReady = true)
                        })

                        mapView.mapboxMap.addOnMapClickListener(onMapClickListener = {
                            toggleUIVisibility(isExpanded, isMiddleItemsVisible)
                            true
                        })
                    }
                }, update = { mapView ->
                    if (point != null) {
                        pointAnnotationManager?.let {
                            it.deleteAll()
                            val pointAnnotationOptions =
                                PointAnnotationOptions().withPoint(point).withIconImage(marker)

                            it.create(pointAnnotationOptions)
                        }
                        mapView.mapboxMap.flyTo(
                            flyCameraToLiveLocation(mapZoomLevel, state)
                        )
                    }
                    NoOpUpdate
                }, modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(map) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .clickable {
                        toggleUIVisibility(isExpanded, isMiddleItemsVisible)
                    })

                // Hamburger Icon
                HamburgerIcon(modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
                    .size(56.dp)
                    .constrainAs(hamburger) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    })

                val activeTab = remember { mutableStateOf(context.getString(R.string.faol)) }

                // Tabs for Busy and Active
                TabViewWithAnimation(modifier = Modifier
                    .padding(top = 16.dp)
                    .height(56.dp)
                    .constrainAs(tabs) {
                        top.linkTo(parent.top)
                        start.linkTo(hamburger.end)
                        end.linkTo(speed.start)
                    })

                // Speed Text (Top-Right)
                SpeedIndicator(modifier = Modifier
                    .padding(end = 16.dp, top = 16.dp)
                    .size(56.dp)
                    .constrainAs(speed) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    })

                // Up Arrow (Middle-Left)
                if (isMiddleItemsVisible.value) {
                    MiddleUpArrowIcon(modifier = Modifier
                        .padding(start = 16.dp)
                        .size(56.dp)
                        .constrainAs(upArrow) {
                            start.linkTo(parent.start)
                            bottom.linkTo(map.bottom)
                            top.linkTo(map.top)
                        })
                }

                // Plus, Minus, Navigator (Middle-Right)
                if (isMiddleItemsVisible.value) {
                    MiddleRightIcons(modifier = Modifier
                        .padding(end = 16.dp)
                        .width(56.dp)
                        .constrainAs(plusMinusNav) {
                            end.linkTo(parent.end)
                            top.linkTo(upArrow.top)
                        }, onZoomIn = {
                        mapZoomLevel.value += 1.0
                    }, onZoomOut = {
                        if (mapZoomLevel.value > 1) {
                            mapZoomLevel.value -= 1.0
                        }
                    }, onNavigator = {
                        mapBoxMap.value?.let {
                            it.mapboxMap.flyTo(
                                flyCameraToLiveLocation(mapZoomLevel, state)
                            )
                        }
                    })
                }
            }
        }
    }
}

private fun flyCameraToLiveLocation(
    mapZoomLevel: MutableState<Double>, state: State<HomeScreenReducer.HomeState>
): CameraOptions {
    return CameraOptions.Builder().zoom(mapZoomLevel.value).center(
        Point.fromLngLat(
            state.value.userLiveLocation?.longitude ?: 39.0,
            state.value.userLiveLocation?.latitude ?: 41.0
        )
    ).build()
}

private fun sendMapReady(viewModel: HomeViewModel, isMapReady: Boolean) {
    viewModel.sendEvent(HomeEvent.SetMapReady(isMapReady = isMapReady))
}

private fun toggleUIVisibility(
    isExpanded: MutableState<Boolean>, isMiddleItemsVisible: MutableState<Boolean>
) {
    isExpanded.value = !isExpanded.value
    isMiddleItemsVisible.value = !isMiddleItemsVisible.value
}
