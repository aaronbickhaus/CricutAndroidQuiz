package com.cricut.common.provider

import kotlinx.coroutines.Dispatchers

interface DispatchProvider {
    val main
        get() = Dispatchers.Main
    val default
        get() = Dispatchers.Default
    val io
        get() = Dispatchers.IO
    val unconfined
        get() = Dispatchers.Unconfined
}

class DispatcherProviderImpl : DispatchProvider