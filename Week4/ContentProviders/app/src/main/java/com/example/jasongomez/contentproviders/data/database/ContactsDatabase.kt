package com.example.jasongomez.contentproviders.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.jasongomez.contentproviders.common.DATABASE_VERSION
import com.example.jasongomez.contentproviders.data.database.dao.ContactsDao
import com.example.jasongomez.contentproviders.data.database.entities.Contact

@Database(entities = [Contact::class], version = DATABASE_VERSION)
abstract class ContactsDatabase : RoomDatabase() {
    abstract fun contactsDao(): ContactsDao
}