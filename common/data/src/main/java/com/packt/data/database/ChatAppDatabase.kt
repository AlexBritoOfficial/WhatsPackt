package com.packt.data.database

import com.packt.data.database.AppUser
import com.packt.data.database.AppUserDao
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        Conversation::class,
        Message::class,
        AppUser::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ChatAppDatabase : RoomDatabase() {

    abstract fun chatDao(): ConversationDao
    abstract fun messageDao(): MessageDao
    abstract fun appUserDao(): AppUserDao

    companion object {
        @Volatile
        private var INSTANCE: ChatAppDatabase? = null

        fun getDatabase(context: Context): ChatAppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ChatAppDatabase::class.java,
                    "chat_app_database"
                ).fallbackToDestructiveMigration(dropAllTables = true) // 👈 THIS DROPS AND REBUILDS THE DB
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
