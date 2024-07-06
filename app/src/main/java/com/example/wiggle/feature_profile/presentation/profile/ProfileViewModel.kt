package com.example.wiggle.feature_profile.presentation.profile

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.wiggle.core.domain.model.PostM
import com.example.wiggle.core.domain.use_case.GetOwnUserIdUseCase
import com.example.wiggle.core.presentation.PagingState
import com.example.wiggle.core.presentation.util.UiEvent
import com.example.wiggle.core.util.DefaultPaginator
import com.example.wiggle.core.util.Event
import com.example.wiggle.core.util.ParentType
import com.example.wiggle.core.util.PostLiker
import com.example.wiggle.core.util.Resource
import com.example.wiggle.core.util.UiText
import com.example.wiggle.feature_post.domain.use_case.PostUseCases
import com.example.wiggle.feature_profile.domain.use_case.ProfileUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class  ProfileViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases,
    private val postUseCases: PostUseCases,
    private val getOwnUserId: GetOwnUserIdUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val postLiker: PostLiker,

) : ViewModel() {

    private val _toolbarState = mutableStateOf(ProfileToolbarState())
    val toolbarState: State<ProfileToolbarState> = _toolbarState

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    private val _pagingState = mutableStateOf<PagingState<PostM>>(PagingState())
    val pagingState: State<PagingState<PostM>> = _pagingState

    private val paginator = DefaultPaginator(
        onLoadUpdated = { isLoading ->
            _pagingState.value = pagingState.value.copy(
                isLoading = isLoading
            )
        },
        onRequest = { page ->
            val userId = savedStateHandle.get<String>("userId") ?: getOwnUserId()
            Log.d("own id "," ${getOwnUserId()}")
            profileUseCases.getPostForProfile(
                userId = userId,
                 page
            )
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

    fun setExpandedRatio(ratio: Float) {
        _toolbarState.value = _toolbarState.value.copy(expandedRatio = ratio)
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.LikePost -> {
                viewModelScope.launch {
                    toggleLikeForParent(
                        parentId = event.postId
                    )
                }
            }

            else -> {}
        }
    }
    fun setToolbarOffsetY(value: Float) {
        _toolbarState.value = _toolbarState.value.copy(toolbarOffsetY = value)
    }

    init{
        loadNextPosts()
    }
    fun loadNextPosts() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }
    fun getProfile(userId: String?) {
        viewModelScope.launch {
            _state.value = state.value.copy(
                isLoading = true
            )
            val result = profileUseCases.getProfile(userId ?: getOwnUserId())
            when (result) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        profile = result.data,
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false
                    )
                    _eventFlow.emit(
                        UiEvent.SnackbarEvent(
                            uiText = result.uiText ?: UiText.unknownError()
                        )
                    )
                }
            }
        }

    }
    private fun toggleLikeForParent(parentId: String, ) {
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
                        items = posts,
                    )
                }
            )
        }
    }
}
