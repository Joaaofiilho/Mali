package com.joaoferreira.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.joaoferreira.data.models.MarketItemModel

@Database(entities = [MarketItemModel::class], version = 1)
abstract class MaliDatabase: RoomDatabase() {
    abstract fun marketItemsDao(): MarketItemsDao

    companion object {
        @Volatile
        private var INSTANCE: MaliDatabase? = null

        fun getDatabase(context: Context): MaliDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    MaliDatabase::class.java,
                    "mali_database")
                    .createFromAsset("database/mali_database.db")
                    .build()

                INSTANCE = instance

                instance
            }
        }
    }
}