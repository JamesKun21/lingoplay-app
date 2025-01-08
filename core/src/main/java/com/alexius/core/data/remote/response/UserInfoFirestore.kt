package com.alexius.core.data.remote.response

import com.alexius.core.domain.model.UserInfo

data class UserInfoFirestore(
    var birth_date: String? = null,
    var full_name: String? = null,
    var phone_number: String? = null
)

fun UserInfoFirestore.toDomainModel(): UserInfo {
    return UserInfo(
        birth_date = this.birth_date?:"",
        full_name = this.full_name?: "",
        phone_number = this.phone_number?: ""
    )
}