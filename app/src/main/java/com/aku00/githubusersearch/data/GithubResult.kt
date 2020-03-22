package com.aku00.githubusersearch.data

import io.reactivex.Single

data class GithubResult (
    var total_count: Int,
    var incomplete_results: Boolean,
    var items: MutableList<GithubUserInfo>
)