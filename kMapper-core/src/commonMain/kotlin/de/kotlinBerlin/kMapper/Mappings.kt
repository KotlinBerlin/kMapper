package de.kotlinBerlin.kMapper

import kotlin.reflect.KType

interface Mapping<in S, out T> {
    val sourceType: KType
    val targetType: KType
    fun map(source: S): T
    fun map(source: S, target: @UnsafeVariance T)
}

interface BidirectionalMapping<S, T> : Mapping<S, T> {
    fun mapReverse(target: T): S
    fun mapReverse(target: T, source: S)
}