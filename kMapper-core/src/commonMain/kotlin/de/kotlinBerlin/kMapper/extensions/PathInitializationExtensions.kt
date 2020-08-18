@file:Suppress("unused")
@file:JvmName("PathInitializationUtil")

package de.kotlinBerlin.kMapper.extensions

import de.kotlinBerlin.kMapper.ReadWritePath
import kotlin.jvm.JvmName
import kotlin.reflect.KMutableProperty1

inline fun <S, V> ReadWritePath<S, V?, V>.initOnRead(crossinline block: () -> V): ReadWritePath<S, V, V> {
    return object : ReadWritePath<S, V, V> {
        override val name: String get() = this@initOnRead.name
        override fun invoke(source: S): V {
            val tempValue = this@initOnRead(source)
            if (tempValue == null) {
                val tempNewValue = block()
                this@initOnRead(source, tempNewValue)
                return tempNewValue
            }
            return tempValue
        }

        override fun invoke(target: S, value: V) = this@initOnRead(target, value)
    }
}

inline fun <S, V> KMutableProperty1<S, V?>.initOnRead(crossinline block: () -> V): ReadWritePath<S, V, V> =
    path.initOnRead { block() }