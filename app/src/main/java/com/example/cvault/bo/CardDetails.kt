package com.example.cvault.bo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_card_details")
data class CardDetails(
    @PrimaryKey
    val name: String,
    /*val companyName: String?,
    val contactNumber: String?,
    val mailID: String?,*/
    val imageURL : String?
)