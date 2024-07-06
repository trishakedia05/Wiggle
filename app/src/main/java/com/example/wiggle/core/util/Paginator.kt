package com.example.wiggle.core.util

interface Paginator<T> {
    suspend fun loadNextItems()
}