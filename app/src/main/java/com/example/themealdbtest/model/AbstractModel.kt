package com.example.themealdbtest.model

data class AbstractModel<T>(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val erro: Throwable? = null,
    val obj: T? = null)