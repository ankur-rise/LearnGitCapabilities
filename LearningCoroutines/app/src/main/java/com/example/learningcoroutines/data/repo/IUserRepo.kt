package com.example.learningcoroutines.data.repo

interface IUserRepo {
    suspend fun fetchUser() :User
}