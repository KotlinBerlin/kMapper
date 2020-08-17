@file:JvmName("CollectionPathUtil")

package de.kotlinBerlin.kMapper.extensions

import de.kotlinBerlin.kMapper.ReadPath
import de.kotlinBerlin.kMapper.ReadWritePath
import de.kotlinBerlin.kMapper.internal.BasicReadPath
import de.kotlinBerlin.kMapper.internal.BasicReadWritePath
import kotlin.jvm.JvmName

//List

@JvmName("readListIndex")
operator fun <S, V> ReadPath<S, List<V>>.get(index: Int): ReadPath<S, V> = BasicReadPath("$name.[$index]") { this(it)[index] }

@JvmName("readListIndex")
operator fun <S, V> ((S) -> List<V>).get(index: Int): ReadPath<S, V> = path[index]

@JvmName("readWriteListIndex")
operator fun <S, V> ReadPath<S, MutableList<V>>.get(index: Int): ReadWritePath<S, V, V> =
    BasicReadWritePath("$name.[$index]", { this(it)[index] }) { target, value -> this(target)[index] = value }

@JvmName("readWriteListIndex")
operator fun <S, V> ((S) -> MutableList<V>).get(index: Int): ReadWritePath<S, V, V> = path[index]

// Array

@JvmName("readWriteArrayIndex")
operator fun <S, V> ReadPath<S, Array<V>>.get(index: Int): ReadWritePath<S, V, V> =
    BasicReadWritePath("$name.[$index]", { this(it)[index] }) { target, value -> this(target)[index] = value }

@JvmName("readWriteArrayIndex")
operator fun <S, V> ((S) -> Array<V>).get(index: Int): ReadWritePath<S, V, V> = path[index]

//Map

operator fun <S, K, V> ReadPath<S, Map<K, V>>.get(key: K): ReadPath<S, V?> = BasicReadPath("$name.[$key]") { this(it)[key] }

operator fun <S, K, V> ((S) -> Map<K, V>).get(key: K): ReadPath<S, V?> = this.path[key]

operator fun <S, K, V> ReadPath<S, MutableMap<K, V>>.get(key: K): ReadWritePath<S, V?, V> =
    BasicReadWritePath("$name.[$key]", { this(it)[key] }) { target, value -> this(target)[key] = value }

operator fun <S, K, V> ((S) -> MutableMap<K, V>).get(key: K): ReadWritePath<S, V?, V> = this.path[key]