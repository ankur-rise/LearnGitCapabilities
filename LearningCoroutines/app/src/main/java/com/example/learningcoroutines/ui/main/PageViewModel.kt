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

     fun fetchAndShowUser_2(repo: UserRepoImpl) {
        GlobalScope.async (Dispatchers.IO){

        }
        GlobalScope.launch(Dispatchers.Main){
            val user1 = fetchFirstUser_2(repo)
            val user2 = fetchSecondUser_2(repo)

            Log.i("PageViewModel", user1.name.plus(" ").plus(user2.name))
        }
    }

    private suspend fun fetchFirstUser_2(repo: UserRepoImpl): User {
        return withContext(Dispatchers.IO) {
            Thread.sleep(1000L)
            repo.fetchUser()
        }
    }
    private suspend fun fetchSecondUser_2(repo: UserRepoImpl): User {
        return withContext(Dispatchers.IO) {
            repo.fetchUser()
        }
    }

     fun fetchAndShowUser(repo: UserRepoImpl) {
        GlobalScope.launch(Dispatchers.IO) {
            val userDef = withContext(Dispatchers.IO){fetchUser(repo)}

            val user2 = fetchSecondUser(repo)
            showUser(userDef)
            showUser(user2)

            Log.i("PageViewModel","Both user printed")
        }
     }

    private suspend fun fetchUser(repo: UserRepoImpl): User {
        Log.i("PageViewModel","Inside fetch user")
        Thread.sleep(1000L)
         return repo.fetchUser()
    }

    private suspend fun fetchSecondUser(repo: UserRepoImpl) : User? {
        Log.i("PageViewModel","Inside fetchSecondUser")
        return repo.fetchUser()

    }

    private fun showUser(user:User?) {
        Log.i("PageViewModel", user?.name)
    }
}