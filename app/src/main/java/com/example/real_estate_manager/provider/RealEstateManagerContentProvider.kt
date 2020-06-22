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

class RealEstateManagerContentProvider : ContentProvider() {
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
        const val HOUSE_PRICE = "price"
        const val HOUSE_SURFACE = "surface"
        const val HOUSE_NUMBER_OF_ROOM = "roomNumber"
        const val HOUSE_DESCRIPTION = "description"
        const val HOUSE_LOCATION = "location"
        const val HOUSE_LATITUDE = "latitude"
        const val HOUSE_LONGITUDE = "longitude"
        const val HOUSE_ENTRY_DATE = "entryDate"
        const val HOUSE_SOLD_DATE = "soldDate"

        const val REAL_ESTATE_AGENT_ID = "agentId"
        const val REAL_ESTATE_AGENT = "realEstateAgent"

        const val PICTURES_ID = "picturesId"
        const val PICTURES = "pictures"
        const val PICTURES_TEXT = "pictureText"

        const val INTEREST_POINTS_ID = "interestId"
        const val INTEREST_POINTS = "interestPoints"

        const val TYPE_ID = "typeId"
        const val TYPE_ = "type"
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
                var bindedSelection = selection
                selectionArgs?.forEach {
                    bindedSelection = bindedSelection?.replaceFirst("?", it)
                }
                stringBuilder.append(" WHERE $bindedSelection")
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