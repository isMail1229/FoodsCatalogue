package id.mailstudio.core.utils.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject

class CoroutineConfigImpl @Inject constructor() : CoroutineConfig {
    override fun mainDispatcher(): CoroutineDispatcher =
        Dispatchers.Main

    override fun ioDispatcher(): CoroutineDispatcher =
        Dispatchers.IO

    override fun defaultDispatcher(): CoroutineDispatcher =
        Dispatchers.Default

    override fun applicationScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob() + ioDispatcher())
    }
}