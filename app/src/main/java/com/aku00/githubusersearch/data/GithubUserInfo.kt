package com.aku00.githubusersearch.data

import androidx.room.Entity

@Entity(primaryKeys = ["id"])
data class GithubUserInfo (
        var login: String,
        var id: Int,
        var avatar_url: String) {

    var isFavorite: Boolean = false
}