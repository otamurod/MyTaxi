package uz.otamurod.mytaxi.presentation.util.network

import android.content.Context
import android.location.LocationManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext

@Composable
fun observeGPSState(): Boolean {
    val context = LocalContext.current
    return rememberUpdatedState(
        isGPSEnabled(context)
    ).value
}

fun isGPSEnabled(context: Context): Boolean {
    val locationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
}
