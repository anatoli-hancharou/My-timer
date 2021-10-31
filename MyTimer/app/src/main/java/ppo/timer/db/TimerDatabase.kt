package ppo.timer.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ppo.timer.R

@Database(entities = [TimerEntity::class], version = 2, exportSchema = false)
abstract class TimerDatabase : RoomDatabase() {

    abstract fun timerDao(): TimerDao

    companion object {
        private var instance: TimerDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): TimerDatabase {
            if(instance == null)
                instance = Room.databaseBuilder(context.applicationContext, TimerDatabase::class.java,
                    "timers.db")
                    .build()

            return instance!!
        }
    }
}
