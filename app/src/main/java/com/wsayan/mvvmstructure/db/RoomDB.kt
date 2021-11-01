package com.wsayan.mvvmstructure.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wsayan.mvvmstructure.db.dao.CategoryDao
import com.wsayan.mvvmstructure.db.dao.ImageConfigDao
import com.wsayan.mvvmstructure.db.entity.Category
import com.wsayan.mvvmstructure.db.entity.ImagesConfig


@Database(entities = [ImagesConfig::class], version = 1, exportSchema = false)
abstract class RoomDB : RoomDatabase() {

    abstract fun imageConfigDao(): ImageConfigDao
}