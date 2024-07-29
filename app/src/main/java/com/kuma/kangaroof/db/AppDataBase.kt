package com.kuma.kangaroof.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kuma.kangaroof.db.entities.LeafViewConfig

@Database(entities = [LeafViewConfig::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun configDao()
}