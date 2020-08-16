@file:Suppress("unused")
@file:JvmName("MappingPathUtil")

package de.kotlinBerlin.kMapper

import kotlin.jvm.JvmName

interface ReadPath<in S, out V> {
    val name: String
    operator fun invoke(source: S): V
}

interface WritePath<in S, in V> {
    val name: String
    operator fun invoke(target: S, value: V)
}

interface ReadWritePath<in S, out V, in V1> : ReadPath<S, V>, WritePath<S, V1>
