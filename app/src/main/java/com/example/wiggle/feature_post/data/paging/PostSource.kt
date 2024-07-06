package com.example.wiggle.feature_post.data.paging


import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.wiggle.core.domain.model.PostM
import com.example.wiggle.core.util.Constants
import com.example.wiggle.feature_post.data.remote.PostApi
import retrofit2.HttpException
import java.io.IOException

class PostSource(
    private val api: PostApi,
    private val source: Source
) : PagingSource<Int, PostM>() {

    private var currentPage = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostM> {
        return try {
            val nextPage = params.key ?: currentPage
            val posts = when(source) {
                is Source.Follows -> api.getPostForFollows(
                    page = nextPage,
                    pageSize = Constants.DEFAULT_PAGE_SIZE
                )
                is Source.Profile -> api.getPostsForProfile(
                    userId = source.userId,
                    page = nextPage,
                    pageSize = Constants.DEFAULT_PAGE_SIZE
                )
            }
            LoadResult.Page(
                data = posts,
                prevKey = if (nextPage == 0) null else nextPage - 1,
                nextKey = if (posts.isEmpty()) null else currentPage + 1
            ).also { currentPage++ }
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PostM>): Int? {
        return state.anchorPosition
    }

    sealed class Source {
        object Follows: Source()
        data class Profile(val userId: String): Source()
    }
}