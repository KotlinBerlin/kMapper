@file:Suppress("unused")
@file:JvmName("MappingBuilderUtil")

package de.kotlinBerlin.kMapper

import de.kotlinBerlin.kMapper.extensions.path
import de.kotlinBerlin.kMapper.internal.BasicBidirectionalMappingBuilder
import de.kotlinBerlin.kMapper.internal.BasicMappingBuilder
import kotlin.jvm.JvmName
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KType
import kotlin.reflect.typeOf

@ExperimentalStdlibApi
inline fun <reified S : Any, reified T : Any> defineSimpleMapping(crossinline mapping: (S) -> T) =
    defineMapping<S, T> {
        defConstructor { mapping(it) }
    }

@ExperimentalStdlibApi
inline fun <reified S, reified T> defineMapping(crossinline init: MappingBuilder<S, T>.() -> Unit): Mapping<S, T> =
    defineMapping(typeOf<S>(), typeOf<T>()) { init() }

fun <S, T> defineMapping(sourceType: KType, targetType: KType, init: MappingBuilder<S, T>.() -> Unit): Mapping<S, T> =
    BasicMappingBuilder<S, T>(sourceType, targetType).run {
        init()
        build()
    }

@ExperimentalStdlibApi
inline fun <reified S : Any, reified T : Any> defineSimpleBidirectionalMapping(crossinline targetMapping: (S) -> T, crossinline sourceMapping: (T) -> S) =
    defineBidirectionalMapping<S, T> {
        defSourceConstructor { sourceMapping(it) }
        defTargetConstructor { targetMapping(it) }
    }

@ExperimentalStdlibApi
inline fun <reified S, reified T> defineBidirectionalMapping(crossinline init: BidirectionalMappingBuilder<S, T>.() -> Unit): BidirectionalMapping<S, T> =
    defineBidirectionalMapping(typeOf<S>(), typeOf<T>()) { init() }

fun <S, T> defineBidirectionalMapping(
    sourceClass: KType,
    targetClass: KType,
    init: BidirectionalMappingBuilder<S, T>.() -> Unit
): BidirectionalMapping<S, T> = BasicBidirectionalMappingBuilder<S, T>(sourceClass, targetClass).run {
    init()
    build()
}

interface MappingBuilder<S, T> {

    val sourceType: KType
    val targetType: KType

    fun defConstructor(constructor: (S) -> T)

    fun run(mapping: Mapping<S, T>)

    //Basic mappings
    infix fun <V> ReadPath<S, V>.map(writePath: WritePath<T, V>)

    //Mappings by functions
    infix fun <V> ((S) -> V).map(setter: (T, V) -> Unit): Unit = this.path map setter.path

    //Mappings from functions to properties
    infix fun <V> ((S) -> V).map(setter: KMutableProperty1<T, V>): Unit = this.path map setter.path

    //Mappings from properties to paths
    infix fun <V> ReadPath<S, V>.map(setter: KMutableProperty1<T, V>): Unit = this map setter.path

    //Mappings from functions to paths
    infix fun <V> ReadPath<S, V>.map(setter: (T, V) -> Unit): Unit = this map setter.path
    infix fun <V> ((S) -> V).map(setter: WritePath<T, V>): Unit = this.path map setter

}

interface BidirectionalMappingBuilder<S, T> {

    val sourceType: KType
    val targetType: KType

    fun defSourceConstructor(constructor: (T) -> S)
    fun defTargetConstructor(constructor: (S) -> T)

    fun run(mapping: BidirectionalMapping<S, T>)
    fun runToSource(mapping: Mapping<T, S>)
    fun runToTarget(mapping: Mapping<S, T>)

    //Basic mappings (bidirectional, and unidirectional)
    infix fun <V, V1> ReadWritePath<S, V, V1>.map(readWritePath: ReadWritePath<T, V1, V>)
    infix fun <V> WritePath<S, V>.mapToSource(readPath: ReadPath<T, V>)
    infix fun <V> ReadPath<S, V>.mapToTarget(writePath: WritePath<T, V>)

    //Bidirectional mappings between Properties
    infix fun <V> KMutableProperty1<S, V>.map(property: KMutableProperty1<T, V>) = this.path map property.path

    //Bidirectional mappings between Properties and paths
    infix fun <V> ReadWritePath<S, V, V>.map(property: KMutableProperty1<T, V>): Unit = this map property.path
    infix fun <V> KMutableProperty1<S, V>.map(property: ReadWritePath<T, V, V>): Unit = this.path map property

    //Unidirectional mappings by functions
    infix fun <V> ((S, V) -> Unit).mapToSource(getter: (T) -> V): Unit = this.path mapToSource getter.path
    infix fun <V> ((S) -> V).mapToTarget(setter: (T, V) -> Unit): Unit = this.path mapToTarget setter.path

    //Unidirectional mappings from Properties to functions and reverse
    infix fun <V> KMutableProperty1<S, V>.mapToSource(getter: (T) -> V): Unit = this.path mapToSource getter.path
    infix fun <V> ((S) -> V).mapToTarget(setter: KMutableProperty1<T, V>): Unit = this.path mapToTarget setter.path

    //Unidirectional mappings from Properties to paths and reverse
    infix fun <V> KMutableProperty1<S, V>.mapToSource(getter: ReadPath<T, V>): Unit = this.path mapToSource getter
    infix fun <V> ReadPath<S, V>.mapToTarget(setter: KMutableProperty1<T, V>): Unit = this mapToTarget setter.path

    //Unidirectional mappings from functions to paths and reverse
    infix fun <V> ((S, V) -> Unit).mapToSource(getter: ReadPath<T, V>): Unit = this.path mapToSource getter
    infix fun <V> WritePath<S, V>.mapToSource(getter: (T) -> V): Unit = this mapToSource getter.path
    infix fun <V> ReadPath<S, V>.mapToTarget(setter: (T, V) -> Unit): Unit = this mapToTarget setter.path
    infix fun <V> ((S) -> V).mapToTarget(setter: WritePath<T, V>): Unit = this.path mapToTarget setter
}