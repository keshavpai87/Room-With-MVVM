package com.example.roommvvm

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roommvvm.db.Subscriber
import com.example.roommvvm.db.SubscriberRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.regex.Pattern

class SubscriberViewModel(private val repository: SubscriberRepository) : ViewModel() {

    val subscribers = repository.subscribers
    private var isUpdateOrDelete = false
    private lateinit var subscriberUpdateOrDelete: Subscriber

    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()

    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearOrDeleteButtonText = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()

    val message: LiveData<Event<String>>
        get() = statusMessage

    init {
        saveOrUpdateButtonText.value = "Save"
        clearOrDeleteButtonText.value = "Clear All"
    }

    fun saveOrUpdate() {

        if (inputName.value == null) {
            statusMessage.value = Event("Please enter Subscriber's Name")
        } else if (inputEmail.value == null) {
            statusMessage.value = Event("Please enter Subscriber's Email")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches()) {
            statusMessage.value = Event("Please enter correct Email Address")
        } else {
            if (isUpdateOrDelete) {
                subscriberUpdateOrDelete.name = inputName.value!!
                subscriberUpdateOrDelete.email = inputEmail.value!!
                update(subscriberUpdateOrDelete)
            } else {
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
        }
    }

    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            delete(subscriberUpdateOrDelete)
        } else {
            clearAll()
        }
    }

    /*fun insert(subscriber: Subscriber) {
        viewModelScope.launch(IO) {
            repository.insert(subscriber)
        }
    }*/

    // Same as above in a concise way
    private fun insert(subscriber: Subscriber) = viewModelScope.launch(IO) {
        val newRowId = repository.insert(subscriber)
        withContext(Dispatchers.Main) {
            if (newRowId > -1) {
                statusMessage.value = Event("Subscriber Inserted Successfully $newRowId")
            } else {
                statusMessage.value = Event("Data Insertion Failed")
            }

        }
    }

    fun update(subscriber: Subscriber) = viewModelScope.launch(IO) {
        val returnedVal = repository.update(subscriber)
        withContext(Dispatchers.Main) {
            if (returnedVal > 0) {
                inputName.value = ""
                inputEmail.value = ""
                isUpdateOrDelete = false
                saveOrUpdateButtonText.value = "Save"
                clearOrDeleteButtonText.value = "Clear All"
                statusMessage.value = Event("Subscriber Updated Successfully $returnedVal")
            } else {
                statusMessage.value = Event("Subscriber Update Failed")
            }
        }
    }

    fun delete(subscriber: Subscriber) = viewModelScope.launch(IO) {
        val returnedVal = repository.delete(subscriber)
        withContext(Dispatchers.Main) {
            if (returnedVal > 0) {
                inputName.value = ""
                inputEmail.value = ""
                isUpdateOrDelete = false
                saveOrUpdateButtonText.value = "Save"
                clearOrDeleteButtonText.value = "Clear All"
                statusMessage.value = Event("Subscriber Deleted Successfully $returnedVal")
            } else {
                statusMessage.value = Event("Subscriber Delete Failed")
            }
        }
    }

    fun clearAll() = viewModelScope.launch(IO) {
        val returnedVal = repository.deleteAll()
        withContext(Dispatchers.Main) {
            if (returnedVal > 0) {
                statusMessage.value = Event("All Subscribers Deleted Successfully $returnedVal")
            } else {
                statusMessage.value = Event("Subscriber Delete All Failed")
            }
        }
    }

    fun initUpdateAndDelete(subscriber: Subscriber) {
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        isUpdateOrDelete = true
        subscriberUpdateOrDelete = subscriber
        saveOrUpdateButtonText.value = "Update"
        clearOrDeleteButtonText.value = "Delete"
    }
}