package com.comunidadedevspace.taskbeats

import kotlin.random.Random

interface MyCountRepository {

    fun sum(): Int

    fun sub(p1: Int, p2: Int): Int
}

class MyCountRepositoryImpl(private val numbersProviders: MyNumbersProviders): MyCountRepository {
    override fun sum(): Int {
        val p1 = numbersProviders.getNumber()
        val p2 = numbersProviders.getNumber()
        return p1 + p2
    }

    override fun sub(p1: Int, p2: Int): Int {
        return p1 - p2
    }

}

interface MyNumbersProviders {
    fun getNumber(): Int
}

class MyNumbersProvidersImpl: MyNumbersProviders {
    override fun getNumber(): Int {
        return Random.nextInt(from = 0, until = 100)
    }


}