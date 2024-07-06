package com.example.wiggle.feature_post.presentation.main_feed

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.wiggle.core.domain.model.PostM
import com.example.wiggle.core.presentation.PagingState
import com.example.wiggle.core.presentation.util.UiEvent
import com.example.wiggle.core.util.DefaultPaginator
import com.example.wiggle.core.util.Event
import com.example.wiggle.core.util.ParentType
import com.example.wiggle.core.util.PostLiker
import com.example.wiggle.feature_post.domain.use_case.PostUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFeedViewModel @Inject constructor(
    private val postUseCases: PostUseCases,
    private val postLiker: PostLiker
) : ViewModel()
{

    private val _pagingState = mutableStateOf<PagingState<PostM>>(PagingState())
    val pagingState: State<PagingState<PostM>> = _pagingState

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val paginator = DefaultPaginator(
        onLoadUpdated = { isLoading ->
            _pagingState.value = pagingState.value.copy(
                isLoading = isLoading
            )
        },
        onRequest = { page->
            postUseCases.getPostForFollows(page = page)
        },
        onSuccess = { posts ->
            _pagingState.value = pagingState.value.copy(
                items = pagingState.value.items + posts,
                endReached = posts.isEmpty(),
                isLoading = false
            )
        },
        onError = { uiText ->
            _eventFlow.emit(UiEvent.SnackbarEvent(uiText))
        }
    )

    init {
        loadNextPosts()
    }

    fun onEvent(event: MainFeedEvent) {
        when(event) {
            is MainFeedEvent.LikedPost -> {
                toggleLikeForParent(event.postId)
            }

            else -> {}
        }
    }

    private fun toggleLikeForParent(
        parentId: String,
    ) {
        viewModelScope.launch {
            postLiker.toggleLike(
                posts = pagingState.value.items,
                parentId = parentId,
                onRequest = { isLiked ->
                    postUseCases.toggleLikeForParent(
                        parentId = parentId,
                        parentType = ParentType.Post.type,
                        isLiked = isLiked
                    )
                },
                onStateUpdated = { posts ->
                    _pagingState.value = pagingState.value.copy(
                        items = posts
                    )
                }
            )
        }
    }

    fun loadNextPosts() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }
}