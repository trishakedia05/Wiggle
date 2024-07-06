package com.plcoding.routes

import com.google.gson.Gson
import com.plcoding.data.requests.AccReq
import com.plcoding.di.testModule
import com.plcoding.repository.user.FakeUserRepo
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest


internal class UserRoutesKtTest: KoinTest {
    private val userRepository by inject<FakeUserRepo>()
    private val gson = Gson()
    @BeforeTest
    fun setUp(){

    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }
    @Test
    fun `Create user, no body attached, responds with badrequest`()  {

        testApplication {
                application {
                    loadKoinModules(testModule)
                    routing {
                        createUserRoutes(userRepository)
                    }
                }

                val response = client.post("/api/user/create") {
                    header("Content-Type","application/json")
                    setBody(gson.toJson(AccReq(email="",username="",password="")))
                    // Add request body or headers here (if applicable)
                }

                assertEquals(HttpStatusCode.OK, response.status) // Assert expected response status
                // Assert other response details (e.g., content, headers)
            }
        }
    }
