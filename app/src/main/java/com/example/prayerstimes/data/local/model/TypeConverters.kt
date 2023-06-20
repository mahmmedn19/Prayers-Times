package com.example.prayerstimes.data.local.model
import androidx.room.TypeConverter
import com.example.prayerstimes.domain.model.DateEntity
import com.example.prayerstimes.domain.model.TimingsEntity
import com.google.gson.Gson

object TypeConvertersDateTime {
    private val gson = Gson()

    @TypeConverter
    fun fromDateEntity(date: DateEntity): String {
        return gson.toJson(date)
    }

    @TypeConverter
    fun toDateEntity(json: String): DateEntity {
        return gson.fromJson(json, DateEntity::class.java)
    }

    @TypeConverter
    fun fromTimingsEntity(timings: TimingsEntity): String {
        return gson.toJson(timings)
    }

    @TypeConverter
    fun toTimingsEntity(json: String): TimingsEntity {
        return gson.fromJson(json, TimingsEntity::class.java)
    }
}
