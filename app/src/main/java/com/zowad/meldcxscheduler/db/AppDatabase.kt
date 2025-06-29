package com.zowad.meldcxscheduler.db

import android.graphics.drawable.Drawable
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Entity(tableName = "schedules")
@Serializable
@Parcelize
data class ScheduleItem(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "name") var scheduleName: String,
    @ColumnInfo(name = "package_name") var schedulePackageName: String,
    @ColumnInfo(name = "schedule_time_millis") var scheduleTimeMillis: Long,
    @ColumnInfo(name = "schedule_hour") var scheduleHour: Int,
    @ColumnInfo(name = "schedule_minute") var scheduleMinute: Int,
): Parcelable

fun ScheduleItem.toScheduleLog(scheduleFireTimeMillis: Long) =
    ScheduleLog(
        id = 0,
        previousScheduleId = id,
        scheduleName = scheduleName,
        schedulePackageName = schedulePackageName,
        scheduleTimeMillis = scheduleTimeMillis,
        scheduleFireTimeMillis = scheduleFireTimeMillis
    )

@Entity(tableName = "schedule_logs")
data class ScheduleLog(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "previous_schedule_id") var previousScheduleId: Int,
    @ColumnInfo(name = "name") var scheduleName: String,
    @ColumnInfo(name = "package_name") var schedulePackageName: String,
    @ColumnInfo(name = "schedule_time_millis") var scheduleTimeMillis: Long,
    @ColumnInfo(name = "schedule_fire_time_millis") var scheduleFireTimeMillis: Long
)

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM schedules")
    fun getAll(): Flow<MutableList<ScheduleItem>>

    @Query("SELECT * FROM schedules")
    suspend fun getAllSync(): List<ScheduleItem>

    @Query("SELECT * FROM schedule_logs ORDER BY schedule_fire_time_millis DESC")
    fun getLogs(): Flow<List<ScheduleLog>>

    @Query("SELECT * FROM schedules WHERE id=:id")
    suspend fun getScheduleItem(id: Int): ScheduleItem?

    @Query("SELECT * FROM schedules WHERE schedule_time_millis>=:timeInMillis")
    suspend fun getAllSync(timeInMillis: Long): List<ScheduleItem>

    @Insert/*suspend*/ fun insertAll(vararg schedules: ScheduleItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSchedule(schedule: ScheduleItem): Long

    @Delete
    suspend fun delete(schedule: ScheduleItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveScheduleLog(schedule: ScheduleLog): Long


    @Delete/*suspend*/ fun deleteSchedule(schedule: ScheduleItem)
}

@Database(entities = [ScheduleItem::class, ScheduleLog::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scheduleItemDao(): ScheduleDao
}