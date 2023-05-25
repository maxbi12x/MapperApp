package com.example.mapperapp.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mapperapp.models.ImageDetailsModel
import com.example.mapperapp.models.MarkerModel
import com.example.mapperapp.roomDB.dao.ImageDetailsDao
import com.example.mapperapp.roomDB.dao.MarkerDao
import com.example.mapperapp.roomDB.typeConverter.UriTypeConverter

@Database(
    entities = [ImageDetailsModel::class,MarkerModel::class],
    version = 3)
@TypeConverters(value = [UriTypeConverter::class])
abstract class AppDatabase : RoomDatabase() {
    public abstract fun imageDetailsDao() : ImageDetailsDao
    public abstract fun markerDao() : MarkerDao

   companion object{
       private var instance: AppDatabase? = null
       @Synchronized
       fun getInstance(context: Context): AppDatabase {
           if (instance == null) {
               instance = databaseBuilder(
                   context.applicationContext,
                   AppDatabase::class.java, "app_database"
               )
                   .fallbackToDestructiveMigration()
                   .build()
           }
           return instance!!
       }
   }
}