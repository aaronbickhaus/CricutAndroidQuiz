package com.cricut.data.repository

import com.cricut.common.model.Question
import com.cricut.common.model.Result
import com.cricut.data.dao.QuestionsDao
import com.cricut.data.entity.toQuestion
import javax.inject.Inject

interface FetchFourQuestionsRepository {
    suspend fun fetchFourQuestions() : Result<List<Question>>
}

/**
 * [FetchFourQuestionsRepositoryImpl] implements [FetchFourQuestionsRepository]
 *
 * repository used for retrieving 4 questions from [QuestionsDao]
 *
 * @param questionsDao [QuestionsDao]
 */
class FetchFourQuestionsRepositoryImpl @Inject constructor(
    private val questionsDao: QuestionsDao
) : FetchFourQuestionsRepository {
    override suspend fun fetchFourQuestions(): Result<List<Question>> = try {
        Result.Success(questionsDao.fetchFourQuestions().map { it.toQuestion() })
    } catch (ex : Exception) {
        Result.Failure(ex)
    }
}