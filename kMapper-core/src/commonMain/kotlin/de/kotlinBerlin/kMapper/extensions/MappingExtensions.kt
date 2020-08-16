@file:JvmName("MappingUtil")

package de.kotlinBerlin.kMapper.extensions

import de.kotlinBerlin.kMapper.BidirectionalMapping
import de.kotlinBerlin.kMapper.Mapping
import de.kotlinBerlin.kMapper.internal.globalMappings
import kotlin.jvm.JvmName
import kotlin.reflect.KType

@ExperimentalStdlibApi
fun Mapping<*, *>.register() {
    globalMappings[sourceType to targetType] = this
    if (this is BidirectionalMapping) {
        globalMappings[targetType to sourceType] = this.reversed()
    }
}

fun <S, T> BidirectionalMapping<S, T>.reversed() = object : BidirectionalMapping<T, S> {
    override val sourceType: KType get() = this@reversed.targetType
    override val targetType: KType get() = this@reversed.sourceType
    override fun map(source: T): S = this@reversed.mapReverse(source)
    override fun map(source: T, target: S) = this@reversed.mapReverse(source, target)
    override fun mapReverse(target: S): T = this@reversed.map(target)
    override fun mapReverse(target: S, source: T) = this@reversed.map(target, source)
}