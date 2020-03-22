package com.aku00.githubusersearch.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Single
import com.aku00.githubusersearch.data.GithubUserInfo

@Dao
interface GithubUserInfoDao {

    @Insert
    fun insert(user: GithubUserInfo)

    @Query("SELECT * FROM githubuserinfo")
    fun selectUser(): Single<MutableList<GithubUserInfo>>

    @Query("SELECT * FROM githubuserinfo where login LIKE '%' || :login || '%'")
    fun searchUser(login: String): Single<MutableList<GithubUserInfo>>

    @Query("DELETE FROM githubuserinfo where login = :login")
    fun deleteUser(login: String)

    @Query("DELETE FROM githubuserinfo")
    fun deleteAll()
}
