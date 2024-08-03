package uz.otamurod.mytaxi.data.database.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.otamurod.mytaxi.data.database.dao.LiveLocationDao
import uz.otamurod.mytaxi.data.database.entity.LiveLocationEntity

@Database(
    entities = [LiveLocationEntity::class],
    version = 1
)
abstract class LiveLocationDatabase : RoomDatabase() {

    abstract fun liveLocationDao(): LiveLocationDao

    companion object {
        private var INSTANCE: LiveLocationDatabase? = null

        @Synchronized
        fun getInstance(context: Context): LiveLocationDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context, LiveLocationDatabase::class.java, "Live_Location_DB.db"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}