@file:Suppress("unused")
@file:JvmName("ReadWritePathConversionUtil")

package de.kotlinBerlin.kMapper.extensions

import de.kotlinBerlin.kMapper.ReadWritePath

import kotlin.jvm.JvmName
import kotlin.reflect.KMutableProperty1

//Not Null

//Path

fun <S, V : Any, V1 : Any> ReadWritePath<S, V?, V1>.notNull(): ReadWritePath<S, V, V1?> = readNotNull().writeNotNull()

fun <S, V : Any, V1 : Any> ReadWritePath<S, V?, V1>.notNull(exceptionBlock: () -> Nothing): ReadWritePath<S, V, V1?> =
    readNotNull(exceptionBlock).writeNotNull(exceptionBlock)

//Property

fun <S, V : Any> KMutableProperty1<S, V?>.notNull(): ReadWritePath<S, V, V?> = path.notNull()

fun <S, V : Any> KMutableProperty1<S, V?>.notNull(exceptionBlock: () -> Nothing): ReadWritePath<S, V, V?> = path.notNull(exceptionBlock)

//Map when not null

//Path

fun <S, V : Any, V1 : Any> ReadWritePath<S, V?, V1>.ifPresent(): ReadWritePath<S, V, V1?> = readIfPresent().writeIfPresent()

//Property

fun <S, V : Any> KMutableProperty1<S, V?>.ifPresent(): ReadWritePath<S, V, V?> = path.ifPresent()

//Default value

//Path

fun <S, V : Any, V1 : Any> ReadWritePath<S, V?, V1>.withDefault(readDefault: V, writeDefault: V1): ReadWritePath<S, V, V1?> =
    readWithDefault(readDefault).writeWithDefault(writeDefault)

fun <S, V : Any> ReadWritePath<S, V?, V>.withDefault(default: V): ReadWritePath<S, V, V?> =
    readWithDefault(default).writeWithDefault(default)

fun <S, V : Any, V1 : Any> ReadWritePath<S, V?, V1>.withDefault(readDefaultBlock: () -> V, writeDefaultBlock: () -> V1): ReadWritePath<S, V, V1?> =
    readWithDefault(readDefaultBlock).writeWithDefault(writeDefaultBlock)

fun <S, V : Any> ReadWritePath<S, V?, V>.withDefault(defaultBlock: () -> V): ReadWritePath<S, V, V?> =
    readWithDefault(defaultBlock).writeWithDefault(defaultBlock)

//Property

fun <S, V : Any> KMutableProperty1<S, V?>.withDefault(default: V): ReadWritePath<S, V, V?> =
    path.withDefault(default)

fun <S, V : Any> KMutableProperty1<S, V?>.withDefault(defaultBlock: () -> V): ReadWritePath<S, V, V?> =
    path.withDefault(defaultBlock)

//Double

//Path

@JvmName("doubleAsString")
fun <S> ReadWritePath<S, Double, Double>.asString(): ReadWritePath<S, String, String> =
    readAsString().writeFromStr()

fun <S> ReadWritePath<S, String, String>.asDouble(): ReadWritePath<S, Double, Double> =
    readAsDouble().writeFromAny()

@JvmName("doubleAsStringOrNull")
fun <S> ReadWritePath<S, Double, Double?>.asStringOrNull(): ReadWritePath<S, String, String> =
    readAsString().writeFromStrOrNull()

fun <S> ReadWritePath<S, String, String>.asDoubleOrNull(): ReadWritePath<S, Double?, Double> =
    readAsDoubleOrNull().writeFromAny()

@JvmName("doubleAsNullableString")
fun <S> ReadWritePath<S, Double?, Double?>.asString(): ReadWritePath<S, String?, String?> =
    readAsString().writeFromStr()

@JvmName("asNullableDouble")
fun <S> ReadWritePath<S, String?, String?>.asDouble(): ReadWritePath<S, Double?, Double?> =
    readAsDouble().writeFromAny()

@JvmName("doubleAsNullableStringOrNull")
fun <S> ReadWritePath<S, Double?, Double?>.asStringOrNull(): ReadWritePath<S, String?, String?> =
    readAsString().writeFromNullableStrOrNull()

@JvmName("asNullableDoubleOrNull")
fun <S> ReadWritePath<S, String?, String?>.asDoubleOrNull(): ReadWritePath<S, Double?, Double?> =
    readAsDoubleOrNull().writeFromAny()

//Property

@JvmName("doubleAsString")
fun <S> KMutableProperty1<S, Double>.asString(): ReadWritePath<S, String, String> =
    path.asString()

fun <S> KMutableProperty1<S, String>.asDouble(): ReadWritePath<S, Double, Double> =
    path.asDouble()

@JvmName("doubleAsNullableString")
fun <S> KMutableProperty1<S, Double?>.asString(): ReadWritePath<S, String?, String?> =
    path.asString()

@JvmName("asNullableDouble")
fun <S> KMutableProperty1<S, String?>.asDouble(): ReadWritePath<S, Double?, Double?> =
    path.asDouble()

@JvmName("doubleAsNullableStringOrNull")
fun <S> KMutableProperty1<S, Double?>.asStringOrNull(): ReadWritePath<S, String?, String?> =
    path.asStringOrNull()

fun <S> KMutableProperty1<S, String?>.asDoubleOrNull(): ReadWritePath<S, Double?, Double?> =
    path.asDoubleOrNull()

//Float

//Path

@JvmName("floatAsString")
fun <S> ReadWritePath<S, Float, Float>.asString(): ReadWritePath<S, String, String> =
    readAsString().writeFromStr()

fun <S> ReadWritePath<S, String, String>.asFloat(): ReadWritePath<S, Float, Float> =
    readAsFloat().writeFromAny()

@JvmName("floatAsStringOrNull")
fun <S> ReadWritePath<S, Float, Float?>.asStringOrNull(): ReadWritePath<S, String, String> =
    readAsString().writeFromStrOrNull()

fun <S> ReadWritePath<S, String, String>.asFloatOrNull(): ReadWritePath<S, Float?, Float> =
    readAsFloatOrNull().writeFromAny()

@JvmName("floatAsNullableString")
fun <S> ReadWritePath<S, Float?, Float?>.asString(): ReadWritePath<S, String?, String?> =
    readAsString().writeFromStr()

@JvmName("asNullableFloat")
fun <S> ReadWritePath<S, String?, String?>.asFloat(): ReadWritePath<S, Float?, Float?> =
    readAsFloat().writeFromAny()

@JvmName("floatAsNullableStringOrNull")
fun <S> ReadWritePath<S, Float?, Float?>.asStringOrNull(): ReadWritePath<S, String?, String?> =
    readAsString().writeFromNullableStrOrNull()

@JvmName("asNullableFloatOrNull")
fun <S> ReadWritePath<S, String?, String?>.asFloatOrNull(): ReadWritePath<S, Float?, Float?> =
    readAsFloatOrNull().writeFromAny()

//Property

@JvmName("floatAsString")
fun <S> KMutableProperty1<S, Float>.asString(): ReadWritePath<S, String, String> =
    path.asString()

fun <S> KMutableProperty1<S, String>.asFloat(): ReadWritePath<S, Float, Float> =
    path.asFloat()

@JvmName("floatAsNullableString")
fun <S> KMutableProperty1<S, Float?>.asString(): ReadWritePath<S, String?, String?> =
    path.asString()

@JvmName("asNullableFloat")
fun <S> KMutableProperty1<S, String?>.asFloat(): ReadWritePath<S, Float?, Float?> =
    path.asFloat()

@JvmName("floatAsNullableStringOrNull")
fun <S> KMutableProperty1<S, Float?>.asStringOrNull(): ReadWritePath<S, String?, String?> =
    path.asStringOrNull()

fun <S> KMutableProperty1<S, String?>.asFloatOrNull(): ReadWritePath<S, Float?, Float?> =
    path.asFloatOrNull()

//Long

//Path

@JvmName("longAsString")
fun <S> ReadWritePath<S, Long, Long>.asString(): ReadWritePath<S, String, String> =
    readAsString().writeFromStr()

fun <S> ReadWritePath<S, String, String>.asLong(): ReadWritePath<S, Long, Long> =
    readAsLong().writeFromAny()

@JvmName("longAsStringOrNull")
fun <S> ReadWritePath<S, Long, Long?>.asStringOrNull(): ReadWritePath<S, String, String> =
    readAsString().writeFromStrOrNull()

fun <S> ReadWritePath<S, String, String>.asLongOrNull(): ReadWritePath<S, Long?, Long> =
    readAsLongOrNull().writeFromAny()

@JvmName("longAsNullableString")
fun <S> ReadWritePath<S, Long?, Long?>.asString(): ReadWritePath<S, String?, String?> =
    readAsString().writeFromStr()

@JvmName("asNullableLong")
fun <S> ReadWritePath<S, String?, String?>.asLong(): ReadWritePath<S, Long?, Long?> =
    readAsLong().writeFromAny()

@JvmName("longAsNullableStringOrNull")
fun <S> ReadWritePath<S, Long?, Long?>.asStringOrNull(): ReadWritePath<S, String?, String?> =
    readAsString().writeFromNullableStrOrNull()

@JvmName("asNullableLongOrNull")
fun <S> ReadWritePath<S, String?, String?>.asLongOrNull(): ReadWritePath<S, Long?, Long?> =
    readAsLongOrNull().writeFromAny()

//Property

@JvmName("longAsString")
fun <S> KMutableProperty1<S, Long>.asString(): ReadWritePath<S, String, String> =
    path.asString()

fun <S> KMutableProperty1<S, String>.asLong(): ReadWritePath<S, Long, Long> =
    path.asLong()

@JvmName("longAsNullableString")
fun <S> KMutableProperty1<S, Long?>.asString(): ReadWritePath<S, String?, String?> =
    path.asString()

@JvmName("asNullableLong")
fun <S> KMutableProperty1<S, String?>.asLong(): ReadWritePath<S, Long?, Long?> =
    path.asLong()

@JvmName("longAsNullableStringOrNull")
fun <S> KMutableProperty1<S, Long?>.asStringOrNull(): ReadWritePath<S, String?, String?> =
    path.asStringOrNull()

fun <S> KMutableProperty1<S, String?>.asLongOrNull(): ReadWritePath<S, Long?, Long?> =
    path.asLongOrNull()

//Int

//Path

@JvmName("intAsString")
fun <S> ReadWritePath<S, Int, Int>.asString(): ReadWritePath<S, String, String> =
    readAsString().writeFromStr()

fun <S> ReadWritePath<S, String, String>.asInt(): ReadWritePath<S, Int, Int> =
    readAsInt().writeFromAny()

@JvmName("intAsStringOrNull")
fun <S> ReadWritePath<S, Int, Int?>.asStringOrNull(): ReadWritePath<S, String, String> =
    readAsString().writeFromStrOrNull()

fun <S> ReadWritePath<S, String, String>.asIntOrNull(): ReadWritePath<S, Int?, Int> =
    readAsIntOrNull().writeFromAny()

@JvmName("intAsNullableString")
fun <S> ReadWritePath<S, Int?, Int?>.asString(): ReadWritePath<S, String?, String?> =
    readAsString().writeFromStr()

@JvmName("asNullableInt")
fun <S> ReadWritePath<S, String?, String?>.asInt(): ReadWritePath<S, Int?, Int?> =
    readAsInt().writeFromAny()

@JvmName("intAsNullableStringOrNull")
fun <S> ReadWritePath<S, Int?, Int?>.asStringOrNull(): ReadWritePath<S, String?, String?> =
    readAsString().writeFromNullableStrOrNull()

@JvmName("asNullableIntOrNull")
fun <S> ReadWritePath<S, String?, String?>.asIntOrNull(): ReadWritePath<S, Int?, Int?> =
    readAsIntOrNull().writeFromAny()

//Property

@JvmName("intAsString")
fun <S> KMutableProperty1<S, Int>.asString(): ReadWritePath<S, String, String> =
    path.asString()

fun <S> KMutableProperty1<S, String>.asInt(): ReadWritePath<S, Int, Int> =
    path.asInt()

@JvmName("intAsNullableString")
fun <S> KMutableProperty1<S, Int?>.asString(): ReadWritePath<S, String?, String?> =
    path.asString()

@JvmName("asNullableInt")
fun <S> KMutableProperty1<S, String?>.asInt(): ReadWritePath<S, Int?, Int?> =
    path.asInt()

@JvmName("intAsNullableStringOrNull")
fun <S> KMutableProperty1<S, Int?>.asStringOrNull(): ReadWritePath<S, String?, String?> =
    path.asStringOrNull()

fun <S> KMutableProperty1<S, String?>.asIntOrNull(): ReadWritePath<S, Int?, Int?> =
    path.asIntOrNull()

//Short

//Path

@JvmName("shortAsString")
fun <S> ReadWritePath<S, Short, Short>.asString(): ReadWritePath<S, String, String> =
    readAsString().writeFromStr()

fun <S> ReadWritePath<S, String, String>.asShort(): ReadWritePath<S, Short, Short> =
    readAsShort().writeFromAny()

@JvmName("shortAsStringOrNull")
fun <S> ReadWritePath<S, Short, Short?>.asStringOrNull(): ReadWritePath<S, String, String> =
    readAsString().writeFromStrOrNull()

fun <S> ReadWritePath<S, String, String>.asShortOrNull(): ReadWritePath<S, Short?, Short> =
    readAsShortOrNull().writeFromAny()

@JvmName("shortAsNullableString")
fun <S> ReadWritePath<S, Short?, Short?>.asString(): ReadWritePath<S, String?, String?> =
    readAsString().writeFromStr()

@JvmName("asNullableShort")
fun <S> ReadWritePath<S, String?, String?>.asShort(): ReadWritePath<S, Short?, Short?> =
    readAsShort().writeFromAny()

@JvmName("shortAsNullableStringOrNull")
fun <S> ReadWritePath<S, Short?, Short?>.asStringOrNull(): ReadWritePath<S, String?, String?> =
    readAsString().writeFromNullableStrOrNull()

@JvmName("asNullableShortOrNull")
fun <S> ReadWritePath<S, String?, String?>.asShortOrNull(): ReadWritePath<S, Short?, Short?> =
    readAsShortOrNull().writeFromAny()

//Property

@JvmName("shortAsString")
fun <S> KMutableProperty1<S, Short>.asString(): ReadWritePath<S, String, String> =
    path.asString()

fun <S> KMutableProperty1<S, String>.asShort(): ReadWritePath<S, Short, Short> =
    path.asShort()

@JvmName("shortAsNullableString")
fun <S> KMutableProperty1<S, Short?>.asString(): ReadWritePath<S, String?, String?> =
    path.asString()

@JvmName("asNullableShort")
fun <S> KMutableProperty1<S, String?>.asShort(): ReadWritePath<S, Short?, Short?> =
    path.asShort()

@JvmName("shortAsNullableStringOrNull")
fun <S> KMutableProperty1<S, Short?>.asStringOrNull(): ReadWritePath<S, String?, String?> =
    path.asStringOrNull()

fun <S> KMutableProperty1<S, String?>.asShortOrNull(): ReadWritePath<S, Short?, Short?> =
    path.asShortOrNull()

//Byte

//Path

@JvmName("byteAsString")
fun <S> ReadWritePath<S, Byte, Byte>.asString(): ReadWritePath<S, String, String> =
    readAsString().writeFromStr()

fun <S> ReadWritePath<S, String, String>.asByte(): ReadWritePath<S, Byte, Byte> =
    readAsByte().writeFromAny()

@JvmName("byteAsStringOrNull")
fun <S> ReadWritePath<S, Byte, Byte?>.asStringOrNull(): ReadWritePath<S, String, String> =
    readAsString().writeFromStrOrNull()

fun <S> ReadWritePath<S, String, String>.asByteOrNull(): ReadWritePath<S, Byte?, Byte> =
    readAsByteOrNull().writeFromAny()

@JvmName("byteAsNullableString")
fun <S> ReadWritePath<S, Byte?, Byte?>.asString(): ReadWritePath<S, String?, String?> =
    readAsString().writeFromStr()

@JvmName("asNullableByte")
fun <S> ReadWritePath<S, String?, String?>.asByte(): ReadWritePath<S, Byte?, Byte?> =
    readAsByte().writeFromAny()

@JvmName("byteAsNullableStringOrNull")
fun <S> ReadWritePath<S, Byte?, Byte?>.asStringOrNull(): ReadWritePath<S, String?, String?> =
    readAsString().writeFromNullableStrOrNull()

@JvmName("asNullableByteOrNull")
fun <S> ReadWritePath<S, String?, String?>.asByteOrNull(): ReadWritePath<S, Byte?, Byte?> =
    readAsByteOrNull().writeFromAny()

//Property

@JvmName("byteAsString")
fun <S> KMutableProperty1<S, Byte>.asString(): ReadWritePath<S, String, String> =
    path.asString()

fun <S> KMutableProperty1<S, String>.asByte(): ReadWritePath<S, Byte, Byte> =
    path.asByte()

@JvmName("byteAsNullableString")
fun <S> KMutableProperty1<S, Byte?>.asString(): ReadWritePath<S, String?, String?> =
    path.asString()

@JvmName("asNullableByte")
fun <S> KMutableProperty1<S, String?>.asByte(): ReadWritePath<S, Byte?, Byte?> =
    path.asByte()

@JvmName("byteAsNullableStringOrNull")
fun <S> KMutableProperty1<S, Byte?>.asStringOrNull(): ReadWritePath<S, String?, String?> =
    path.asStringOrNull()

fun <S> KMutableProperty1<S, String?>.asByteOrNull(): ReadWritePath<S, Byte?, Byte?> =
    path.asByteOrNull()