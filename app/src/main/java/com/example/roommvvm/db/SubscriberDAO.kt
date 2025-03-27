package com.example.roommvvm.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface SubscriberDAO {

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    @Insert(onConflict = OnConflictStrategy.IGNORE)

    @Insert
    suspend fun insertSubscriber(subscriber : Subscriber) : Long

    @Update
    suspend fun updateSubscriber(subscriber : Subscriber)

    @Delete
    suspend fun deleteSubscriber(subscriber : Subscriber)

    @Query("Delete from subscriber_data_table")
    suspend fun deleteAll()

    @Query("Select * from subscriber_data_table")
    fun getAllSubscribers() : LiveData<List<Subscriber>>
}