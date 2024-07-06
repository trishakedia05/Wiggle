package com.plcoding.repository.user

import com.plcoding.data.models.User
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class FakeUserRepo :UserRepository {

         val users = mutableListOf<User>()
        override suspend fun createUser(user: User)
        {
            users.add(user)
        }
        override suspend fun getUserById(id: String): User? {
            return users.find{it.id == id}
        }
        override suspend fun getUserByEmail(email: String): User? {
            return users.find{it.email == email}
        }
    }
