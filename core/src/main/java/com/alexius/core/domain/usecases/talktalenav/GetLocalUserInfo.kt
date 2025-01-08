package com.alexius.core.domain.usecases.talktalenav

import com.alexius.core.domain.model.UserInfo
import com.alexius.core.domain.repository.Repository
import javax.inject.Inject

class GetLocalUserInfo @Inject constructor(
    private val repository: Repository
) {

    operator fun invoke(): UserInfo {
        return repository.getLocalUserInfo()
    }

}