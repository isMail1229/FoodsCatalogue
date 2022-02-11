package id.mailstudio.core.utils.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

interface CoroutineConfig {

    fun mainDispatcher(): CoroutineDispatcher

    fun ioDispatcher(): CoroutineDispatcher

    fun defaultDispatcher(): CoroutineDispatcher

    fun applicationScope(): CoroutineScope
}