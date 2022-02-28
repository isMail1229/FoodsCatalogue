package id.mailstudio.detail

import id.mailstudio.core.utils.coroutine.CoroutineConfig
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope


@ExperimentalCoroutinesApi
class FakeCoroutineConfigImpl constructor(
    private val testCoroutineDispatcher: CoroutineDispatcher = TestCoroutineDispatcher(),
    private val testCoroutineScope: CoroutineScope = TestCoroutineScope()
) : CoroutineConfig {

    override fun mainDispatcher(): CoroutineDispatcher {
        return testCoroutineDispatcher
    }

    override fun ioDispatcher(): CoroutineDispatcher {
        return testCoroutineDispatcher
    }

    override fun defaultDispatcher(): CoroutineDispatcher {
        return testCoroutineDispatcher
    }

    override fun applicationScope(): CoroutineScope {
        return testCoroutineScope
    }
}