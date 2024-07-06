package com.plcoding.di

import com.plcoding.repository.user.FakeUserRepo
import org.koin.dsl.module

internal val testModule = module {
    single{ FakeUserRepo() }
}