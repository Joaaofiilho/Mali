package com.joaoferreira.data.database

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.joaoferreira.domain.models.MarketItem
import kotlinx.coroutines.flow.Flow

interface MarketItemsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(marketItem: MarketItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(marketItems: List<MarketItem>)

    @Query("SELECT * FROM market_items")
    suspend fun getAll(): Flow<List<MarketItem>>

    @Query("SELECT * FROM market_items WHERE is_done=1")
    suspend fun getAllDone(): Flow<List<MarketItem>>

    @Query("SELECT * FROM market_items WHERE id=:id")
    suspend fun getById(id: String): Flow<MarketItem>

    @Update
    suspend fun update(marketItem: MarketItem)

    @Query("DELETE FROM market_items WHERE id=:id")
    suspend fun deleteById(id: String)

    @Query("DELETE FROM market_items WHERE is_done=1")
    suspend fun deleteAllDone()
}