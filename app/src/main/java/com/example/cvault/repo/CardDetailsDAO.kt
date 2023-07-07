package com.example.cvault.repo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.cvault.bo.CardDetails

@Dao
interface CardDetailsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(article : CardDetails)

    @Query("SELECT * FROM table_card_details ORDER BY name ASC")
    fun loadCardDetails() : List<CardDetails>?

    @Update
    suspend fun updateCardDetails(article: CardDetails)

}