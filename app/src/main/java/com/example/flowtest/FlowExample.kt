package com.example.flowtest

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

/*val namesFlow = flow {
    val names = listOf("Jody", "Steve", "Lance", "Joe")
    names.forEach {
        delay(1000)
        emit(it)
    }




    val httpClient = OkHttpClient()
    val request =
        Request.Builder().url("https://www.vogella.com/tutorials/JavaLibrary-OkHttp/article.html")
            .build()
}*/

//val namesFlow = flowOf("Jody", "Steve", "Lance", "Joe")


@FlowPreview
fun main() {


    //COMBINE
    /*val numbersFlow = flowOf(1, 2, 3).onEach { delay(1000) }
    val lettersFlow = flowOf("A", "B", "C").onEach { delay(2000) }

    val combinedFlow = numbersFlow.combine(lettersFlow) { number, letter ->
        "$number $letter"
    }
    runBlocking {
        combinedFlow.collect { println(it) }
    }

    //ZIP
    val numbersFlow = flowOf(1, 2, 3).onEach { delay(1000) }
    val lettersFlow = flowOf("A", "B", "C", "D").onEach { delay(2000) }

    val combinedFlow = numbersFlow.zip(lettersFlow) { number, letter ->
        "$number $letter"
    }
    runBlocking {
        combinedFlow.collect { println(it) }
    }*/

    //FLATTEN MERGE
    val numbersFlow = flowOf(1, 2, 3).onEach { delay(1000) }
    val lettersFlow = flowOf("A", "B", "C", "D").onEach { delay(2000) }

    val combinedFlow = flowOf(numbersFlow, lettersFlow).flattenMerge()

    runBlocking {
        combinedFlow.collect { println(it) }
    }

    /*val namesFlow = listOf("Jody", "Steve", "Lance", "Joe").asFlow()

    runBlocking {
        namesFlow
            .map { name ->
                mapOf<String, Any>(
                    Pair("name", name),
                    Pair("text", "${name}'s name has ${name.length} letters."),
                    Pair("nameLength", name.length)
                )
            }
            .onEach { println("FIRST THREAD ---> ${Thread.currentThread().name}") }
            .flowOn(Dispatchers.Default)
            .onEach { println("SECOND THREAD ---> ${Thread.currentThread().name}") }
            .collect {
                println(it["text"])
            }
    }*/


    //ZIP
}