@file:Suppress("unused")
@file:JvmName("ReadPathConversionUtil")

package de.kotlinBerlin.kMapper.extensions

import de.kotlinBerlin.kMapper.*
import de.kotlinBerlin.kMapper.internal.IgnoreMappingException
import de.kotlinBerlin.kMapper.toReadWritePath
import kotlin.jvm.JvmName
import kotlin.reflect.KMutableProperty1

//Non Null

//Path

fun <S, V : Any, V1> ReadWritePath<S, V?, V1>.readNotNull(): ReadWritePath<S, V, V1> = readConverted { it!! }

fun <S, V : Any, V1> ReadWritePath<S, V?, V1>.readNotNull(exceptionBlock: () -> Nothing): ReadWritePath<S, V, V1> = readConverted { it ?: exceptionBlock() }

fun <S, V : Any> ReadPath<S, V?>.readNotNull(): ReadPath<S, V> = readConverted { it!! }

fun <S, V : Any> ReadPath<S, V?>.readNotNull(exceptionBlock: () -> Nothing): ReadPath<S, V> = readConverted { it ?: exceptionBlock() }

//Property

fun <S, V : Any> KMutableProperty1<S, V?>.readNotNull(): ReadWritePath<S, V, V> = toReadWritePath().readNotNull()

fun <S, V : Any> KMutableProperty1<S, V?>.readNotNull(exceptionBlock: () -> Nothing): ReadWritePath<S, V, V> = toReadWritePath().readNotNull(exceptionBlock)

//Function

fun <S, V : Any> ((S) -> V?).readNotNull(): ReadPath<S, V> = toReadPath().readNotNull()

fun <S, V : Any> ((S) -> V?).readNotNull(exceptionBlock: () -> Nothing): ReadPath<S, V> = toReadPath().readNotNull(exceptionBlock)

//Map when not null

//Path

fun <S, V : Any, V1> ReadWritePath<S, V?, V1>.readIfPresent(): ReadWritePath<S, V, V1> = readNotNull { throw IgnoreMappingException() }

fun <S, V : Any> ReadPath<S, V?>.readIfPresent(): ReadPath<S, V> = readNotNull { throw IgnoreMappingException() }

//Property

fun <S, V : Any> KMutableProperty1<S, V?>.readIfPresent(): ReadWritePath<S, V, V> = toReadWritePath().readIfPresent()

//Function

fun <S, V : Any> ((S) -> V?).readIfPresent(): ReadPath<S, V> = toReadPath().readIfPresent()


//Default value

//Path

fun <S, V : Any, V1> ReadWritePath<S, V?, V1>.readWithDefault(aDefault: V): ReadWritePath<S, V, V1> = readConverted { it ?: aDefault }

fun <S, V : Any, V1> ReadWritePath<S, V?, V1>.readWithDefault(aDefaultBlock: () -> V): ReadWritePath<S, V, V1> = readConverted { it ?: aDefaultBlock() }

fun <S, V : Any> ReadPath<S, V?>.readWithDefault(aDefault: V): ReadPath<S, V> = readConverted { it ?: aDefault }

fun <S, V : Any> ReadPath<S, V?>.readWithDefault(aDefaultBlock: () -> V): ReadPath<S, V> = readConverted { it ?: aDefaultBlock() }

//Property

fun <S, V : Any> KMutableProperty1<S, V?>.readWithDefault(aDefault: V): ReadWritePath<S, V, V> = toReadWritePath().readWithDefault(aDefault)

fun <S, V : Any> KMutableProperty1<S, V?>.readWithDefault(aDefaultBlock: () -> V): ReadWritePath<S, V, V> = toReadWritePath().readWithDefault(aDefaultBlock)

//Function

fun <S, V : Any> ((S) -> V?).readWithDefault(aDefault: V): ReadPath<S, V> = toReadPath().readWithDefault(aDefault)

fun <S, V : Any> ((S) -> V?).readWithDefault(aDefaultBlock: () -> V): ReadPath<S, V> = toReadPath().readWithDefault(aDefaultBlock)

//String

//Path

fun <S, V : Any, V1> ReadWritePath<S, V, V1>.readAsString(): ReadWritePath<S, String, V1> = readConverted { it.toString() }

@JvmName("readAsNullableString")
fun <S, V, V1> ReadWritePath<S, V, V1>.readAsString(): ReadWritePath<S, String?, V1> = readConverted { it?.toString() }

fun <S, V : Any> ReadPath<S, V>.readAsString(): ReadPath<S, String> = readConverted { it.toString() }

@JvmName("readAsNullableString")
fun <S, V> ReadPath<S, V>.readAsString(): ReadPath<S, String?> = readConverted { it?.toString() }

//Property

fun <S, V : Any> KMutableProperty1<S, V>.readAsString(): ReadWritePath<S, String, V> = toReadWritePath().readAsString()

@JvmName("readAsNullableString")
fun <S, V> KMutableProperty1<S, V?>.readAsString(): ReadWritePath<S, String?, V> = toReadWritePath().readAsString()

//Function

fun <S, V : Any> ((S) -> V).readAsString(): ReadPath<S, String> = toReadPath().readAsString()

@JvmName("readAsNullableString")
fun <S, V> ((S) -> V).readAsString(): ReadPath<S, String?> = toReadPath().readAsString()

//Double

//Path

@JvmName("readNumAsDouble")
fun <S, V> ReadWritePath<S, Number, V>.readAsDouble(): ReadWritePath<S, Double, V> = readConverted { it.toDouble() }

@JvmName("readNumAsNullableDouble")
fun <S, V> ReadWritePath<S, Number?, V>.readAsDouble(): ReadWritePath<S, Double?, V> = readConverted { it?.toDouble() }

@JvmName("readStrAsDouble")
fun <S, V> ReadWritePath<S, String, V>.readAsDouble(): ReadWritePath<S, Double, V> = readConverted { it.toDouble() }

fun <S, V> ReadWritePath<S, String, V>.readAsDoubleOrNull(): ReadWritePath<S, Double?, V> = readConverted { it.toDoubleOrNull() }

@JvmName("readStrAsNullableDouble")
fun <S, V> ReadWritePath<S, String?, V>.readAsDouble(): ReadWritePath<S, Double?, V> = readConverted { it?.toDouble() }

@JvmName("readStrAsNullableDoubleOrNull")
fun <S, V> ReadWritePath<S, String?, V>.readAsDoubleOrNull(): ReadWritePath<S, Double?, V> = readConverted { it?.toDoubleOrNull() }

@JvmName("readNumAsDouble")
fun <S> ReadPath<S, Number>.readAsDouble(): ReadPath<S, Double> = readConverted { it.toDouble() }

@JvmName("readNumAsNullableDouble")
fun <S> ReadPath<S, Number?>.readAsDouble(): ReadPath<S, Double?> = readConverted { it?.toDouble() }

@JvmName("readStrAsDouble")
fun <S> ReadPath<S, String>.readAsDouble(): ReadPath<S, Double> = readConverted { it.toDouble() }

fun <S> ReadPath<S, String>.readAsDoubleOrNull(): ReadPath<S, Double?> = readConverted { it.toDoubleOrNull() }

@JvmName("readStrAsNullableDouble")
fun <S> ReadPath<S, String?>.readAsDouble(): ReadPath<S, Double?> = readConverted { it?.toDouble() }

@JvmName("readStrAsNullableDoubleOrNull")
fun <S> ReadPath<S, String?>.readAsDoubleOrNull(): ReadPath<S, Double?> = readConverted { it?.toDoubleOrNull() }

//Property

@JvmName("readNumAsDouble")
fun <S> KMutableProperty1<S, Number>.readAsDouble(): ReadWritePath<S, Double, Number> = toReadWritePath().readAsDouble()

@JvmName("readNumAsNullableDouble")
fun <S> KMutableProperty1<S, Number?>.readAsDouble(): ReadWritePath<S, Double?, Number?> = toReadWritePath().readAsDouble()

@JvmName("readStrAsDouble")
fun <S> KMutableProperty1<S, String>.readAsDouble(): ReadWritePath<S, Double, String> = toReadWritePath().readAsDouble()

fun <S> KMutableProperty1<S, String>.readAsDoubleOrNull(): ReadWritePath<S, Double?, String> = toReadWritePath().readAsDoubleOrNull()

@JvmName("readStrAsNullableDouble")
fun <S> KMutableProperty1<S, String?>.readAsDouble(): ReadWritePath<S, Double?, String?> = toReadWritePath().readAsDouble()

@JvmName("readStrAsNullableDoubleOrNull")
fun <S> KMutableProperty1<S, String?>.readAsDoubleOrNull(): ReadWritePath<S, Double?, String?> = toReadWritePath().readAsDoubleOrNull()

//Function

@JvmName("readNumAsDouble")
fun <S> ((S) -> Number).readAsDouble(): ReadPath<S, Double> = toReadPath().readAsDouble()

@JvmName("readNumAsNullableDouble")
fun <S> ((S) -> Number?).readAsDouble(): ReadPath<S, Double?> = toReadPath().readAsDouble()

@JvmName("readStrAsDouble")
fun <S> ((S) -> String).readAsDouble(): ReadPath<S, Double> = toReadPath().readAsDouble()

fun <S> ((S) -> String).readAsDoubleOrNull(): ReadPath<S, Double?> = toReadPath().readAsDoubleOrNull()

@JvmName("readStrAsNullableDouble")
fun <S> ((S) -> String?).readAsDouble(): ReadPath<S, Double?> = toReadPath().readAsDouble()

@JvmName("readStrAsNullableDoubleOrNull")
fun <S> ((S) -> String?).readAsDoubleOrNull(): ReadPath<S, Double?> = toReadPath().readAsDoubleOrNull()

//Float

//Path

@JvmName("readNumAsFloat")
fun <S, V> ReadWritePath<S, Number, V>.readAsFloat(): ReadWritePath<S, Float, V> = readConverted { it.toFloat() }

@JvmName("readNumAsNullableFloat")
fun <S, V> ReadWritePath<S, Number?, V>.readAsFloat(): ReadWritePath<S, Float?, V> = readConverted { it?.toFloat() }

@JvmName("readStrAsFloat")
fun <S, V> ReadWritePath<S, String, V>.readAsFloat(): ReadWritePath<S, Float, V> = readConverted { it.toFloat() }

fun <S, V> ReadWritePath<S, String, V>.readAsFloatOrNull(): ReadWritePath<S, Float?, V> = readConverted { it.toFloatOrNull() }

@JvmName("readStrAsNullableFloat")
fun <S, V> ReadWritePath<S, String?, V>.readAsFloat(): ReadWritePath<S, Float?, V> = readConverted { it?.toFloat() }

@JvmName("readStrAsNullableFloatOrNull")
fun <S, V> ReadWritePath<S, String?, V>.readAsFloatOrNull(): ReadWritePath<S, Float?, V> = readConverted { it?.toFloatOrNull() }

@JvmName("readNumAsFloat")
fun <S> ReadPath<S, Number>.readAsFloat(): ReadPath<S, Float> = readConverted { it.toFloat() }

@JvmName("readNumAsNullableFloat")
fun <S> ReadPath<S, Number?>.readAsFloat(): ReadPath<S, Float?> = readConverted { it?.toFloat() }

@JvmName("readStrAsFloat")
fun <S> ReadPath<S, String>.readAsFloat(): ReadPath<S, Float> = readConverted { it.toFloat() }

fun <S> ReadPath<S, String>.readAsFloatOrNull(): ReadPath<S, Float?> = readConverted { it.toFloatOrNull() }

@JvmName("readStrAsNullableFloat")
fun <S> ReadPath<S, String?>.readAsFloat(): ReadPath<S, Float?> = readConverted { it?.toFloat() }

@JvmName("readStrAsNullableFloatOrNull")
fun <S> ReadPath<S, String?>.readAsFloatOrNull(): ReadPath<S, Float?> = readConverted { it?.toFloatOrNull() }

//Property

@JvmName("readNumAsFloat")
fun <S> KMutableProperty1<S, Number>.readAsFloat(): ReadWritePath<S, Float, Number> = toReadWritePath().readAsFloat()

@JvmName("readNumAsNullableFloat")
fun <S> KMutableProperty1<S, Number?>.readAsFloat(): ReadWritePath<S, Float?, Number?> = toReadWritePath().readAsFloat()

@JvmName("readStrAsFloat")
fun <S> KMutableProperty1<S, String>.readAsFloat(): ReadWritePath<S, Float, String> = toReadWritePath().readAsFloat()

fun <S> KMutableProperty1<S, String>.readAsFloatOrNull(): ReadWritePath<S, Float?, String> = toReadWritePath().readAsFloatOrNull()

@JvmName("readStrAsNullableFloat")
fun <S> KMutableProperty1<S, String?>.readAsFloat(): ReadWritePath<S, Float?, String?> = toReadWritePath().readAsFloat()

@JvmName("readStrAsNullableFloatOrNull")
fun <S> KMutableProperty1<S, String?>.readAsFloatOrNull(): ReadWritePath<S, Float?, String?> = toReadWritePath().readAsFloatOrNull()

//Function

@JvmName("readNumAsFloat")
fun <S> ((S) -> Number).readAsFloat(): ReadPath<S, Float> = toReadPath().readAsFloat()

@JvmName("readNumAsNullableFloat")
fun <S> ((S) -> Number?).readAsFloat(): ReadPath<S, Float?> = toReadPath().readAsFloat()

@JvmName("readStrAsFloat")
fun <S> ((S) -> String).readAsFloat(): ReadPath<S, Float> = toReadPath().readAsFloat()

fun <S> ((S) -> String).readAsFloatOrNull(): ReadPath<S, Float?> = toReadPath().readAsFloatOrNull()

@JvmName("readStrAsNullableFloat")
fun <S> ((S) -> String?).readAsFloat(): ReadPath<S, Float?> = toReadPath().readAsFloat()

@JvmName("readStrAsNullableFloatOrNull")
fun <S> ((S) -> String?).readAsFloatOrNull(): ReadPath<S, Float?> = toReadPath().readAsFloatOrNull()

//Long

//Path

@JvmName("readNumAsLong")
fun <S, V> ReadWritePath<S, Number, V>.readAsLong(): ReadWritePath<S, Long, V> = readConverted { it.toLong() }

@JvmName("readNumAsNullableLong")
fun <S, V> ReadWritePath<S, Number?, V>.readAsLong(): ReadWritePath<S, Long?, V> = readConverted { it?.toLong() }

@JvmName("readStrAsLong")
fun <S, V> ReadWritePath<S, String, V>.readAsLong(): ReadWritePath<S, Long, V> = readConverted { it.toLong() }

fun <S, V> ReadWritePath<S, String, V>.readAsLongOrNull(): ReadWritePath<S, Long?, V> = readConverted { it.toLongOrNull() }

@JvmName("readStrAsNullableLong")
fun <S, V> ReadWritePath<S, String?, V>.readAsLong(): ReadWritePath<S, Long?, V> = readConverted { it?.toLong() }

@JvmName("readStrAsNullableLongOrNull")
fun <S, V> ReadWritePath<S, String?, V>.readAsLongOrNull(): ReadWritePath<S, Long?, V> = readConverted { it?.toLongOrNull() }

@JvmName("readNumAsLong")
fun <S> ReadPath<S, Number>.readAsLong(): ReadPath<S, Long> = readConverted { it.toLong() }

@JvmName("readNumAsNullableLong")
fun <S> ReadPath<S, Number?>.readAsLong(): ReadPath<S, Long?> = readConverted { it?.toLong() }

@JvmName("readStrAsLong")
fun <S> ReadPath<S, String>.readAsLong(): ReadPath<S, Long> = readConverted { it.toLong() }

fun <S> ReadPath<S, String>.readAsLongOrNull(): ReadPath<S, Long?> = readConverted { it.toLongOrNull() }

@JvmName("readStrAsNullableLong")
fun <S> ReadPath<S, String?>.readAsLong(): ReadPath<S, Long?> = readConverted { it?.toLong() }

@JvmName("readStrAsNullableLongOrNull")
fun <S> ReadPath<S, String?>.readAsLongOrNull(): ReadPath<S, Long?> = readConverted { it?.toLongOrNull() }

//Property

@JvmName("readNumAsLong")
fun <S> KMutableProperty1<S, Number>.readAsLong(): ReadWritePath<S, Long, Number> = toReadWritePath().readAsLong()

@JvmName("readNumAsNullableLong")
fun <S> KMutableProperty1<S, Number?>.readAsLong(): ReadWritePath<S, Long?, Number?> = toReadWritePath().readAsLong()

@JvmName("readStrAsLong")
fun <S> KMutableProperty1<S, String>.readAsLong(): ReadWritePath<S, Long, String> = toReadWritePath().readAsLong()

fun <S> KMutableProperty1<S, String>.readAsLongOrNull(): ReadWritePath<S, Long?, String> = toReadWritePath().readAsLongOrNull()

@JvmName("readStrAsNullableLong")
fun <S> KMutableProperty1<S, String?>.readAsLong(): ReadWritePath<S, Long?, String?> = toReadWritePath().readAsLong()

@JvmName("readStrAsNullableLongOrNull")
fun <S> KMutableProperty1<S, String?>.readAsLongOrNull(): ReadWritePath<S, Long?, String?> = toReadWritePath().readAsLongOrNull()

//Function

@JvmName("readNumAsLong")
fun <S> ((S) -> Number).readAsLong(): ReadPath<S, Long> = toReadPath().readAsLong()

@JvmName("readNumAsNullableLong")
fun <S> ((S) -> Number?).readAsLong(): ReadPath<S, Long?> = toReadPath().readAsLong()

@JvmName("readStrAsLong")
fun <S> ((S) -> String).readAsLong(): ReadPath<S, Long> = toReadPath().readAsLong()

fun <S> ((S) -> String).readAsLongOrNull(): ReadPath<S, Long?> = toReadPath().readAsLongOrNull()

@JvmName("readStrAsNullableLong")
fun <S> ((S) -> String?).readAsLong(): ReadPath<S, Long?> = toReadPath().readAsLong()

@JvmName("readStrAsNullableLongOrNull")
fun <S> ((S) -> String?).readAsLongOrNull(): ReadPath<S, Long?> = toReadPath().readAsLongOrNull()

//Int

//Path

@JvmName("readNumAsInt")
fun <S, V> ReadWritePath<S, Number, V>.readAsInt(): ReadWritePath<S, Int, V> = readConverted { it.toInt() }

@JvmName("readNumAsNullableInt")
fun <S, V> ReadWritePath<S, Number?, V>.readAsInt(): ReadWritePath<S, Int?, V> = readConverted { it?.toInt() }

@JvmName("readStrAsInt")
fun <S, V> ReadWritePath<S, String, V>.readAsInt(): ReadWritePath<S, Int, V> = readConverted { it.toInt() }

fun <S, V> ReadWritePath<S, String, V>.readAsIntOrNull(): ReadWritePath<S, Int?, V> = readConverted { it.toIntOrNull() }

@JvmName("readStrAsNullableInt")
fun <S, V> ReadWritePath<S, String?, V>.readAsInt(): ReadWritePath<S, Int?, V> = readConverted { it?.toInt() }

@JvmName("readStrAsNullableIntOrNull")
fun <S, V> ReadWritePath<S, String?, V>.readAsIntOrNull(): ReadWritePath<S, Int?, V> = readConverted { it?.toIntOrNull() }

@JvmName("readNumAsInt")
fun <S> ReadPath<S, Number>.readAsInt(): ReadPath<S, Int> = readConverted { it.toInt() }

@JvmName("readNumAsNullableInt")
fun <S> ReadPath<S, Number?>.readAsInt(): ReadPath<S, Int?> = readConverted { it?.toInt() }

@JvmName("readStrAsInt")
fun <S> ReadPath<S, String>.readAsInt(): ReadPath<S, Int> = readConverted { it.toInt() }

fun <S> ReadPath<S, String>.readAsIntOrNull(): ReadPath<S, Int?> = readConverted { it.toIntOrNull() }

@JvmName("readStrAsNullableInt")
fun <S> ReadPath<S, String?>.readAsInt(): ReadPath<S, Int?> = readConverted { it?.toInt() }

@JvmName("readStrAsNullableIntOrNull")
fun <S> ReadPath<S, String?>.readAsIntOrNull(): ReadPath<S, Int?> = readConverted { it?.toIntOrNull() }

//Property

@JvmName("readNumAsInt")
fun <S> KMutableProperty1<S, Number>.readAsInt(): ReadWritePath<S, Int, Number> = toReadWritePath().readAsInt()

@JvmName("readNumAsNullableInt")
fun <S> KMutableProperty1<S, Number?>.readAsInt(): ReadWritePath<S, Int?, Number?> = toReadWritePath().readAsInt()

@JvmName("readStrAsInt")
fun <S> KMutableProperty1<S, String>.readAsInt(): ReadWritePath<S, Int, String> = toReadWritePath().readAsInt()

fun <S> KMutableProperty1<S, String>.readAsIntOrNull(): ReadWritePath<S, Int?, String> = toReadWritePath().readAsIntOrNull()

@JvmName("readStrAsNullableInt")
fun <S> KMutableProperty1<S, String?>.readAsInt(): ReadWritePath<S, Int?, String?> = toReadWritePath().readAsInt()

@JvmName("readStrAsNullableIntOrNull")
fun <S> KMutableProperty1<S, String?>.readAsIntOrNull(): ReadWritePath<S, Int?, String?> = toReadWritePath().readAsIntOrNull()

//Function

@JvmName("readNumAsInt")
fun <S> ((S) -> Number).readAsInt(): ReadPath<S, Int> = toReadPath().readAsInt()

@JvmName("readNumAsNullableInt")
fun <S> ((S) -> Number?).readAsInt(): ReadPath<S, Int?> = toReadPath().readAsInt()

@JvmName("readStrAsInt")
fun <S> ((S) -> String).readAsInt(): ReadPath<S, Int> = toReadPath().readAsInt()

fun <S> ((S) -> String).readAsIntOrNull(): ReadPath<S, Int?> = toReadPath().readAsIntOrNull()

@JvmName("readStrAsNullableInt")
fun <S> ((S) -> String?).readAsInt(): ReadPath<S, Int?> = toReadPath().readAsInt()

@JvmName("readStrAsNullableIntOrNull")
fun <S> ((S) -> String?).readAsIntOrNull(): ReadPath<S, Int?> = toReadPath().readAsIntOrNull()

//Short

//Path

@JvmName("readNumAsShort")
fun <S, V> ReadWritePath<S, Number, V>.readAsShort(): ReadWritePath<S, Short, V> = readConverted { it.toShort() }

@JvmName("readNumAsNullableShort")
fun <S, V> ReadWritePath<S, Number?, V>.readAsShort(): ReadWritePath<S, Short?, V> = readConverted { it?.toShort() }

@JvmName("readStrAsShort")
fun <S, V> ReadWritePath<S, String, V>.readAsShort(): ReadWritePath<S, Short, V> = readConverted { it.toShort() }

fun <S, V> ReadWritePath<S, String, V>.readAsShortOrNull(): ReadWritePath<S, Short?, V> = readConverted { it.toShortOrNull() }

@JvmName("readStrAsNullableShort")
fun <S, V> ReadWritePath<S, String?, V>.readAsShort(): ReadWritePath<S, Short?, V> = readConverted { it?.toShort() }

@JvmName("readStrAsNullableShortOrNull")
fun <S, V> ReadWritePath<S, String?, V>.readAsShortOrNull(): ReadWritePath<S, Short?, V> = readConverted { it?.toShortOrNull() }

@JvmName("readNumAsShort")
fun <S> ReadPath<S, Number>.readAsShort(): ReadPath<S, Short> = readConverted { it.toShort() }

@JvmName("readNumAsNullableShort")
fun <S> ReadPath<S, Number?>.readAsShort(): ReadPath<S, Short?> = readConverted { it?.toShort() }

@JvmName("readStrAsShort")
fun <S> ReadPath<S, String>.readAsShort(): ReadPath<S, Short> = readConverted { it.toShort() }

fun <S> ReadPath<S, String>.readAsShortOrNull(): ReadPath<S, Short?> = readConverted { it.toShortOrNull() }

@JvmName("readStrAsNullableShort")
fun <S> ReadPath<S, String?>.readAsShort(): ReadPath<S, Short?> = readConverted { it?.toShort() }

@JvmName("readStrAsNullableShortOrNull")
fun <S> ReadPath<S, String?>.readAsShortOrNull(): ReadPath<S, Short?> = readConverted { it?.toShortOrNull() }

//Property

@JvmName("readNumAsShort")
fun <S> KMutableProperty1<S, Number>.readAsShort(): ReadWritePath<S, Short, Number> = toReadWritePath().readAsShort()

@JvmName("readNumAsNullableShort")
fun <S> KMutableProperty1<S, Number?>.readAsShort(): ReadWritePath<S, Short?, Number?> = toReadWritePath().readAsShort()

@JvmName("readStrAsShort")
fun <S> KMutableProperty1<S, String>.readAsShort(): ReadWritePath<S, Short, String> = toReadWritePath().readAsShort()

fun <S> KMutableProperty1<S, String>.readAsShortOrNull(): ReadWritePath<S, Short?, String> = toReadWritePath().readAsShortOrNull()

@JvmName("readStrAsNullableShort")
fun <S> KMutableProperty1<S, String?>.readAsShort(): ReadWritePath<S, Short?, String?> = toReadWritePath().readAsShort()

@JvmName("readStrAsNullableShortOrNull")
fun <S> KMutableProperty1<S, String?>.readAsShortOrNull(): ReadWritePath<S, Short?, String?> = toReadWritePath().readAsShortOrNull()

//Function

@JvmName("readNumAsShort")
fun <S> ((S) -> Number).readAsShort(): ReadPath<S, Short> = toReadPath().readAsShort()

@JvmName("readNumAsNullableShort")
fun <S> ((S) -> Number?).readAsShort(): ReadPath<S, Short?> = toReadPath().readAsShort()

@JvmName("readStrAsShort")
fun <S> ((S) -> String).readAsShort(): ReadPath<S, Short> = toReadPath().readAsShort()

fun <S> ((S) -> String).readAsShortOrNull(): ReadPath<S, Short?> = toReadPath().readAsShortOrNull()

@JvmName("readStrAsNullableShort")
fun <S> ((S) -> String?).readAsShort(): ReadPath<S, Short?> = toReadPath().readAsShort()

@JvmName("readStrAsNullableShortOrNull")
fun <S> ((S) -> String?).readAsShortOrNull(): ReadPath<S, Short?> = toReadPath().readAsShortOrNull()

//Byte

//Path

@JvmName("readNumAsByte")
fun <S, V> ReadWritePath<S, Number, V>.readAsByte(): ReadWritePath<S, Byte, V> = readConverted { it.toByte() }

@JvmName("readNumAsNullableByte")
fun <S, V> ReadWritePath<S, Number?, V>.readAsByte(): ReadWritePath<S, Byte?, V> = readConverted { it?.toByte() }

@JvmName("readStrAsByte")
fun <S, V> ReadWritePath<S, String, V>.readAsByte(): ReadWritePath<S, Byte, V> = readConverted { it.toByte() }

fun <S, V> ReadWritePath<S, String, V>.readAsByteOrNull(): ReadWritePath<S, Byte?, V> = readConverted { it.toByteOrNull() }

@JvmName("readStrAsNullableByte")
fun <S, V> ReadWritePath<S, String?, V>.readAsByte(): ReadWritePath<S, Byte?, V> = readConverted { it?.toByte() }

@JvmName("readStrAsNullableByteOrNull")
fun <S, V> ReadWritePath<S, String?, V>.readAsByteOrNull(): ReadWritePath<S, Byte?, V> = readConverted { it?.toByteOrNull() }

@JvmName("readNumAsByte")
fun <S> ReadPath<S, Number>.readAsByte(): ReadPath<S, Byte> = readConverted { it.toByte() }

@JvmName("readNumAsNullableByte")
fun <S> ReadPath<S, Number?>.readAsByte(): ReadPath<S, Byte?> = readConverted { it?.toByte() }

@JvmName("readStrAsByte")
fun <S> ReadPath<S, String>.readAsByte(): ReadPath<S, Byte> = readConverted { it.toByte() }

fun <S> ReadPath<S, String>.readAsByteOrNull(): ReadPath<S, Byte?> = readConverted { it.toByteOrNull() }

@JvmName("readStrAsNullableByte")
fun <S> ReadPath<S, String?>.readAsByte(): ReadPath<S, Byte?> = readConverted { it?.toByte() }

@JvmName("readStrAsNullableByteOrNull")
fun <S> ReadPath<S, String?>.readAsByteOrNull(): ReadPath<S, Byte?> = readConverted { it?.toByteOrNull() }

//Property

@JvmName("readNumAsByte")
fun <S> KMutableProperty1<S, Number>.readAsByte(): ReadWritePath<S, Byte, Number> = toReadWritePath().readAsByte()

@JvmName("readNumAsNullableByte")
fun <S> KMutableProperty1<S, Number?>.readAsByte(): ReadWritePath<S, Byte?, Number?> = toReadWritePath().readAsByte()

@JvmName("readStrAsByte")
fun <S> KMutableProperty1<S, String>.readAsByte(): ReadWritePath<S, Byte, String> = toReadWritePath().readAsByte()

fun <S> KMutableProperty1<S, String>.readAsByteOrNull(): ReadWritePath<S, Byte?, String> = toReadWritePath().readAsByteOrNull()

@JvmName("readStrAsNullableByte")
fun <S> KMutableProperty1<S, String?>.readAsByte(): ReadWritePath<S, Byte?, String?> = toReadWritePath().readAsByte()

@JvmName("readStrAsNullableByteOrNull")
fun <S> KMutableProperty1<S, String?>.readAsByteOrNull(): ReadWritePath<S, Byte?, String?> = toReadWritePath().readAsByteOrNull()

//Function

@JvmName("readNumAsByte")
fun <S> ((S) -> Number).readAsByte(): ReadPath<S, Byte> = toReadPath().readAsByte()

@JvmName("readNumAsNullableByte")
fun <S> ((S) -> Number?).readAsByte(): ReadPath<S, Byte?> = toReadPath().readAsByte()

@JvmName("readStrAsByte")
fun <S> ((S) -> String).readAsByte(): ReadPath<S, Byte> = toReadPath().readAsByte()

fun <S> ((S) -> String).readAsByteOrNull(): ReadPath<S, Byte?> = toReadPath().readAsByteOrNull()

@JvmName("readStrAsNullableByte")
fun <S> ((S) -> String?).readAsByte(): ReadPath<S, Byte?> = toReadPath().readAsByte()

@JvmName("readStrAsNullableByteOrNull")
fun <S> ((S) -> String?).readAsByteOrNull(): ReadPath<S, Byte?> = toReadPath().readAsByteOrNull()