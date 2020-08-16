package de.kotlinBerlin.kMapper.internal

import de.kotlinBerlin.kMapper.ReadPath
import de.kotlinBerlin.kMapper.ReadWritePath
import de.kotlinBerlin.kMapper.WritePath

internal class BasicReadPath<S, V>(override val name: String, private val getter: (S) -> V) : ReadPath<S, V> {
    override fun invoke(source: S): V = getter(source)
}

internal class BasicWritePath<S, V>(override val name: String, private val setter: (S, V) -> Unit) : WritePath<S, V> {
    override fun invoke(target: S, value: V) = setter(target, value)
}

internal class BasicReadWritePath<S, V, V1>(override val name: String, private val getter: (S) -> V, private val setter: (S, V1) -> Unit) :
    ReadWritePath<S, V, V1> {
    override fun invoke(source: S): V = getter(source)
    override fun invoke(target: S, value: V1) = setter(target, value)
}

@Suppress("UNCHECKED_CAST")
internal class ConvertedReadWritePath<S, V, V1, V2, V3>(
    private val wrapped: ReadWritePath<S, V, V1>,
    private val readConvert: (V) -> V2 = { it as V2 },
    private val writeConvert: (V3) -> V1 = { it as V1 }
) : ReadWritePath<S, V2, V3> {
    override val name: String get() = wrapped.name
    override fun invoke(source: S): V2 = readConvert(wrapped(source))
    override fun invoke(target: S, value: V3) = wrapped.invoke(target, writeConvert(value))
}

internal class ConvertedReadPath<S, V, V1>(private val wrapped: ReadPath<S, V>, private val convert: (V) -> V1) : ReadPath<S, V1> {
    override val name: String get() = wrapped.name
    override fun invoke(source: S): V1 = convert(wrapped(source))
}

internal class ConvertedWritePath<S, V, V1>(private val wrapped: WritePath<S, V>, private val convert: (V1) -> V) : WritePath<S, V1> {
    override val name: String get() = wrapped.name
    override fun invoke(target: S, value: V1) = wrapped(target, convert(value))
}
