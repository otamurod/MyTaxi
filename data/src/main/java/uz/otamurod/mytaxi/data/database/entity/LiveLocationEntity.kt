package uz.otamurod.mytaxi.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "live_location")
data class LiveLocationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val latitude: Double,
    val longitude: Double,
    val timestamp: String
)
