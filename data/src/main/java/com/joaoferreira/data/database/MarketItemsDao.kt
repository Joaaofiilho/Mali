package com.joaoferreira.data.database

import androidx.room.*
import com.joaoferreira.data.models.MarketItemModel
import com.joaoferreira.domain.models.MarketItem
import kotlinx.coroutines.flow.Flow

@Dao
interface MarketItemsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(marketItem: MarketItemModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(marketItems: List<MarketItemModel>)

    @Query("SELECT * FROM market_items")
    fun getAll(): Flow<List<MarketItemModel>>

    @Query("SELECT * FROM market_items WHERE is_done=1")
    fun getAllDone(): Flow<List<MarketItemModel>>

    @Query("SELECT * FROM market_items WHERE id=:id")
    fun getById(id: String): Flow<MarketItemModel>

    @Update
    suspend fun update(marketItem: MarketItemModel)

    @Query("DELETE FROM market_items WHERE id=:id")
    suspend fun deleteById(id: String)

    @Query("DELETE FROM market_items WHERE is_done=1")
    suspend fun deleteAllDone()
}