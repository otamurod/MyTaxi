package uz.otamurod.mytaxi.domain.util

sealed class DataState<out D> {
    data class Success<out D>(val data: D) : DataState<D>()
    data class Error(val error: String) : DataState<Nothing>()
    object Loading : DataState<Nothing>()

    fun isSuccessful() = this is Success
    fun hasFailed() = this is Error
    fun isLoading() = this is Loading

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$error]"
            Loading -> "Loading"
        }
    }
}