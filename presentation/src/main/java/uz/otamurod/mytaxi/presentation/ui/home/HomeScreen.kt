package uz.otamurod.mytaxi.presentation.ui.home

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.NoOpUpdate
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import com.google.accompanist.systemuicontroller.rememberSystemUiController
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
    SetSystemBarColors()

    MapboxOptions.accessToken = viewModel.getMapBoxDownloadsToken()
    val context = LocalContext.current
    val state = viewModel.state.collectAsStateWithLifecycle()
    val effect = rememberFlowWithLifecycle(viewModel.effect)

    val mapBoxMap = remember { mutableStateOf<MapView?>(null) }
    var pointAnnotationManager: PointAnnotationManager? by remember { mutableStateOf(null) }

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = false
        )
    )
    val isBottomSheetExpanded = remember { mutableStateOf(false) }
    val animatedBottomSheetHeight by animateDpAsState(
        targetValue = if (isBottomSheetExpanded.value) 216.dp else 126.dp,
        animationSpec = tween(durationMillis = 500), label = ""
    )

    val mapZoomLevel = remember { mutableStateOf(15.0) }
    val point = Point.fromLngLat(
        state.value.userLiveLocation?.longitude ?: 39.0,
        state.value.userLiveLocation?.latitude ?: 41.0
    )
    val marker = remember(context) { context.getDrawable(R.drawable.car_marker)!!.toBitmap() }
    val isDarkTheme = isSystemInDarkTheme()

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
            sheetContent = {
                BottomSheetContent(isBottomSheetExpanded.value)
            },
            sheetPeekHeight = animatedBottomSheetHeight,
            sheetShape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp),
            sheetDragHandle = null,
            sheetContainerColor = Color.Transparent,
            containerColor = Color.Transparent,
            sheetShadowElevation = 0.dp,
            sheetTonalElevation = 0.dp,
        ) {
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (map, hamburger, tabs, speed, upArrow, plusMinusNav) = createRefs()

                // MapBox Implementation
                AndroidView(factory = {
                    MapView(it).also { mapView ->
                        mapBoxMap.value = mapView
                        setMapStyle(mapView, isDarkTheme)

                        val annotationApi = mapView.annotations

                        pointAnnotationManager = annotationApi.createPointAnnotationManager()

                        mapView.mapboxMap.subscribeMapLoaded(mapLoadedCallback = {
                            sendMapReady(viewModel = viewModel, isMapReady = true)
                        })

                        mapView.mapboxMap.addOnMapClickListener(onMapClickListener = {
                            toggleMapControllerVisibility(isBottomSheetExpanded)
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
                    })

                // Hamburger Icon
                HamburgerIcon(modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
                    .size(56.dp)
                    .constrainAs(hamburger) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    })

                // Tabs for Busy and Active
                TabViewWithAnimation(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .height(56.dp)
                        .constrainAs(tabs) {
                            top.linkTo(parent.top)
                            start.linkTo(hamburger.end)
                            end.linkTo(speed.start)
                        },
                    isActiveTabOpen = {
                        viewModel.sendEvent(HomeEvent.SwitchTab(it))
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
                AnimatedVisibility(
                    visible = !isBottomSheetExpanded.value,
                    enter = slideInHorizontally(
                        initialOffsetX = { it / 2 - 16 },
                        animationSpec = tween(durationMillis = 500)
                    ) + fadeIn(animationSpec = tween(durationMillis = 500)),
                    exit = slideOutHorizontally(
                        targetOffsetX = { it / 2 - 16 },
                        animationSpec = tween(durationMillis = 500)
                    ) + fadeOut(animationSpec = tween(durationMillis = 500)),
                    modifier = Modifier
                        .constrainAs(upArrow) {
                            start.linkTo(parent.start)
                            bottom.linkTo(map.bottom)
                            top.linkTo(map.top)
                        }
                ) {
                    MiddleUpArrowIcon(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .size(56.dp)
                    )
                }

                // Plus, Minus, Navigator (Middle-Right)
                AnimatedVisibility(
                    visible = !isBottomSheetExpanded.value,
                    enter = slideInHorizontally(animationSpec = tween(durationMillis = 500))
                            + fadeIn(animationSpec = tween(durationMillis = 500)),
                    exit = slideOutHorizontally(
                        targetOffsetX = { -it / 2 + 16 },
                        animationSpec = tween(durationMillis = 500)
                    ) + fadeOut(animationSpec = tween(durationMillis = 500)),
                    modifier = Modifier
                        .constrainAs(plusMinusNav) {
                            end.linkTo(parent.end)
                            top.linkTo(upArrow.top)
                        }
                ) {
                    MiddleRightIcons(modifier = Modifier
                        .padding(end = 16.dp)
                        .width(56.dp),
                        onZoomIn = {
                            if (mapZoomLevel.value < 22) {
                                mapZoomLevel.value += 1.0
                            }
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

@Composable
fun SetSystemBarColors() {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()

    systemUiController.setStatusBarColor(
        color = MaterialTheme.colorScheme.primary,
        darkIcons = useDarkIcons
    )

    systemUiController.setNavigationBarColor(
        color = MaterialTheme.colorScheme.primary,
        darkIcons = useDarkIcons
    )
}

fun setMapStyle(mapView: MapView, isDarkTheme: Boolean) {
    val styleUri = if (isDarkTheme) {
        Style.DARK
    } else {
        Style.MAPBOX_STREETS
    }

    mapView.mapboxMap.loadStyle(styleUri)
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

private fun toggleMapControllerVisibility(
    isExpanded: MutableState<Boolean>
) {
    isExpanded.value = !isExpanded.value
}
