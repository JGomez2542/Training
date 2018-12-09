package com.example.jasongomez.contentproviders.ui

import android.content.ContentValues
import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.view.View
import android.widget.Toast
import com.example.jasongomez.contentproviders.R
import com.example.jasongomez.contentproviders.common.URL_LOADER
import com.example.jasongomez.contentproviders.providers.MyContentProviderContract
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportLoaderManager.initLoader(URL_LOADER, null, this)
    }

    override fun onCreateLoader(loaderId: Int, bundle: Bundle?): Loader<Cursor> {
        return when (loaderId) {
            URL_LOADER -> CursorLoader(
                this, //Context
                MyContentProviderContract.MyContentProviderEntry.CONTENT_URI, //Table to query
                null, //Projection to return
                null, //Selection Clause
                null, // Selection Arguments
                null //Sort order
            )
            else -> throw NullPointerException()
        }
    }

    /**
     * When the query is done, the background framework calls this method.
     */

    override fun onLoadFinished(loader: Loader<Cursor>, cursor: Cursor?) {
        Toast.makeText(this, "Load Done", Toast.LENGTH_SHORT).show()
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
    }

    fun insertContact(view: View) {
        val contentValues = ContentValues()
        contentValues.put(
            MyContentProviderContract.MyContentProviderEntry.COLUMN_CONTACT_NAME,
            etContactName.text.toString()
        )
        contentValues.put(
            MyContentProviderContract.MyContentProviderEntry.COLUMN_CONTACT_RELATIONSHIP,
            etContactRelationship.text.toString()
        )
        contentValues.put(
            MyContentProviderContract.MyContentProviderEntry.COLUMN_CONTACT_PRIMARY_NUMBER,
            etContactPrimaryNumber.text.toString()
        )
        contentValues.put(
            MyContentProviderContract.MyContentProviderEntry.COLUMN_CONTACT_SECONDARY_NUMBER,
            etContactSecondaryNumber.text.toString()
        )
        contentResolver.insert(MyContentProviderContract.MyContentProviderEntry.CONTENT_URI, contentValues)
    }
}
