package com.joaoferreira.data.services

import com.joaoferreira.data.models.MarketItemModel
import retrofit2.http.*

interface MaliApi {
    @POST("create")
    suspend fun create(@Body marketItemModel: MarketItemModel)

    @GET("all")
    suspend fun getAll(): List<MarketItemModel>

    @GET("item/{id}")
    suspend fun getById(@Path("id") id: String): MarketItemModel

    @PUT("update/{id}")
    suspend fun update(@Path("id") id: String, @Body marketItemModel: MarketItemModel)

    @DELETE("delete/{id}")
    suspend fun deleteById(@Path("id") id: String)

    @DELETE("deletedone")
    suspend fun deleteAllDone()
}