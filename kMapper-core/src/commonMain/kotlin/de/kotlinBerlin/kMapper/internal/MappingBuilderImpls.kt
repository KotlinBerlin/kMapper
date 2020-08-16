package de.kotlinBerlin.kMapper.internal

import de.kotlinBerlin.kMapper.*
import de.kotlinBerlin.kMapper.extensions.reversed
import kotlin.reflect.KType

internal class BasicMappingBuilder<S, T>(override val sourceType: KType, override val targetType: KType) : MappingBuilder<S, T> {

    private val mappings = mutableMapOf<ReadPath<S, *>, WritePath<T, *>>()

    private lateinit var targetConstructor: (S) -> T

    override fun defConstructor(constructor: (S) -> T) {
        targetConstructor = constructor
    }

    override fun <V> ReadPath<S, V>.map(writePath: WritePath<T, V>) {
        mappings[this] = writePath
    }

    override fun run(mapping: Mapping<S, T>) {
        { it: S -> it } map { target, value -> mapping.map(value, target) }
    }


    fun build(): BasicMapping<S, T> = BasicMapping<S, T>(mappings, targetConstructor, sourceType, targetType)
}

internal class BasicBidirectionalMappingBuilder<S, T>(override val sourceType: KType, override val targetType: KType) :
    BidirectionalMappingBuilder<S, T> {

    private val mappings = mutableMapOf<ReadWritePath<S, *, *>, ReadWritePath<T, *, *>>()
    private val targetMappings = mutableMapOf<ReadPath<S, *>, WritePath<T, *>>()
    private val sourceMappings = mutableMapOf<WritePath<S, *>, ReadPath<T, *>>()

    private lateinit var sourceConstructor: (T) -> S
    private lateinit var targetConstructor: (S) -> T

    override fun defSourceConstructor(constructor: (T) -> S) {
        sourceConstructor = constructor
    }

    override fun defTargetConstructor(constructor: (S) -> T) {
        targetConstructor = constructor
    }

    override fun run(mapping: BidirectionalMapping<S, T>) {
        runToSource(mapping.reversed())
        runToTarget(mapping)
    }

    override fun runToSource(mapping: Mapping<T, S>) {
        { target: S, value: T -> mapping.map(value, target) } mapToSource { it }
    }

    override fun runToTarget(mapping: Mapping<S, T>) {
        { it: S -> it } mapToTarget { target, value -> mapping.map(value, target) }
    }

    override fun <V, V1> ReadWritePath<S, V, V1>.map(readWritePath: ReadWritePath<T, V1, V>) {
        mappings[this] = readWritePath
    }

    override fun <V> WritePath<S, V>.mapToSource(readPath: ReadPath<T, V>) {
        sourceMappings[this] = readPath
    }

    override fun <V> ReadPath<S, V>.mapToTarget(writePath: WritePath<T, V>) {
        targetMappings[this] = writePath
    }

    fun build(): BidirectionalMapping<S, T> =
        BasicBidirectionalMapping(
            mappings,
            targetMappings,
            sourceMappings,
            sourceConstructor,
            targetConstructor,
            sourceType,
            targetType
        )
}