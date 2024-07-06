package com.example.wiggle.feature_activity.data.repository
import com.example.wiggle.feature_activity.domain.repository.ActivityRepository
import ActivitySource
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.wiggle.core.domain.model.Activity
import com.example.wiggle.core.util.Constants
import com.example.wiggle.feature_activity.data.remote.ActivityApi
import kotlinx.coroutines.flow.Flow

class ActivityRepositoryImpl(
    private val api: ActivityApi
): ActivityRepository {

    override val activities: Flow<PagingData<Activity>>
        get() = Pager(PagingConfig(pageSize = Constants.DEFAULT_PAGE_SIZE)) {
            ActivitySource(api)
        }.flow
}