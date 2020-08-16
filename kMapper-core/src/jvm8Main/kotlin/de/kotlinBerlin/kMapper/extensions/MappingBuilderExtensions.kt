package de.kotlinBerlin.kMapper.extensions

import de.kotlinBerlin.kMapper.*
import de.kotlinBerlin.kMapper.internal.BasicDefaultBidirectionalMappingConfig
import de.kotlinBerlin.kMapper.internal.BasicDefaultMappingConfig

@ExperimentalStdlibApi
fun <S, T> MappingBuilder<S, T>.withDefault() = withDefault {}

@Suppress("UNCHECKED_CAST")
@ExperimentalStdlibApi
fun <S, T> MappingBuilder<S, T>.withDefault(init: DefaultMappingConfig<S, T>.() -> Unit): Unit = BasicDefaultMappingConfig<S, T>(sourceType, targetType).run {
    init()
    val tempMappings = buildMappings()
    tempMappings.forEach { (readPath, writePath) ->
        readPath map writePath as WritePath<T, Any?>
    }
    buildConstructor()?.let(::defConstructor)
}

@ExperimentalStdlibApi
fun <S, T> BidirectionalMappingBuilder<S, T>.withDefault() = withDefault {}

@Suppress("UNCHECKED_CAST")
@ExperimentalStdlibApi
fun <S, T> BidirectionalMappingBuilder<S, T>.withDefault(init: DefaultBidirectionalMappingConfig<S, T>.() -> Unit): Unit =
    BasicDefaultBidirectionalMappingConfig<S, T>(sourceType, targetType).run {
        init()
        val (tempTargetMappings, tempSourceMappings) = buildMappings()
        tempTargetMappings.forEach { (readPath, writePath) ->
            readPath mapToTarget writePath as WritePath<T, Any?>
        }
        tempSourceMappings.forEach { (writePath, readPath) ->
            writePath as WritePath<S, Any?> mapToSource readPath
        }
        buildSourceConstructor()?.let(::defSourceConstructor)
        buildTargetConstructor()?.let(::defTargetConstructor)
    }