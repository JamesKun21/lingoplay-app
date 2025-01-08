package com.alexius.core.domain.usecases.talktalenav

import com.alexius.core.domain.repository.Repository
import javax.inject.Inject

class GenerateGrammarPrompt @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(userSentence: String) = repository.generateGrammarPrompt(userSentence)
}