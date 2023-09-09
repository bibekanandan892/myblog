package com.popshop.myblog.data

import com.popshop.myblog.models.Post
import com.popshop.myblog.models.User

interface MongoRepository {
    suspend fun addPost(post: Post): Boolean

    suspend fun checkUserExistence(user: User): User?
    suspend fun checkUserId(id: String): Boolean

}