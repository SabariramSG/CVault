package com.example.cvault.repo

import com.example.cvault.bo.CardDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CardDetailsRepo(private val cardDetailsDAO: CardDetailsDAO, private val isOnline : Boolean) {
    //private val apiService: ApiService = RetrofitBuilder.getRetrofit().create(ApiService::class.java)
    suspend fun loadCards() : Result<List<CardDetails>?>{
        return withContext(Dispatchers.IO){
            try{
                val localCardDetails = loadCardDetailsFromLocal()
                if(localCardDetails.isNullOrEmpty())
                    return@withContext Result.failure(Exception("No cards available"))
                return@withContext Result.success(localCardDetails)
                /*if(isOnline) {
                    val result: News = apiService.getTopNews("sports","en","in", BuildConfig.API_KEY)?: return@withContext Result.failure(Exception("No news available right now"))
                    if (result.articles.isEmpty())
                        return@withContext Result.failure(Exception("No News Available Right Now"))
                    updateArticlesInLocal(result.articles)
                    return@withContext Result.success(result.articles)
                }*/
            }
            catch (e : Exception){
                e.printStackTrace()
                Result.failure(Exception(e))
            }
        }
    }

    suspend fun updateCardDetailsInLocal(articles: List<CardDetails>) {
        articles.forEach {
            cardDetailsDAO.insertCard(it)
        }
    }

    private fun loadCardDetailsFromLocal() : List<CardDetails>? {
        return cardDetailsDAO.loadCardDetails()
    }


}