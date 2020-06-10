package com.example.real_estate_manager.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import androidx.annotation.WorkerThread
import com.example.real_estate_manager.Constants
import com.example.real_estate_manager.room.database.HouseDatabase
import com.example.real_estate_manager.room.model.*

class MyContentProvider : ContentProvider() {
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw NotImplementedError()
    }

    companion object {

        val HOUSE_TABLE = House::class.java.simpleName
        val REAL_ESTATE_AGENT_TABLE = RealEstateAgent::class.java.simpleName
        val TYPE_TABLE = Type::class.java.simpleName
        val INTEREST_POINTS_TABLE = InterestPoints::class.java.simpleName
        val PICTURES_TABLE = Pictures::class.java.simpleName

        val URI_HOUSE: Uri = Uri.parse("content://${Constants.AUTHORITY}/$HOUSE_TABLE")
        val URI_REAL_ESTATE_AGENT: Uri =
            Uri.parse("content://${Constants.AUTHORITY}/$REAL_ESTATE_AGENT_TABLE")
        val URI_TYPE: Uri = Uri.parse("content://${Constants.AUTHORITY}/$TYPE_TABLE")
        val URI_INTEREST_POINTS: Uri =
            Uri.parse("content://${Constants.AUTHORITY}/$INTEREST_POINTS_TABLE")
        val URI_PICTURES: Uri = Uri.parse("content://${Constants.AUTHORITY}/$PICTURES_TABLE")

        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        const val HOUSE_ID = "houseId"
    }


    override fun onCreate(): Boolean {
        this.initializeUriMatching()
        return true
    }

    private fun initializeUriMatching() {
        uriMatcher.addURI(Constants.AUTHORITY, HOUSE_TABLE, Constants.ALL_PROPERTIES)
        uriMatcher.addURI(
            Constants.AUTHORITY,
            REAL_ESTATE_AGENT_TABLE,
            Constants.ALL_REAL_ESTATE_AGENTS
        )
        uriMatcher.addURI(Constants.AUTHORITY, TYPE_TABLE, Constants.ALL_TYPE)
        uriMatcher.addURI(Constants.AUTHORITY, INTEREST_POINTS_TABLE, Constants.ALL_INTEREST_POINTS)
        uriMatcher.addURI(Constants.AUTHORITY, PICTURES_TABLE, Constants.ALL_PICTURES)
    }

    @WorkerThread
    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        context?.let {
            // Database
            val getDatabaseInstance = HouseDatabase.getInstance(it)

            val stringBuilder = StringBuilder("SELECT ")
            if (projection.isNullOrEmpty()) {
                stringBuilder.append("* ")
            } else {
                stringBuilder.append(projection.joinToString(", "))
            }
            stringBuilder.append(" FROM ${uri.lastPathSegment}")
            if (!selection.isNullOrEmpty()) {
                stringBuilder.append(" WHERE ${selection.replace("?", selectionArgs?.map {  })}")
            }
            if (!sortOrder.isNullOrEmpty()) {
                stringBuilder.append(" ORDER BY $sortOrder")
            }

            return getDatabaseInstance?.contentProviderQuery(stringBuilder.toString())
        }
        throw IllegalArgumentException("Failed to query row for uri $uri")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        throw NotImplementedError()
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        throw NotImplementedError()
    }
}