package com.example.wiggle.feature_post.domain.repository

import android.net.Uri
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.wiggle.core.domain.model.CommentM
import com.example.wiggle.core.domain.model.PostM
import com.example.wiggle.core.domain.model.UserItem
import com.example.wiggle.core.util.Constants
import com.example.wiggle.core.util.Resource
import com.example.wiggle.core.util.SimpleResource
import com.example.wiggle.feature_post.data.paging.PostSource
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    suspend fun getPostsForFollows(page: Int, pageSize: Int): Resource<List<PostM>>
    suspend fun createPost(description: String,imageUri: Uri) : SimpleResource
    suspend fun getPostDetails(postId:String) : Resource<PostM>
    suspend fun getCommentsForPost(postId: String): Resource<List<CommentM>>
    suspend fun createComment(postId: String, comment: String): SimpleResource
    suspend fun likeParent(parentId: String, parentType: Int): SimpleResource
    suspend fun unlikeParent(parentId: String, parentType: Int): SimpleResource
    suspend fun getLikesForParent(parentId: String): Resource<List<UserItem>>
    suspend fun deletePost(postId: String): SimpleResource


}