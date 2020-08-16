package de.kotlinBerlin.kMapper

import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KParameter
import kotlin.reflect.KProperty1
import kotlin.reflect.KType

interface DefaultMappingConfig<S, T> {

    fun KProperty1<S, *>.exclude()

    fun KMutableProperty1<T, *>.exclude()

    fun withPropertyMatcher(matcher: (KProperty1<S, *>) -> KMutableProperty1<T, *>?)

    fun withMappingResolver(resolver: (KType, KType) -> Mapping<*, *>?)

    fun detectPrimaryConstructor()

    fun detectPrimaryConstructor(matcher: (KParameter) -> KProperty1<S, *>)
}

interface DefaultBidirectionalMappingConfig<S, T> {

    fun KProperty1<S, *>.excludeFromSource()

    fun KProperty1<T, *>.excludeFromTarget()

    fun withPropertyMatcherToTarget(matcher: (KProperty1<S, *>) -> KMutableProperty1<T, *>?)

    fun withPropertyMatcherToSource(matcher: (KProperty1<T, *>) -> KMutableProperty1<S, *>?)

    fun withMappingResolver(resolver: (KType, KType) -> Mapping<*, *>?)

    fun detectTargetPrimaryConstructor()
    fun detectTargetPrimaryConstructor(matcher: (KParameter) -> KProperty1<S, *>)

    fun detectSourcePrimaryConstructor()
    fun detectSourcePrimaryConstructor(matcher: (KParameter) -> KProperty1<T, *>)
}