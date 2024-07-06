package com.example.wiggle.feature_post.data.repository

import android.net.Uri
import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.core.net.toFile
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.wiggle.R
import com.example.wiggle.core.domain.model.CommentM
import com.example.wiggle.core.domain.model.PostM
import com.example.wiggle.core.domain.model.UserItem
import com.example.wiggle.core.util.Constants
import com.example.wiggle.core.util.Resource
import com.example.wiggle.core.util.SimpleResource
import com.example.wiggle.core.util.UiText
import com.example.wiggle.feature_auth.data.remote.request.CreateAccountRequest
import com.example.wiggle.feature_post.data.remote.PostApi
import com.example.wiggle.feature_post.data.remote.request.CreatePostReq
import com.example.wiggle.feature_post.data.paging.PostSource
import com.example.wiggle.feature_post.data.remote.request.CreateCommentRequest
import com.example.wiggle.feature_post.data.remote.request.LikeUpdateRequest
import com.example.wiggle.feature_post.domain.repository.PostRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.IOException

class PostRepositoryImpl(
    private val api: PostApi,
    private val gson: Gson
) : PostRepository
{

    override suspend fun getPostsForFollows(page: Int, pageSize: Int): Resource<List<PostM>> {
        return try {
            val posts = api.getPostForFollows(
                page = page,
                pageSize = pageSize
            )
            Resource.Success(data = posts)
        } catch(e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch(e: retrofit2.HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }


    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun createPost(description: String, imageUri: Uri)
    : SimpleResource
    {
        val request = CreatePostReq(description)
        val file = imageUri.toFile()
        return try{
            val response = api.createPost(
                postData = MultipartBody.Part
                    .createFormData(
                        "post_data",
                        gson.toJson(request)
                    ),
                postImage = MultipartBody.Part
                    .createFormData(
                        name = "post_image",
                        filename = file.name,
                        body = file.asRequestBody()
                    )
            )
            if(response.successful){
                Resource.Success(Unit)
            } else{
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))
            }
        } catch (e: IOException) {
            Log.e("CreatePost", "IOException occurred: ${e.message}")
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server))
        } catch (e: HttpException){
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }

    override suspend fun getPostDetails(postId: String): Resource<PostM> {
        return try {
            val response = api.getPostDetails(postId = postId)
            if(response.successful) {
                Resource.Success(response.data)
            } else {
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))
            }
        } catch(e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch(e: retrofit2.HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }

    override suspend fun getCommentsForPost(postId: String): Resource<List<CommentM>> {
        return try {
            val comments = api.getCommentsForPost(postId = postId).map {
                it.toComment()
            }
            Resource.Success(comments)
        } catch(e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch(e: retrofit2.HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }
    override suspend fun createComment(postId: String, comment: String): SimpleResource {
        return try {
            val response = api.createComment(
                CreateCommentRequest(
                    comment = comment,
                    postId = postId,
                )
            )
            if(response.successful) {
                Resource.Success(response.data)
            } else {
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))
            }
        } catch(e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch(e: retrofit2.HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }

    override suspend fun likeParent(parentId: String, parentType: Int): SimpleResource {
        return try {
            val response = api.likeParent(
                LikeUpdateRequest(
                    parentId = parentId,
                    parentType = parentType
                )
            )
            if(response.successful) {
                Resource.Success(response.data)
            } else {
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))
            }
        } catch(e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch(e: retrofit2.HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }

    override suspend fun unlikeParent(parentId: String, parentType: Int): SimpleResource {
        return try {
            val response = api.unlikeParent(
                parentId = parentId,
                parentType = parentType
            )
            if(response.successful) {
                Resource.Success(response.data)
            } else {
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))
            }
        } catch(e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch(e: retrofit2.HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }

    override suspend fun getLikesForParent(parentId: String): Resource<List<UserItem>> {
        return try {
            val response = api.getLikesForParent(
                parentId = parentId,
            )
            Resource.Success(response.map { it.toUserItem() })
        } catch(e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch(e: retrofit2.HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }

    override suspend fun deletePost(postId: String): SimpleResource {
        return try {
            api.deletePost(postId)
            Resource.Success(Unit)
        } catch(e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch(e: retrofit2.HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }



}