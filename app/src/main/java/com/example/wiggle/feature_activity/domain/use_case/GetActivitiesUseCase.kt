package com.example.wiggle.feature_activity.domain.use_case

import com.example.wiggle.feature_activity.domain.repository.ActivityRepository
import androidx.paging.PagingData
import com.example.wiggle.core.domain.model.Activity
import kotlinx.coroutines.flow.Flow

class GetActivitiesUseCase(
    private val repository: ActivityRepository
) {

    operator fun invoke(): Flow<PagingData<Activity>> {
        return repository.activities
    }
}