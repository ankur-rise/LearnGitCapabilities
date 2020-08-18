package com.example.learningcoroutines.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.learningcoroutines.data.repo.IUserRepo
import com.example.learningcoroutines.data.repo.User
import com.example.learningcoroutines.data.repo.UserRepoImpl
import kotlinx.coroutines.*

class PageViewModel constructor() : ViewModel() {
    private val _index = MutableLiveData<Int>()
    val text: LiveData<String> = Transformations.map(_index) {
        "Hello world from section: $it"
    }

    fun setIndex(index: Int) {
        _index.value = index
    }

    suspend fun fetchAndShowUser(repo: UserRepoImpl) {
            val userDef = fetchUser(repo)
            userDef.await()
        if(userDef.isCompleted) {
            val user = userDef.getCompleted()
            user.name = "ankur chaudahry"
            showUser(user)
        }
            val user2 = fetchSecondUser(repo)
            showUser(user2)

        Log.i("PageViewModel","Both user printed")
        }

    private suspend fun fetchUser(repo: UserRepoImpl): Deferred<User> {
         return GlobalScope.async(Dispatchers.IO) {
            Thread.sleep(1000L)
             repo.fetchUser()
        }
    }

    private suspend fun fetchSecondUser(repo: UserRepoImpl) : User? {
        return GlobalScope.async(Dispatchers.IO) {
            repo.fetchUser()
        }.await()
    }

    private fun showUser(user:User?) {
        Log.i("PageViewModel", user?.name)
    }
}