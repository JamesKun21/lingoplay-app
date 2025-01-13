package com.alexius.core.domain.usecases.profile

import com.alexius.core.domain.repository.Repository
import javax.inject.Inject

class SignOutUser @Inject constructor(
    private val repository: Repository
){
    operator fun invoke() {
        repository.signOut()
    }
}