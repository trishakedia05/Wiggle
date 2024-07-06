package com.example.wiggle.feature_activity.domain.repository
import androidx.paging.PagingData
import com.example.wiggle.core.domain.model.Activity

import kotlinx.coroutines.flow.Flow

interface ActivityRepository {

    val activities: Flow<PagingData<Activity>>
}