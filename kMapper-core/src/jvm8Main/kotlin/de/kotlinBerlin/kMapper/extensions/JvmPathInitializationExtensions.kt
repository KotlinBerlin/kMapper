package de.kotlinBerlin.kMapper.extensions

import de.kotlinBerlin.kMapper.ReadWritePath
import kotlin.RequiresOptIn.Level
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.jvm.isAccessible

inline fun <S, reified V> ReadWritePath<S, V?, V>.initOnRead(): ReadWritePath<S, V, V> {
    val tempKClass = V::class
    val tempConstructor = tempKClass.constructors.find { it.parameters.isEmpty() }
        ?: throw java.lang.IllegalArgumentException("Can not find no args constructor for KClass: $tempKClass")
    return initOnRead { tempConstructor.call() }
}

inline fun <S, reified V> KMutableProperty1<S, V?>.initOnRead(): ReadWritePath<S, V, V> = path.initOnRead()

@JvmName("initLateInitVarOnRead")
inline fun <S, V : Any> KMutableProperty1<S, V>.initOnRead(crossinline block: () -> V): ReadWritePath<S, V, V> {

    return if (isLateinit) {
        object : ReadWritePath<S, V, V> {
            override val name: String get() = this@initOnRead.name

            override fun invoke(source: S): V {
                return try {
                    this@initOnRead(source)
                } catch (e: UninitializedPropertyAccessException) {
                    val tempNewValue = block()
                    this@initOnRead.set(source, tempNewValue)
                    tempNewValue
                }
            }

            override fun invoke(target: S, value: V) = this@initOnRead.set(target, value)
        }
    } else {
        path
    }
}

@JvmName("initLateInitVarOnRead")
inline fun <S, reified V : Any> KMutableProperty1<S, V>.initOnRead(): ReadWritePath<S, V, V> {
    val tempKClass = V::class
    val tempConstructor = tempKClass.constructors.find { it.parameters.isEmpty() }
        ?: throw java.lang.IllegalArgumentException("Can not find no args constructor for KClass: $tempKClass")
    return initOnRead { tempConstructor.call() }
}

@DelegatedNotNullInit
inline fun <S, V : Any> KMutableProperty1<S, V>.initNotNullDelegatedOnRead(crossinline block: () -> V): ReadWritePath<S, V, V> =
    object : ReadWritePath<S, V, V> {
        override val name: String get() = this@initNotNullDelegatedOnRead.name

        override fun invoke(source: S): V {
            return try {
                this@initNotNullDelegatedOnRead(source)
            } catch (e: Exception) {
                this@initNotNullDelegatedOnRead.isAccessible.let {
                    try {
                        this@initNotNullDelegatedOnRead.isAccessible = true
                        if (this@initNotNullDelegatedOnRead.getDelegate(source) != null) {
                            val tempNewValue = block()
                            this@initNotNullDelegatedOnRead.set(source, tempNewValue)
                            tempNewValue
                        } else {
                            throw e
                        }
                    } finally {
                        this@initNotNullDelegatedOnRead.isAccessible = it
                    }
                }
            }
        }

        override fun invoke(target: S, value: V) = this@initNotNullDelegatedOnRead.set(target, value)
    }

@DelegatedNotNullInit
inline fun <S, reified V : Any> KMutableProperty1<S, V>.initNotNullDelegatedOnRead(): ReadWritePath<S, V, V> {
    val tempKClass = V::class
    val tempConstructor = tempKClass.constructors.find { it.parameters.isEmpty() }
        ?: throw java.lang.IllegalArgumentException("Can not find no args constructor for KClass: $tempKClass")
    return initNotNullDelegatedOnRead { tempConstructor.call() }
}

@RequiresOptIn(level = Level.WARNING)
annotation class DelegatedNotNullInit