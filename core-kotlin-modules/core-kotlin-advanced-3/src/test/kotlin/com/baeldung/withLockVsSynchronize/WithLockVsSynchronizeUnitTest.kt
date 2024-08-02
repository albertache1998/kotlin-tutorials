package com.baeldung.withLockVsSynchronize

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread
import kotlin.concurrent.withLock

class WithLockVsSynchronizeUnitTest {

    @Test
    fun `test synchronized keyword usage on a class`() {
        val counter = CounterClass()
        val threads = List(500) {
            thread {
                counter.increment()
            }
        }
        threads.forEach { it.join() }
        assertEquals(500, counter.getCount())
    }

    @Test
    fun `test withLock method usage`() {
        val counter = LockCounterClass()
        val threads = List(500) {
            thread {
                counter.increment()
            }
        }
        threads.forEach { it.join() }
        assertEquals(500, counter.getCount())
    }
}
class CounterClass {
    private var count = 0

    @Synchronized
    fun increment() {
        count++
    }

    @Synchronized
    fun getCount(): Int {
        return count
    }
}

class LockCounterClass {
    private var count = 0
    private val lock = ReentrantLock()

    fun increment() {
        lock.withLock {
            count++
        }
    }

    fun getCount(): Int {
        return lock.withLock {
            count
        }
    }
}