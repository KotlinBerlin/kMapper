@file:JvmName("NestedPathUtil")

package de.kotlinBerlin.kMapper.extensions

import de.kotlinBerlin.kMapper.ReadPath
import de.kotlinBerlin.kMapper.ReadWritePath
import de.kotlinBerlin.kMapper.WritePath
import de.kotlinBerlin.kMapper.internal.BasicReadPath
import de.kotlinBerlin.kMapper.internal.BasicReadWritePath
import de.kotlinBerlin.kMapper.internal.BasicWritePath

import kotlin.jvm.JvmName
import kotlin.reflect.*

//Nested Paths

operator fun <S, V, V1, V2> ReadPath<S, V>.get(subPath: ReadWritePath<V, V1, V2>): ReadWritePath<S, V1, V2> =
    BasicReadWritePath("${this.name}.${subPath.name}", { subPath(this(it)) }) { target: S, value: V2 -> subPath(this(target), value) }

operator fun <S, V, V1> ReadPath<S, V>.get(subPath: ReadPath<V, V1>): ReadPath<S, V1> = BasicReadPath("${this.name}.${subPath.name}") { subPath(this(it)) }
operator fun <S, V, V1> ReadPath<S, V>.get(subPath: WritePath<V, V1>): WritePath<S, V1> =
    BasicWritePath("${this.name}.${subPath.name}") { target, value -> subPath(this(target), value) }

//Nested Paths with Functions
operator fun <S, V, V1, V2> ((S) -> V).get(subPath: ReadWritePath<V, V1, V2>): ReadWritePath<S, V1, V2> = path[subPath]

operator fun <S, V, V1> ((S) -> V).get(subPath: ReadPath<V, V1>): ReadPath<S, V1> = BasicReadPath("${this.name}.${subPath.name}") { subPath(this(it)) }
operator fun <S, V, V1> ((S) -> V).get(subPath: (V) -> V1): ReadPath<S, V1> = BasicReadPath("${this.name}.${subPath.name}") { subPath(this(it)) }
operator fun <S, V, V1> ReadPath<S, V>.get(subPath: (V) -> V1): ReadPath<S, V1> = BasicReadPath("${this.name}.${subPath.name}") { subPath(this(it)) }

operator fun <S, V, V1> ((S) -> V).get(subPath: WritePath<V, V1>): WritePath<S, V1> =
    BasicWritePath("${this.name}.${subPath.name}") { target, value -> subPath(this(target), value) }

operator fun <S, V, V1> ((S) -> V).get(subPath: (V, V1) -> Unit): WritePath<S, V1> =
    BasicWritePath("${this.name}.${subPath.name}") { target, value -> subPath(this(target), value) }

operator fun <S, V, V1> ReadPath<S, V>.get(subPath: (V, V1) -> Unit): WritePath<S, V1> =
    BasicWritePath("${this.name}.${subPath.name}") { target, value -> subPath(this(target), value) }

//Nested Paths with Properties
operator fun <S, V, V1, V2> KProperty1<S, V>.get(subPath: ReadWritePath<V, V1, V2>): ReadWritePath<S, V1, V2> = path[subPath]

operator fun <S, V, V1> KProperty1<S, V>.get(subPath: ReadPath<V, V1>): ReadPath<S, V1> = BasicReadPath("${this.name}.${subPath.name}") { subPath(this(it)) }
operator fun <S, V, V1> KProperty1<S, V>.get(subPath: KProperty1<V, V1>): ReadPath<S, V1> = BasicReadPath("${this.name}.${subPath.name}") { subPath(this(it)) }
operator fun <S, V, V1> ReadPath<S, V>.get(subPath: KProperty1<V, V1>): ReadPath<S, V1> = BasicReadPath("${this.name}.${subPath.name}") { subPath(this(it)) }

operator fun <S, V, V1> KProperty1<S, V>.get(subPath: WritePath<V, V1>): WritePath<S, V1> =
    BasicWritePath("${this.name}.${subPath.name}") { target, value -> subPath(this(target), value) }

operator fun <S, V, V1> KProperty1<S, V>.get(subPath: KMutableProperty1<V, V1>): ReadWritePath<S, V1, V1> =
    BasicReadWritePath("${this.name}.${subPath.name}", { subPath(this(it)) }) { target, value -> subPath.set(this(target), value) }

operator fun <S, V, V1> ReadPath<S, V>.get(subPath: KMutableProperty1<V, V1>): ReadWritePath<S, V1, V1> =
    BasicReadWritePath("${this.name}.${subPath.name}", { subPath(this(it)) }) { target, value -> subPath.set(this(target), value) }

//Nested Properties with Functions
operator fun <S, V, V1> ((S) -> V).get(subPath: KMutableProperty1<V, V1>): ReadWritePath<S, V1, V1> =
    BasicReadWritePath("${this.name}.${subPath.name}", { subPath(this(it)) }) { target, value -> subPath.set(this(target), value) }

internal val ((Nothing) -> Any?).name
    get() = when (this) {
        is KFunction1 -> name.removePrefix("get").decapitalize()
        is KProperty<*> -> name
        else -> ""
    }

internal val ((Nothing, Nothing) -> Unit).name
    get() = when (this) {
        is KFunction2 -> name.removePrefix("set").decapitalize()
        is KProperty<*> -> name
        else -> ""
    }
