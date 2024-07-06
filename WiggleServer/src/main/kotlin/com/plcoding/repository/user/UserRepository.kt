package com.plcoding.repository.user

import com.plcoding.data.models.User
import com.plcoding.data.requests.UpdateProfileReq

interface UserRepository {
    suspend fun createUser(user: User)
    suspend fun getUserById(id: String): User?
    suspend fun getUserByEmail(email: String): User?
    suspend fun doesPasswordForUserMatch(email:String,enteredPassword: String):Boolean
    suspend fun doesEmailBelongToUserId(email:String, userId: String) : Boolean
    suspend fun searchForUsers(query: String) : List<User>
    suspend fun updateUser(userId: String,updateProfileReq: UpdateProfileReq,bannerUrl: String?,profileImageUrl: String?) :Boolean
   suspend fun getUsers(userIds: List<String>) : List<User>
}