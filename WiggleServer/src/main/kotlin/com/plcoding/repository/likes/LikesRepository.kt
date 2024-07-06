package com.plcoding.repository.likes

import com.plcoding.data.models.Like
import com.plcoding.data.util.ParentType
import com.plcoding.util.Constants

interface LikesRepository {
    suspend fun likeParent(userId: String, parentId: String, parentType: Int): Boolean
    suspend fun unlikeParent(userId: String, parentId: String,parentType: Int): Boolean
    suspend fun deleteLikesForParent(parentId: String)
    suspend fun getLikesForParent(parentId: String,page:Int=0,pageSize :Int = Constants.default_activity_page_size) : List<Like>

}