package com.example.jasongomez.contentproviders.providers

import android.arch.persistence.room.Room
import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.jasongomez.contentproviders.common.DATABASE_NAME
import com.example.jasongomez.contentproviders.common.TABLE_NAME
import com.example.jasongomez.contentproviders.data.database.ContactsDatabase
import com.example.jasongomez.contentproviders.data.database.entities.Contact

class MyContentProvider : ContentProvider() {

    val CONTACTS_URI_CODE = 10
    private lateinit var contactsDatabase: ContactsDatabase
    private lateinit var uriMatcher: UriMatcher

    override fun onCreate(): Boolean {
        contactsDatabase = Room.databaseBuilder(context, ContactsDatabase::class.java, DATABASE_NAME).build()
        uriMatcher = createUriMatcher()
        return true
    }

    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {
        when (uriMatcher.match(uri)) {
            CONTACTS_URI_CODE -> {
                contentValues?.let {
                    Thread {
                        contactsDatabase.contactsDao().insertContact(
                            Contact(
                                contactName = it["contactName"] as String,
                                contactRelationship = it["contactRelationship"] as String,
                                contactPrimaryNumber = it["contactPrimaryNumber"] as String,
                                contactSecondaryNumber = it["contactSecondaryNumber"] as String
                            )
                        )
                    }
                    context.contentResolver.notifyChange(uri, null);
                }
            }
        }
        return MyContentProviderContract.BASE_CONTENT_URI
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? = contactsDatabase.contactsDao().getCursorContacts()


    override fun update(uri: Uri, contentValues: ContentValues?, s: String?, strings: Array<String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(uri: Uri, s: String?, strings: Array<String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getType(uri: Uri): String? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun createUriMatcher(): UriMatcher {
        val matcher = UriMatcher(UriMatcher.NO_MATCH)
        val authority = MyContentProviderContract.CONTENT_AUTHORITY

        matcher.addURI(authority, TABLE_NAME, CONTACTS_URI_CODE)
        return matcher
    }
}