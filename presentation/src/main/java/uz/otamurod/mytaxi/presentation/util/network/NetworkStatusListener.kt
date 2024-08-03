package uz.otamurod.mytaxi.presentation.util.network

interface NetworkStatusListener {
    fun onNetworkAvailable()
    fun onNetworkLost()
}