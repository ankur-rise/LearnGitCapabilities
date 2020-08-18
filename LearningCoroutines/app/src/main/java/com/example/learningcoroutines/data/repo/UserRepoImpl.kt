package com.example.learningcoroutines.data.repo

import com.google.gson.Gson

class UserRepoImpl : IUserRepo {
    var index:Int = 1
    override suspend fun fetchUser() : User {
        return getUser()
    }

    private fun getUser() : User {
        val json = "{\"name\":\"Ankur$index\"}"
        val user = Gson().fromJson<User>(json, User::class.java)
        index++
        return user
    }
}