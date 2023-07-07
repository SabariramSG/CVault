package com.example.cvault.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.cvault.bo.CardDetails
import com.example.cvault.networkutils.NetworkUtils
import com.example.cvault.repo.CardDetailsRepo
import com.example.cvault.repo.LocalDatabase
import kotlinx.coroutines.Dispatchers

class CardDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val cardDetailsDAO = LocalDatabase.getDatabase(application).cardDetailsDAO()
    private val repo = CardDetailsRepo(cardDetailsDAO, NetworkUtils().isNetworkAvailable(application.applicationContext))
    fun fetchCards(): LiveData<List<CardDetails>?> =
        liveData(Dispatchers.IO) {
            try {
                val result = repo.loadCards()
                result.onSuccess {
                    emit(it)
                }
                result.onFailure {
                    emit(null)
                }
            }
            catch (e: Exception) {
                emit(null)
            }
        }

    fun insertCardDetail(cardEntity : CardDetails): LiveData<Boolean> = liveData(Dispatchers.IO){
        try {
           repo.updateCardDetailsInLocal(listOf(cardEntity))
           emit(true)
        }
        catch(e : Exception){
           emit(false)
        }
    }
}