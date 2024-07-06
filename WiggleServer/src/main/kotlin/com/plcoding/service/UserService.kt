package com.plcoding.service

import com.plcoding.data.models.User
import com.plcoding.data.requests.AccReq
import com.plcoding.data.requests.UpdateProfileReq
import com.plcoding.repository.follow.FollowRepository
import com.plcoding.repository.user.UserRepository
import com.plcoding.response.ProfileResponse
import com.plcoding.response.UserResponseItem
import org.litote.kmongo.bitwiseAnd

class UserService (
    private val userRepository: UserRepository,
    private val followRepository: FollowRepository
)
{
    suspend fun doesUserWithEmailExist(email:String) : Boolean{
        return userRepository.getUserByEmail(email) !=null
    }
    suspend fun createUser(request: AccReq){
        userRepository.createUser(
            User(
                email= request.email,
                username = request.username,
                password = request.password,
                profileImageUrl = "",
                bannerUrl = "",
                bio=""
            )
        )
    }
    suspend fun getUserProfile(userId: String, callerUserId: String): ProfileResponse? {
        val user = userRepository.getUserById(userId) ?: return null
        return ProfileResponse(
            userId= user.id,
            username = user.username,
            bio = user.bio,
            followerCount = user.followerCount,
            followingCount = user.followingCount,
            postCount = user.postCount,
            profilePictureUrl = user.profileImageUrl,
            isOwnProfile = userId == callerUserId,
            bannerUrl = user.bannerUrl,
            isFollowing = if (userId != callerUserId) {
                followRepository.doesUserFollow(callerUserId, userId)
            } else {
                false
            }
        )
    }
    suspend fun updateUser(userId: String,
                           profileImageUrl: String?,
                           bannerUrl: String?,
                           updateProfileReq: UpdateProfileReq) : Boolean
    {
     return userRepository.updateUser(userId=userId,
         updateProfileReq=updateProfileReq,
         profileImageUrl=profileImageUrl,
         bannerUrl = bannerUrl)
    }
   suspend fun searchForUsers(query: String,userId:String): List<UserResponseItem>{
       val users= userRepository.searchForUsers(query)
       return users.map { user ->
           val followingByUser = followRepository.getFollowsByUser(userId)
           val isFollowing = followingByUser.find { it.followedUserId == user.id } != null
               UserResponseItem(
                   userId = user.id,
                   username = user.username,
                   profilePictureUrl = user.profileImageUrl,
                   bio = user.bio,
                   isFollowing = isFollowing
               )
       }.filter { it.userId != userId }
   }
    suspend fun getUserByEmail(email:String) :User?{
        return userRepository.getUserByEmail(email)
    }
    fun isValidPassword(enteredPassword: String, actualPassword: String) : Boolean {
        return enteredPassword==actualPassword
    }
    fun validateCreateAccountReq(request : AccReq): ValidationEvent{
        if(request.email.isBlank() || request.password.isBlank() || request.username.isBlank()){
            return ValidationEvent.ErrorFieldEmpty
        }
        return ValidationEvent.Success
    }
    sealed class ValidationEvent{
        object ErrorFieldEmpty : ValidationEvent()
        object Success : ValidationEvent()
    }

}