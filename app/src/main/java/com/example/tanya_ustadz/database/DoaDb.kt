package com.example.tanya_ustadz.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tanya_ustadz.model.Doa

@Database(entities = [Doa::class], version = 2, exportSchema = false)
abstract class DoaDb : RoomDatabase() {

    abstract val dao:DoaDao

    companion object{
        @Volatile
        private var INSTANCE: DoaDb? = null

        fun getInstance(context: Context): DoaDb{
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DoaDb::class.java,
                        "doa.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}