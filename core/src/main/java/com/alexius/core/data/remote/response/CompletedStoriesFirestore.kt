package com.alexius.core.data.remote.response

import com.alexius.core.domain.model.CompletedStories

data class CompletedStoriesFirestore(
    var beginner: ArrayList<Boolean>? = null,
    var intermediate: ArrayList<Boolean>? = null,
    var advancedTales: ArrayList<Boolean>? = null,
    var careerTales: ArrayList<Boolean>? = null
)

fun CompletedStoriesFirestore.toDomainModel(): CompletedStories {
    return CompletedStories(
        beginner = this.beginner?: arrayListOf(),
        intermediate = this.intermediate?: arrayListOf(),
        advancedTales = this.advancedTales?: arrayListOf(),
        careerTales = this.careerTales?: arrayListOf()
    )
}