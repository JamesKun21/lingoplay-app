package com.alexius.core.domain.usecases.app_entry

import com.alexius.core.domain.repository.Repository
import javax.inject.Inject

class ChangeLocalName@Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(name: String) {
        repository.changeLocalUserInfoName(name)
    }
}