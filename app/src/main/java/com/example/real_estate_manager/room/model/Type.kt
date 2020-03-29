package com.example.real_estate_manager.room.model

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.IgnoredOnParcel
import org.koin.core.KoinComponent
import org.koin.core.inject

@Entity
data class Type(
    @PrimaryKey(autoGenerate = true) val typeId: Long,
    @ColumnInfo val type: String?
) : KoinComponent {
    @IgnoredOnParcel
    @delegate:Ignore
    private val context: Context by inject()
    override fun toString(): String {
        return context.getString(
            context.resources.getIdentifier(
                type,
                "string",
                context.packageName
            )
        )
    }
}
