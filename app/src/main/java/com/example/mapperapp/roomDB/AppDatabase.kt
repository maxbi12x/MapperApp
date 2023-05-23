package com.example.mapperapp.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mapperapp.models.ImageDetailsModel
import com.example.mapperapp.models.MarkerImagesModel
import com.example.mapperapp.models.MarkerModel
import com.example.mapperapp.roomDB.dao.ImageDetailsDao
import com.example.mapperapp.roomDB.dao.MarkerDao
import com.example.mapperapp.roomDB.dao.MarkerImagesDao
import com.example.mapperapp.roomDB.typeConverter.UriTypeConverter

@Database(
    entities = [ImageDetailsModel::class,MarkerImagesModel::class,MarkerModel::class],
    version = 1)
@TypeConverters(value = [UriTypeConverter::class])
abstract class AppDatabase : RoomDatabase() {
    public abstract fun imageDetailsDao() : ImageDetailsDao
    public abstract fun markerDao() : MarkerDao
    public abstract fun markerImagesDao() : MarkerImagesDao

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