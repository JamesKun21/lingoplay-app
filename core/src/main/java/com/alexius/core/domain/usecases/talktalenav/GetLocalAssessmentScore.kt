package com.alexius.core.domain.usecases.talktalenav

import com.alexius.core.domain.model.AssessmentScore
import com.alexius.core.domain.model.UserInfo
import com.alexius.core.domain.repository.Repository
import javax.inject.Inject

class GetLocalAssessmentScore @Inject constructor(
    private val repository: Repository
) {

    operator fun invoke(): AssessmentScore {
        return repository.getLocalAssessmentScore()
    }

}