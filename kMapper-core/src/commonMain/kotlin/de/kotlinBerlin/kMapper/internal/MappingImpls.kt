@file:Suppress("UNCHECKED_CAST")

package de.kotlinBerlin.kMapper.internal

import de.kotlinBerlin.kMapper.*
import de.kotlinBerlin.kMapper.extensions.register
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import kotlin.reflect.KType

@ExperimentalStdlibApi
internal val globalMappings by object : ReadOnlyProperty<Any?, MutableMap<Pair<KType, KType>, Mapping<*, *>>> {

    private val mappings by lazy { mutableMapOf<Pair<KType, KType>, Mapping<*, *>>() }

    private var initialized = false

    override fun getValue(thisRef: Any?, property: KProperty<*>): MutableMap<Pair<KType, KType>, Mapping<*, *>> {
        if (!initialized) {
            mappings
            initialized = true
            defineSimpleBidirectionalMapping<Double, String>({ it.toString() }) { it.toDouble() }.register()
            defineSimpleBidirectionalMapping<Float, String>({ it.toString() }) { it.toFloat() }.register()
            defineSimpleBidirectionalMapping<Long, String>({ it.toString() }) { it.toLong() }.register()
            defineSimpleBidirectionalMapping<Int, String>({ it.toString() }) { it.toInt() }.register()
            defineSimpleBidirectionalMapping<Short, String>({ it.toString() }) { it.toShort() }.register()
            defineSimpleBidirectionalMapping<Byte, String>({ it.toString() }) { it.toByte() }.register()
        }
        return mappings
    }
}

internal class BasicMapping<S, T>(
    private val attributeMappings: Map<ReadPath<S, *>, WritePath<T, *>>,
    private val targetConstructor: (S) -> T,
    override val sourceType: KType,
    override val targetType: KType
) : Mapping<S, T> {

    override fun map(source: S): T = targetConstructor(source).also { map(source, it) }

    override fun map(source: S, target: T) {

        attributeMappings.forEach { (sourcePath, targetPath) ->
            try {
                (targetPath as WritePath<T, Any?>).invoke(target, sourcePath.invoke(source))
            } catch (e: IgnoreMappingException) {
            }
        }
    }
}

internal class BasicBidirectionalMapping<S, T>(
    private val mappings: Map<ReadWritePath<S, *, *>, ReadWritePath<T, *, *>>,
    private val targetMappings: Map<ReadPath<S, *>, WritePath<T, *>>,
    private val sourceMappings: Map<WritePath<S, *>, ReadPath<T, *>>,
    private val sourceConstructor: (T) -> S,
    private val targetConstructor: (S) -> T,
    override val sourceType: KType,
    override val targetType: KType
) : BidirectionalMapping<S, T> {

    override fun map(source: S): T = targetConstructor(source).also { map(source, it) }

    override fun map(source: S, target: T) {
        mappings.forEach { (sourcePath, targetPath) ->
            try {
                (targetPath as WritePath<T, Any?>).invoke(target, sourcePath.invoke(source))
            } catch (e: IgnoreMappingException) {
            }
        }
        targetMappings.forEach { (readPath, writePath) ->
            try {
                (writePath as WritePath<T, Any?>).invoke(target, readPath.invoke(source))
            } catch (e: IgnoreMappingException) {
            }
        }
    }

    override fun mapReverse(target: T): S = sourceConstructor(target).also { mapReverse(target, it) }

    override fun mapReverse(target: T, source: S) {
        mappings.forEach { (sourcePath, targetPath) ->
            try {
                (sourcePath as WritePath<S, Any?>).invoke(source, targetPath.invoke(target))
            } catch (e: IgnoreMappingException) {
            }
        }
        sourceMappings.forEach { (writePath, readPath) ->
            try {
                (writePath as WritePath<S, Any?>).invoke(source, readPath.invoke(target))
            } catch (e: IgnoreMappingException) {
            }
        }
    }
}