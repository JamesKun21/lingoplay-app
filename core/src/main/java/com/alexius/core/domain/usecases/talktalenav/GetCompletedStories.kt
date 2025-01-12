package com.alexius.core.domain.usecases.talktalenav

import com.alexius.core.domain.repository.Repository
import javax.inject.Inject

class GetCompletedStories @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke() = repository.getCompletedStories()
}