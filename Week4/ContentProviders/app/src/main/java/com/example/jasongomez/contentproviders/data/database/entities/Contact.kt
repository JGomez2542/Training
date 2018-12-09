package com.example.jasongomez.contentproviders.data.database.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import com.example.jasongomez.contentproviders.common.TABLE_NAME

@Entity(tableName = TABLE_NAME, indices = [Index("contactName", unique = true)])
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val contactId: Int? = null,
    val contactName: String,
    val contactRelationship: String,
    val contactPrimaryNumber: String,
    val contactSecondaryNumber: String
)