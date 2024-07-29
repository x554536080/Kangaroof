package com.kuma.kangaroof.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LeafViewConfig(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "value_1") val value1: Int,
    @ColumnInfo(name = "value_2") val value2: Int,
    @ColumnInfo(name = "value_3") val value3: Int,
    @ColumnInfo(name = "value_4") val value4: Int
)
