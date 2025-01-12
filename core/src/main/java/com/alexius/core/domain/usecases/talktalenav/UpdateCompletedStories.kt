package com.alexius.core.domain.usecases.talktalenav

import com.alexius.core.domain.model.CompletedStories
import com.alexius.core.domain.repository.Repository
import javax.inject.Inject

class UpdateCompletedStories @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(completedStories: CompletedStories) = repository.updateCompletedStories(completedStories)
}