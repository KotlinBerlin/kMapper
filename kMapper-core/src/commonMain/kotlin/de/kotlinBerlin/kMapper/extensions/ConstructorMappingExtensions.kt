@file:Suppress("unused")
@file:JvmName("ConstructorMappingUtil")

package de.kotlinBerlin.kMapper.extensions

import de.kotlinBerlin.kMapper.BidirectionalMappingBuilder
import de.kotlinBerlin.kMapper.MappingBuilder
import kotlin.jvm.JvmName

//Unidirectional

//Constructors with 0 arguments!

fun <S : Any, T : Any> MappingBuilder<S, T>.defConstructor(constructor: () -> T) = defConstructor { constructor() }

//Constructors with 1 arguments!

fun <S : Any, T : Any, V1> MappingBuilder<S, T>.defConstructor(function: (V1) -> T, v1Getter: (S) -> V1) = defConstructor { function.invoke(v1Getter(it)) }

//Constructors with 2 arguments!

fun <S : Any, T : Any, V1, V2> MappingBuilder<S, T>.defConstructor(function: (V1, V2) -> T, v1Getter: (S) -> V1, v2Getter: (S) -> V2) =
    defConstructor { function.invoke(v1Getter(it), v2Getter(it)) }

//Constructors with 3 arguments!

fun <S : Any, T : Any, V1, V2, V3> MappingBuilder<S, T>.defConstructor(
    function: (V1, V2, V3) -> T,
    v1Getter: (S) -> V1,
    v2Getter: (S) -> V2,
    v3Getter: (S) -> V3
) = defConstructor { function.invoke(v1Getter(it), v2Getter(it), v3Getter(it)) }

//Constructors with 4 arguments!

fun <S : Any, T : Any, V1, V2, V3, V4> MappingBuilder<S, T>.defConstructor(
    function: (V1, V2, V3, V4) -> T,
    v1Getter: (S) -> V1,
    v2Getter: (S) -> V2,
    v3Getter: (S) -> V3,
    v4Getter: (S) -> V4
) = defConstructor { function.invoke(v1Getter(it), v2Getter(it), v3Getter(it), v4Getter(it)) }

//Bidirectional

//Constructors with 0 arguments!

fun <S : Any, T : Any> BidirectionalMappingBuilder<S, T>.defSourceConstructor(constructor: () -> S) = defSourceConstructor { constructor() }
fun <S : Any, T : Any> BidirectionalMappingBuilder<S, T>.defTargetConstructor(constructor: () -> T) = defTargetConstructor { constructor() }

//Constructors with 1 arguments!

fun <S : Any, T : Any, V1> BidirectionalMappingBuilder<S, T>.defSourceConstructor(function: (V1) -> S, v1Getter: (T) -> V1) =
    defSourceConstructor { function.invoke(v1Getter(it)) }

fun <S : Any, T : Any, V1> BidirectionalMappingBuilder<S, T>.defTargetConstructor(function: (V1) -> T, v1Getter: (S) -> V1) =
    defTargetConstructor { function.invoke(v1Getter(it)) }

//Constructors with 2 arguments!

fun <S : Any, T : Any, V1, V2> BidirectionalMappingBuilder<S, T>.defSourceConstructor(function: (V1, V2) -> S, v1Getter: (T) -> V1, v2Getter: (T) -> V2) =
    defSourceConstructor { function.invoke(v1Getter(it), v2Getter(it)) }

fun <S : Any, T : Any, V1, V2> BidirectionalMappingBuilder<S, T>.defTargetConstructor(function: (V1, V2) -> T, v1Getter: (S) -> V1, v2Getter: (S) -> V2) =
    defTargetConstructor { function.invoke(v1Getter(it), v2Getter(it)) }

//Constructors with 3 arguments!

fun <S : Any, T : Any, V1, V2, V3> BidirectionalMappingBuilder<S, T>.defSourceConstructor(
    function: (V1, V2, V3) -> S,
    v1Getter: (T) -> V1,
    v2Getter: (T) -> V2,
    v3Getter: (T) -> V3
) = defSourceConstructor { function.invoke(v1Getter(it), v2Getter(it), v3Getter(it)) }

fun <S : Any, T : Any, V1, V2, V3> BidirectionalMappingBuilder<S, T>.defTargetConstructor(
    function: (V1, V2, V3) -> T,
    v1Getter: (S) -> V1,
    v2Getter: (S) -> V2,
    v3Getter: (S) -> V3
) = defTargetConstructor { function.invoke(v1Getter(it), v2Getter(it), v3Getter(it)) }

//Constructors with 4 arguments!

fun <S : Any, T : Any, V1, V2, V3, V4> BidirectionalMappingBuilder<S, T>.defSourceConstructor(
    function: (V1, V2, V3, V4) -> S,
    v1Getter: (T) -> V1,
    v2Getter: (T) -> V2,
    v3Getter: (T) -> V3,
    v4Getter: (T) -> V4
) = defSourceConstructor { function.invoke(v1Getter(it), v2Getter(it), v3Getter(it), v4Getter(it)) }

fun <S : Any, T : Any, V1, V2, V3, V4> BidirectionalMappingBuilder<S, T>.defTargetConstructor(
    function: (V1, V2, V3, V4) -> T,
    v1Getter: (S) -> V1,
    v2Getter: (S) -> V2,
    v3Getter: (S) -> V3,
    v4Getter: (S) -> V4
) = defTargetConstructor { function.invoke(v1Getter(it), v2Getter(it), v3Getter(it), v4Getter(it)) }