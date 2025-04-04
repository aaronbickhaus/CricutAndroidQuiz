package com.cricut.domain.usecase.concrete

import com.cricut.common.model.Question
import com.cricut.data.repository.FetchFourQuestionsRepository
import com.cricut.domain.usecase.abstract.UseCase
import com.cricut.common.model.Result
import javax.inject.Inject

/**
 * [FetchQuestionsUseCase]
 *
 * [UseCase] used to get four random [Question]
 *
 * @param fetchFourQuestionsRepository [FetchFourQuestionsRepository]
 */
class FetchQuestionsUseCase @Inject constructor(
    private val fetchFourQuestionsRepository: FetchFourQuestionsRepository
) : UseCase<Unit, List<Question>>() {
    override suspend fun execute(params: Unit): Result<List<Question>> = fetchFourQuestionsRepository.fetchFourQuestions()
}