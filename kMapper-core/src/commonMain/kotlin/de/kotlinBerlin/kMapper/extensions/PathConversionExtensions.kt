@file:Suppress("unused")
@file:JvmName("PathConversionUtil")

package de.kotlinBerlin.kMapper.extensions

import de.kotlinBerlin.kMapper.*
import de.kotlinBerlin.kMapper.internal.ConvertedReadPath
import de.kotlinBerlin.kMapper.internal.ConvertedReadWritePath
import de.kotlinBerlin.kMapper.internal.ConvertedWritePath
import kotlin.jvm.JvmName
import kotlin.reflect.KMutableProperty1

//Read converted

fun <S, V, V1, V2> ReadWritePath<S, V, V1>.readConverted(convert: (V) -> V2): ReadWritePath<S, V2, V1> = ConvertedReadWritePath(this, readConvert = convert)

fun <S, V, V1> ReadPath<S, V>.readConverted(convert: (V) -> V1): ReadPath<S, V1> = ConvertedReadPath(this, convert)

fun <S, V, V1> KMutableProperty1<S, V>.readConverted(convert: (V) -> V1): ReadWritePath<S, V1, V> = toReadWritePath().readConverted(convert)

fun <S, V, V1> ((S) -> V).readConverted(convert: (V) -> V1): ReadPath<S, V1> = toReadPath().readConverted(convert)

fun <S, V, V1, V2> ReadWritePath<S, V, V1>.readConverted(mapping: Mapping<V, V2>): ReadWritePath<S, V2, V1> =
    ConvertedReadWritePath(this, readConvert = { mapping.map(it) })

fun <S, V, V1> ReadPath<S, V>.readConverted(mapping: Mapping<V, V1>): ReadPath<S, V1> = ConvertedReadPath(this) { mapping.map(it) }

fun <S, V, V1> KMutableProperty1<S, V>.readConverted(mapping: Mapping<V, V1>): ReadWritePath<S, V1, V> = toReadWritePath().readConverted(mapping)

fun <S, V, V1> ((S) -> V).readConverted(mapping: Mapping<V, V1>): ReadPath<S, V1> = toReadPath().readConverted(mapping)

//Write converted

fun <S, V, V1, V2> ReadWritePath<S, V, V1>.writeConverted(convert: (V2) -> V1): ReadWritePath<S, V, V2> = ConvertedReadWritePath(this, writeConvert = convert)

fun <S, V, V1> WritePath<S, V>.writeConverted(convert: (V1) -> V): WritePath<S, V1> = ConvertedWritePath(this, convert)

fun <S, V, V1> KMutableProperty1<S, V>.writeConverted(convert: (V1) -> V): ReadWritePath<S, V, V1> = toReadWritePath().writeConverted(convert)

fun <S, V, V1> ((S, V) -> Unit).writeConverted(convert: (V1) -> V): WritePath<S, V1> = toWritePath().writeConverted(convert)

fun <S, V, V1, V2> ReadWritePath<S, V, V1>.writeConverted(mapping: Mapping<V2, V1>): ReadWritePath<S, V, V2> = ConvertedReadWritePath(
    this,
    writeConvert = { mapping.map(it) })

fun <S, V, V1> WritePath<S, V>.writeConverted(mapping: Mapping<V1, V>): WritePath<S, V1> = ConvertedWritePath(this) { mapping.map(it) }

fun <S, V, V1> KMutableProperty1<S, V>.writeConverted(mapping: Mapping<V1, V>): ReadWritePath<S, V, V1> = toReadWritePath().writeConverted(mapping)

fun <S, V, V1> ((S, V) -> Unit).writeConverted(mapping: Mapping<V1, V>): WritePath<S, V1> = toWritePath().writeConverted(mapping)

//Fully converted

infix fun <S, V : Any, V1 : Any> ReadWritePath<S, V, V>.converted(converter: BidirectionalMapping<V, V1>): ReadWritePath<S, V1, V1> =
    ConvertedReadWritePath(this, converter::map, converter::mapReverse)

infix fun <S, V : Any, V1 : Any> KMutableProperty1<S, V>.converted(converter: BidirectionalMapping<V, V1>): ReadWritePath<S, V1, V1> =
    toReadWritePath() converted converter