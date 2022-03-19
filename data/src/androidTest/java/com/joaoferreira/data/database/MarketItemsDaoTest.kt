package com.joaoferreira.data.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.joaoferreira.data.models.MarketItemModel
import com.joaoferreira.domain.utils.Categories
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MarketItemsDaoTest {
    private lateinit var marketItemsDao: MarketItemsDao
    private lateinit var db: MaliDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MaliDatabase::class.java
        ).build()
        marketItemsDao = db.marketItemsDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertItemAndRead() {
        val id = "unique"
        val insertedItem = MarketItemModel(id, "Bread", 1, false, Categories.FOOD)
        runBlocking {
            marketItemsDao.insert(insertedItem)
            val getItem = marketItemsDao.getById(id).first()
            assertThat(getItem, equalTo(insertedItem))
        }
    }

    @Test
    @Throws(Exception::class)
    fun insertAllItemsAndReadAll() {
        val insertedItems = mutableListOf<MarketItemModel>()
        (0..5).forEach {
            val item = MarketItemModel(it.toString(), "Item", 2, false, Categories.BEAUTY)
            insertedItems.add(item)
        }
        runBlocking {
            marketItemsDao.insertAll(insertedItems)
            val getItems = marketItemsDao.getAll().first()
            assertThat(getItems, equalTo(insertedItems))
        }
    }

    @Test
    @Throws(Exception::class)
    fun readAllDoneItems() {
        val insertedItems = mutableListOf<MarketItemModel>()
        (0..5).forEach {
            //It is only done on even numbers
            val item = MarketItemModel(it.toString(), "Item", 2, it % 2 == 0, Categories.BEAUTY)
            insertedItems.add(item)
        }

        val onlyDoneItems = insertedItems.filter { it.isDone }

        runBlocking {
            marketItemsDao.insertAll(insertedItems)
            val getItems = marketItemsDao.getAllDone().first()
            assertThat(getItems, equalTo(onlyDoneItems))
        }
    }

    @Test
    @Throws(Exception::class)
    fun updateItem() {
        val insertedItem = MarketItemModel("unique", "Item", 2, false, Categories.BEAUTY)

        runBlocking {
            marketItemsDao.insert(insertedItem)
            val updatedItem = insertedItem.copy(title="Rodo", quantity = 4, category = Categories.CLEANING)
            marketItemsDao.update(updatedItem)
            val getItem = marketItemsDao.getById(insertedItem.id).first()
            assertThat(getItem, equalTo(updatedItem))
        }
    }

    @Test
    @Throws(Exception::class)
    fun deleteItem() {
        val insertedItem = MarketItemModel("unique", "Item", 2, false, Categories.BEAUTY)

        runBlocking {
            marketItemsDao.insert(insertedItem)
            val getItem = marketItemsDao.getById(insertedItem.id).first()
            assertThat(getItem, equalTo(insertedItem))
            marketItemsDao.deleteById(insertedItem.id)

            val deletedItem = marketItemsDao.getById(insertedItem.id).first()
            assert(deletedItem == null)
        }
    }

    @Test
    @Throws(Exception::class)
    fun deleteAllDoneItems() {
        val insertedItems = mutableListOf<MarketItemModel>()
        (0..5).forEach {
            //It is only done on even numbers
            val item = MarketItemModel(it.toString(), "Item", 2, it % 2 == 0, Categories.BEAUTY)
            insertedItems.add(item)
        }

        val onlyNotDoneItems = insertedItems.filter { !it.isDone }

        runBlocking {
            marketItemsDao.insertAll(insertedItems)
            marketItemsDao.deleteAllDone()

            val getItems = marketItemsDao.getAll().first()
            assertThat(getItems, equalTo(onlyNotDoneItems))
        }
    }
}