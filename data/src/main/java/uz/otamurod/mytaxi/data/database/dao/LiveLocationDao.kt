package uz.otamurod.mytaxi.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.otamurod.mytaxi.data.database.entity.LiveLocationEntity

@Dao
interface LiveLocationDao {
    @Query("SELECT * FROM live_location ORDER BY id DESC LIMIT 1")
    suspend fun getLiveLocation(): LiveLocationEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLiveLocation(liveLocation: LiveLocationEntity)
}