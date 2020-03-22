package com.aku00.githubusersearch.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aku00.githubusersearch.data.GithubUserInfo

@Database(entities = [GithubUserInfo::class], version = 1)
abstract class GithubRoomDB : RoomDatabase() {
    abstract fun githubUserDao(): GithubUserInfoDao
    companion object {
        private var instance: GithubRoomDB? = null
        fun getInstance(application: Application) =
                instance ?: synchronized(this) {
                    instance ?: Room.databaseBuilder(application,
                            GithubRoomDB::class.java, "users.db").build()
                }
    }
}