package com.example.localuser.retrofittest.aissetest.db

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

/**
 * 聊天消息实体（Room 持久化）
 */
@Entity(tableName = "chat_messages")
data class MessageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val role: String,             // "user" / "assistant"
    val content: String,
    val completionId: String,     // 每轮对话的唯一 ID
    val createTime: Long
)

@Dao
interface MessageDao {

    @Insert
    suspend fun insert(message: MessageEntity)

    @Query("SELECT * FROM chat_messages ORDER BY createTime ASC")
    fun getAllMessages(): Flow<List<MessageEntity>>

    @Query("DELETE FROM chat_messages")
    suspend fun deleteAll()
}

@Database(entities = [MessageEntity::class], version = 1, exportSchema = false)
abstract class ChatDatabase : RoomDatabase() {

    abstract fun messageDao(): MessageDao

    companion object {
        @Volatile
        private var INSTANCE: ChatDatabase? = null

        fun getInstance(context: Context): ChatDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    ChatDatabase::class.java,
                    "chat_db"
                ).build().also { INSTANCE = it }
            }
    }
}
