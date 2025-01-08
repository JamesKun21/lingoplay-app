package com.alexius.core.domain.usecases.talktalenav

import com.alexius.core.data.local.ListOfStories
import com.alexius.core.domain.model.Story
import javax.inject.Inject

class GetStories @Inject constructor() {
    operator fun invoke(): Story {
        return ListOfStories.stories.first()
    }
}