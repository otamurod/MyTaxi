package uz.otamurod.mytaxi.presentation.ui

import android.Manifest
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.androidx.compose.koinViewModel
import uz.otamurod.mytaxi.domain.model.LiveLocation
import uz.otamurod.mytaxi.presentation.R
import uz.otamurod.mytaxi.presentation.android.service.LocationForegroundService
import uz.otamurod.mytaxi.presentation.ui.home.HomeScreen
import uz.otamurod.mytaxi.presentation.ui.home.HomeScreenReducer.HomeEvent.UpdateUserLocation
import uz.otamurod.mytaxi.presentation.ui.home.HomeViewModel
import uz.otamurod.mytaxi.presentation.ui.theme.MyTaxiTheme

class MainActivity : ComponentActivity() {
    private var locationForegroundService: LocationForegroundService? = null

    private var serviceBoundState by mutableStateOf(false)
    private lateinit var viewModel: HomeViewModel

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            Log.d(TAG, "onServiceConnected")

            val binder = service as LocationForegroundService.LocalBinder
            locationForegroundService = binder.getService()
            serviceBoundState = true

            onServiceConnected()
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            Log.d(TAG, "onServiceDisconnected")

            serviceBoundState = false
            locationForegroundService = null
        }
    }

    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {}

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(android.Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted, service can run
                startForegroundService()
            }

            permissions.getOrDefault(android.Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Only approximate location access granted, service can still run.
                startForegroundService()
            }

            else -> {
                // No location access granted, service can't be started as it will crash
                Toast.makeText(
                    this,
                    getString(R.string.location_permission_is_required), Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            viewModel = koinViewModel()

            MyTaxiTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(modifier = Modifier.padding(innerPadding)) {
                        HomeScreen(
                            viewModel = viewModel,
                            onMapReady = { isReady ->
                                if (isReady) {
                                    startForegroundServiceOnMapReady()
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    private fun checkAndRequestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when (ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.POST_NOTIFICATIONS
            )) {
                PackageManager.PERMISSION_GRANTED -> {}

                else -> {
                    notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }

    private fun startForegroundServiceOnMapReady() {
        val fineLocationGranted = ContextCompat.checkSelfPermission(
            this, android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val coarseLocationGranted = ContextCompat.checkSelfPermission(
            this, android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (fineLocationGranted || coarseLocationGranted) {
            startForegroundService()
        } else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun startForegroundService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this, android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                checkAndRequestNotificationPermission()
            }
        }

        val serviceIntent = Intent(this, LocationForegroundService::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent)
        } else {
            startService(serviceIntent)
        }
        tryToBindToServiceIfRunning()
    }

    private fun tryToBindToServiceIfRunning() {
        val serviceIntent = Intent(this, LocationForegroundService::class.java)
        bindService(serviceIntent, serviceConnection, 0)
    }

    private fun onServiceConnected() {
        lifecycleScope.launch {
            locationForegroundService?.locationFlow?.collectLatest {

                it?.let {
                    val time = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                    val liveLocation = LiveLocation(
                        latitude = it.latitude,
                        longitude = it.longitude,
                        timestamp = time.toString()
                    )
                    viewModel.sendEvent(UpdateUserLocation(liveLocation))
                }
                Log.d(TAG, "onServiceConnected: $it")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(serviceConnection)
        locationForegroundService?.stopForegroundService()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}