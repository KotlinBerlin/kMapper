@file:JvmName("PathUtil")

package de.kotlinBerlin.kMapper.extensions

import de.kotlinBerlin.kMapper.ReadPath
import de.kotlinBerlin.kMapper.ReadWritePath
import de.kotlinBerlin.kMapper.WritePath
import de.kotlinBerlin.kMapper.internal.BasicReadPath
import de.kotlinBerlin.kMapper.internal.BasicReadWritePath
import de.kotlinBerlin.kMapper.internal.BasicWritePath
import kotlin.jvm.JvmName
import kotlin.reflect.KMutableProperty1

operator fun <T, V, V1> ((T) -> V).plus(setter: (T, V1) -> Unit): ReadWritePath<T, V, V1> = BasicReadWritePath(this.name, this, setter)
operator fun <T, V, V1> ((T, V1) -> Unit).plus(getter: (T) -> V): ReadWritePath<T, V, V1> = BasicReadWritePath(this.name, getter, this)

val <S, V> KMutableProperty1<S, V>.path: ReadWritePath<S, V, V> get() = BasicReadWritePath(this.name, { this(it) }) { target, value -> this.set(target, value) }
val <S, V> KMutableProperty1<S, V>.wPath: WritePath<S, V> get() = BasicWritePath(this.name) { target, value -> this.set(target, value) }
val <S, V> KMutableProperty1<S, V>.rPath: ReadPath<S, V> get() = BasicReadPath(this.name) { this(it) }
val <S, V> ((S, V) -> Unit).path: WritePath<S, V> get() = BasicWritePath(this.name, this)
val <S, V> ((S) -> V).path: ReadPath<S, V> get() = BasicReadPath(this.name, this)