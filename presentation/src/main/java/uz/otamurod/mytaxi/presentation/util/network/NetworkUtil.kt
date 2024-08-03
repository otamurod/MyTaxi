package uz.otamurod.mytaxi.presentation.util.network

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build

fun isNetworkConnected(context: Context?, networkStatusListener: NetworkStatusListener?): Boolean {
    val connectivityManager =
        context?.getSystemService(Context.CONNECTIVITY_SERVICE) as (ConnectivityManager)
    val activeNetwork = connectivityManager.activeNetwork
    val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
    val isConnected =
        networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)

    registerNetworkChangeListener(context, networkStatusListener)

    return isConnected
}

@SuppressLint("ObsoleteSdkInt")
private fun registerNetworkChangeListener(
    context: Context?, networkStatusListener: NetworkStatusListener?
) {
    val connectivityManager =
        context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            networkStatusListener?.onNetworkAvailable()
        }

        override fun onLost(network: Network) {
            networkStatusListener?.onNetworkLost()
        }
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    } else {
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(request, networkCallback)
    }
}