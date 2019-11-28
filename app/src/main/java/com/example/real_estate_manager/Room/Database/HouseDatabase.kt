package com.example.real_estate_manager.Room.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.real_estate_manager.Room.Dao.*
import com.example.real_estate_manager.Room.Model.*

@Database(entities = [House::class, InterestPoints::class, Pictures::class, RealEstateAgent::class, Type::class], version = 1)
abstract class HouseDatabase : RoomDatabase() {
    abstract fun housedao(): HouseDao
    abstract fun interestPointsDao(): InterestPointsDao
    abstract fun picturesDao() : PicturesDao
    abstract fun realEstateAgentDao() : RealEstateAgentDao
    abstract fun typeDao(): TypeDao

    companion object {
        var INSTANCE: HouseDatabase? = null

        fun getHouseDatabase(context: Context): HouseDatabase? {
            if(INSTANCE == null){
                synchronized(HouseDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, HouseDatabase::class.java, "houseDatabase").build()
                }
            }
            return INSTANCE
        }
    }
}