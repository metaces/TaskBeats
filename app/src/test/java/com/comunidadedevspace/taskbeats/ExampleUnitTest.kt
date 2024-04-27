package com.comunidadedevspace.taskbeats

import org.junit.Test

import org.junit.Assert.*
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private val mockMyNumbersProvider:  MyNumbersProviders = mock()

    private val underTest = MyCountRepositoryImpl(
        numbersProviders = mockMyNumbersProvider
    )
    @Test
    fun addition_isCorrect() {

        // GIVEN
        whenever(mockMyNumbersProvider.getNumber()).thenReturn(2)
//        fakeMyNumbersProvider.p1 = 2

        // WHEN
        val result = underTest.sum()

        // THEN
        val expected = 4
        assertEquals(expected, result)
    }
}