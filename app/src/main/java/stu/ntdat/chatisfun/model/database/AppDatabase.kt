package stu.ntdat.chatisfun.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import stu.ntdat.chatisfun.model.database.dao.*
import stu.ntdat.chatisfun.model.*
import stu.ntdat.chatisfun.model.relation.ConvMessRel
import stu.ntdat.chatisfun.model.relation.UserConvRel

@Database(
    entities = [ChatConversation::class, ChatUser::class, ChatMessage::class, ConvMessRel::class, UserConvRel::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val chatConversationDao: ChatConversationDao
    abstract val chatMessageDao: ChatMessageDao
    abstract val chatUserDao: ChatUserDao
    abstract val convMessRelDao: ConvMessRelDao
    abstract val userConvRelDao: UserConvRelDao

    companion object {
        @Volatile
        private lateinit var instance: AppDatabase

        fun getInstance(context: Context): AppDatabase {
            if (::instance.isInitialized.not()) {
                instance =
                    Room.databaseBuilder(context,AppDatabase::class.java,"hello_there").build()
            }
            return instance
        }
    }
}