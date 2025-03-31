package com.example.roommvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roommvvm.db.Subscriber
import com.example.roommvvm.db.SubscriberRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class SubscriberViewModel(private val repository: SubscriberRepository) : ViewModel() {

    val subscribers = repository.subscribers

    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()

    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearOrDeleteButtonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value = "Save"
        clearOrDeleteButtonText.value = "Clear All"
    }

    fun saveOrUpdate() {
        val name = inputName.value!!
        val email = inputEmail.value!!
        insert(Subscriber(0, name, email))
        inputName.value = ""
        inputEmail.value = ""
        /*if(saveOrUpdateButtonText.value == "Save") {
            saveOrUpdateButtonText.value = "Update"
        } else {
            saveOrUpdateButtonText.value = "Save"
        }*/
    }

    fun clearAllOrDelete() {
        clearAll()
        /*if(clearOrDeleteButtonText.value == "CLear All") {
            clearOrDeleteButtonText.value = "Delete"
        } else {
            clearOrDeleteButtonText.value = "CLear All"
        }*/
    }

    /*fun insert(subscriber: Subscriber) {
        viewModelScope.launch(IO) {
            repository.insert(subscriber)
        }
    }*/

    // Same as above in a concise way
    fun insert(subscriber: Subscriber)  =  viewModelScope.launch(IO) {
        repository.insert(subscriber)
    }

    fun update(subscriber: Subscriber)  =  viewModelScope.launch(IO) {
        repository.update(subscriber)
    }

    fun delete(subscriber: Subscriber)  =  viewModelScope.launch(IO) {
        repository.delete(subscriber)
    }

    fun clearAll()  =  viewModelScope.launch(IO) {
        repository.deleteAll()
    }
}