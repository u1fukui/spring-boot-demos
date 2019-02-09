package com.u1fukui.springbootdemos.redis

import java.io.Serializable

// シリアライズ時に、NON_FINAL なオブジェクトにだけ型情報を書くようにしているので open を付けている
// 型情報を書かないと、デシリアライズ時に型が分からなくてエラーになってしまう
open class RepositorySearchResult(
        val totalCount: Int,
        val incompleteResults: Boolean,
        val items: List<GitHubRepository>
) : Serializable

open class GitHubRepository(
        val id: Long,
        val name: String
) : Serializable