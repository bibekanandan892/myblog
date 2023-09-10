package com.popshop.myblog.data

import com.popshop.myblog.models.Post
import com.popshop.myblog.models.PostWithoutDetails
import com.popshop.myblog.models.User

interface MongoRepository {
    suspend fun addPost(post: Post): Boolean
    suspend fun updatePost(post: Post): Boolean

    suspend fun readMyPosts(skip: Int, author: String): List<PostWithoutDetails>

    suspend fun readSelectedPost(id: String): Post
    suspend fun searchPostsByTittle(query: String, skip: Int): List<PostWithoutDetails>
    suspend fun deleteSelectedPosts(ids: List<String>): Boolean

    suspend fun checkUserExistence(user: User): User?
    suspend fun checkUserId(id: String): Boolean

}