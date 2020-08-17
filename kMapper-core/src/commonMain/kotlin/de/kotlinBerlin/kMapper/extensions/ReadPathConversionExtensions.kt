@file:Suppress("unused")
@file:JvmName("ReadPathConversionUtil")

package de.kotlinBerlin.kMapper.extensions

import de.kotlinBerlin.kMapper.ReadPath
import de.kotlinBerlin.kMapper.ReadWritePath
import de.kotlinBerlin.kMapper.internal.IgnoreMappingException
import kotlin.jvm.JvmName
import kotlin.reflect.KMutableProperty1

//Non Null

//Path

fun <S, V : Any, V1> ReadWritePath<S, V?, V1>.readNotNull(): ReadWritePath<S, V, V1> = readConverted { it!! }

inline fun <S, V : Any, V1> ReadWritePath<S, V?, V1>.readNotNull(crossinline exceptionBlock: () -> Nothing): ReadWritePath<S, V, V1> =
    readConverted { it ?: exceptionBlock() }

fun <S, V : Any> ReadPath<S, V?>.readNotNull(): ReadPath<S, V> = readConverted { it!! }

inline fun <S, V : Any> ReadPath<S, V?>.readNotNull(crossinline exceptionBlock: () -> Nothing): ReadPath<S, V> = readConverted { it ?: exceptionBlock() }

//Property

fun <S, V : Any> KMutableProperty1<S, V?>.readNotNull(): ReadWritePath<S, V, V> = path.readNotNull()

inline fun <S, V : Any> KMutableProperty1<S, V?>.readNotNull(crossinline exceptionBlock: () -> Nothing): ReadWritePath<S, V, V> =
    path.readNotNull(exceptionBlock)

//Function

fun <S, V : Any> ((S) -> V?).readNotNull(): ReadPath<S, V> = path.readNotNull()

inline fun <S, V : Any> ((S) -> V?).readNotNull(crossinline exceptionBlock: () -> Nothing): ReadPath<S, V> = path.readNotNull(exceptionBlock)

//Map when not null

//Path

fun <S, V : Any, V1> ReadWritePath<S, V?, V1>.readIfPresent(): ReadWritePath<S, V, V1> = readNotNull { throw IgnoreMappingException() }

fun <S, V : Any> ReadPath<S, V?>.readIfPresent(): ReadPath<S, V> = readNotNull { throw IgnoreMappingException() }

//Property

fun <S, V : Any> KMutableProperty1<S, V?>.readIfPresent(): ReadWritePath<S, V, V> = path.readIfPresent()

//Function

fun <S, V : Any> ((S) -> V?).readIfPresent(): ReadPath<S, V> = path.readIfPresent()


//Default value

//Path

fun <S, V : Any, V1> ReadWritePath<S, V?, V1>.readWithDefault(aDefault: V): ReadWritePath<S, V, V1> = readConverted { it ?: aDefault }

inline fun <S, V : Any, V1> ReadWritePath<S, V?, V1>.readWithDefault(crossinline aDefaultBlock: () -> V): ReadWritePath<S, V, V1> =
    readConverted { it ?: aDefaultBlock() }

fun <S, V : Any> ReadPath<S, V?>.readWithDefault(aDefault: V): ReadPath<S, V> = readConverted { it ?: aDefault }

inline fun <S, V : Any> ReadPath<S, V?>.readWithDefault(crossinline aDefaultBlock: () -> V): ReadPath<S, V> = readConverted { it ?: aDefaultBlock() }

//Property

fun <S, V : Any> KMutableProperty1<S, V?>.readWithDefault(aDefault: V): ReadWritePath<S, V, V> = path.readWithDefault(aDefault)

inline fun <S, V : Any> KMutableProperty1<S, V?>.readWithDefault(crossinline aDefaultBlock: () -> V): ReadWritePath<S, V, V> =
    path.readWithDefault(aDefaultBlock)

//Function

fun <S, V : Any> ((S) -> V?).readWithDefault(aDefault: V): ReadPath<S, V> = path.readWithDefault(aDefault)

inline fun <S, V : Any> ((S) -> V?).readWithDefault(crossinline aDefaultBlock: () -> V): ReadPath<S, V> = path.readWithDefault(aDefaultBlock)

//String

//Path

fun <S, V : Any, V1> ReadWritePath<S, V, V1>.readAsString(): ReadWritePath<S, String, V1> = readConverted { it.toString() }

@JvmName("readAsNullableString")
fun <S, V, V1> ReadWritePath<S, V, V1>.readAsString(): ReadWritePath<S, String?, V1> = readConverted { it?.toString() }

fun <S, V : Any> ReadPath<S, V>.readAsString(): ReadPath<S, String> = readConverted { it.toString() }

@JvmName("readAsNullableString")
fun <S, V> ReadPath<S, V>.readAsString(): ReadPath<S, String?> = readConverted { it?.toString() }

//Property

fun <S, V : Any> KMutableProperty1<S, V>.readAsString(): ReadWritePath<S, String, V> = path.readAsString()

@JvmName("readAsNullableString")
fun <S, V> KMutableProperty1<S, V?>.readAsString(): ReadWritePath<S, String?, V> = path.readAsString()

//Function

fun <S, V : Any> ((S) -> V).readAsString(): ReadPath<S, String> = path.readAsString()

@JvmName("readAsNullableString")
fun <S, V> ((S) -> V).readAsString(): ReadPath<S, String?> = path.readAsString()

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
fun <S> KMutableProperty1<S, Number>.readAsDouble(): ReadWritePath<S, Double, Number> = path.readAsDouble()

@JvmName("readNumAsNullableDouble")
fun <S> KMutableProperty1<S, Number?>.readAsDouble(): ReadWritePath<S, Double?, Number?> = path.readAsDouble()

@JvmName("readStrAsDouble")
fun <S> KMutableProperty1<S, String>.readAsDouble(): ReadWritePath<S, Double, String> = path.readAsDouble()

fun <S> KMutableProperty1<S, String>.readAsDoubleOrNull(): ReadWritePath<S, Double?, String> = path.readAsDoubleOrNull()

@JvmName("readStrAsNullableDouble")
fun <S> KMutableProperty1<S, String?>.readAsDouble(): ReadWritePath<S, Double?, String?> = path.readAsDouble()

@JvmName("readStrAsNullableDoubleOrNull")
fun <S> KMutableProperty1<S, String?>.readAsDoubleOrNull(): ReadWritePath<S, Double?, String?> = path.readAsDoubleOrNull()

//Function

@JvmName("readNumAsDouble")
fun <S> ((S) -> Number).readAsDouble(): ReadPath<S, Double> = path.readAsDouble()

@JvmName("readNumAsNullableDouble")
fun <S> ((S) -> Number?).readAsDouble(): ReadPath<S, Double?> = path.readAsDouble()

@JvmName("readStrAsDouble")
fun <S> ((S) -> String).readAsDouble(): ReadPath<S, Double> = path.readAsDouble()

fun <S> ((S) -> String).readAsDoubleOrNull(): ReadPath<S, Double?> = path.readAsDoubleOrNull()

@JvmName("readStrAsNullableDouble")
fun <S> ((S) -> String?).readAsDouble(): ReadPath<S, Double?> = path.readAsDouble()

@JvmName("readStrAsNullableDoubleOrNull")
fun <S> ((S) -> String?).readAsDoubleOrNull(): ReadPath<S, Double?> = path.readAsDoubleOrNull()

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
fun <S> KMutableProperty1<S, Number>.readAsFloat(): ReadWritePath<S, Float, Number> = path.readAsFloat()

@JvmName("readNumAsNullableFloat")
fun <S> KMutableProperty1<S, Number?>.readAsFloat(): ReadWritePath<S, Float?, Number?> = path.readAsFloat()

@JvmName("readStrAsFloat")
fun <S> KMutableProperty1<S, String>.readAsFloat(): ReadWritePath<S, Float, String> = path.readAsFloat()

fun <S> KMutableProperty1<S, String>.readAsFloatOrNull(): ReadWritePath<S, Float?, String> = path.readAsFloatOrNull()

@JvmName("readStrAsNullableFloat")
fun <S> KMutableProperty1<S, String?>.readAsFloat(): ReadWritePath<S, Float?, String?> = path.readAsFloat()

@JvmName("readStrAsNullableFloatOrNull")
fun <S> KMutableProperty1<S, String?>.readAsFloatOrNull(): ReadWritePath<S, Float?, String?> = path.readAsFloatOrNull()

//Function

@JvmName("readNumAsFloat")
fun <S> ((S) -> Number).readAsFloat(): ReadPath<S, Float> = path.readAsFloat()

@JvmName("readNumAsNullableFloat")
fun <S> ((S) -> Number?).readAsFloat(): ReadPath<S, Float?> = path.readAsFloat()

@JvmName("readStrAsFloat")
fun <S> ((S) -> String).readAsFloat(): ReadPath<S, Float> = path.readAsFloat()

fun <S> ((S) -> String).readAsFloatOrNull(): ReadPath<S, Float?> = path.readAsFloatOrNull()

@JvmName("readStrAsNullableFloat")
fun <S> ((S) -> String?).readAsFloat(): ReadPath<S, Float?> = path.readAsFloat()

@JvmName("readStrAsNullableFloatOrNull")
fun <S> ((S) -> String?).readAsFloatOrNull(): ReadPath<S, Float?> = path.readAsFloatOrNull()

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
fun <S> KMutableProperty1<S, Number>.readAsLong(): ReadWritePath<S, Long, Number> = path.readAsLong()

@JvmName("readNumAsNullableLong")
fun <S> KMutableProperty1<S, Number?>.readAsLong(): ReadWritePath<S, Long?, Number?> = path.readAsLong()

@JvmName("readStrAsLong")
fun <S> KMutableProperty1<S, String>.readAsLong(): ReadWritePath<S, Long, String> = path.readAsLong()

fun <S> KMutableProperty1<S, String>.readAsLongOrNull(): ReadWritePath<S, Long?, String> = path.readAsLongOrNull()

@JvmName("readStrAsNullableLong")
fun <S> KMutableProperty1<S, String?>.readAsLong(): ReadWritePath<S, Long?, String?> = path.readAsLong()

@JvmName("readStrAsNullableLongOrNull")
fun <S> KMutableProperty1<S, String?>.readAsLongOrNull(): ReadWritePath<S, Long?, String?> = path.readAsLongOrNull()

//Function

@JvmName("readNumAsLong")
fun <S> ((S) -> Number).readAsLong(): ReadPath<S, Long> = path.readAsLong()

@JvmName("readNumAsNullableLong")
fun <S> ((S) -> Number?).readAsLong(): ReadPath<S, Long?> = path.readAsLong()

@JvmName("readStrAsLong")
fun <S> ((S) -> String).readAsLong(): ReadPath<S, Long> = path.readAsLong()

fun <S> ((S) -> String).readAsLongOrNull(): ReadPath<S, Long?> = path.readAsLongOrNull()

@JvmName("readStrAsNullableLong")
fun <S> ((S) -> String?).readAsLong(): ReadPath<S, Long?> = path.readAsLong()

@JvmName("readStrAsNullableLongOrNull")
fun <S> ((S) -> String?).readAsLongOrNull(): ReadPath<S, Long?> = path.readAsLongOrNull()

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
fun <S> KMutableProperty1<S, Number>.readAsInt(): ReadWritePath<S, Int, Number> = path.readAsInt()

@JvmName("readNumAsNullableInt")
fun <S> KMutableProperty1<S, Number?>.readAsInt(): ReadWritePath<S, Int?, Number?> = path.readAsInt()

@JvmName("readStrAsInt")
fun <S> KMutableProperty1<S, String>.readAsInt(): ReadWritePath<S, Int, String> = path.readAsInt()

fun <S> KMutableProperty1<S, String>.readAsIntOrNull(): ReadWritePath<S, Int?, String> = path.readAsIntOrNull()

@JvmName("readStrAsNullableInt")
fun <S> KMutableProperty1<S, String?>.readAsInt(): ReadWritePath<S, Int?, String?> = path.readAsInt()

@JvmName("readStrAsNullableIntOrNull")
fun <S> KMutableProperty1<S, String?>.readAsIntOrNull(): ReadWritePath<S, Int?, String?> = path.readAsIntOrNull()

//Function

@JvmName("readNumAsInt")
fun <S> ((S) -> Number).readAsInt(): ReadPath<S, Int> = path.readAsInt()

@JvmName("readNumAsNullableInt")
fun <S> ((S) -> Number?).readAsInt(): ReadPath<S, Int?> = path.readAsInt()

@JvmName("readStrAsInt")
fun <S> ((S) -> String).readAsInt(): ReadPath<S, Int> = path.readAsInt()

fun <S> ((S) -> String).readAsIntOrNull(): ReadPath<S, Int?> = path.readAsIntOrNull()

@JvmName("readStrAsNullableInt")
fun <S> ((S) -> String?).readAsInt(): ReadPath<S, Int?> = path.readAsInt()

@JvmName("readStrAsNullableIntOrNull")
fun <S> ((S) -> String?).readAsIntOrNull(): ReadPath<S, Int?> = path.readAsIntOrNull()

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
fun <S> KMutableProperty1<S, Number>.readAsShort(): ReadWritePath<S, Short, Number> = path.readAsShort()

@JvmName("readNumAsNullableShort")
fun <S> KMutableProperty1<S, Number?>.readAsShort(): ReadWritePath<S, Short?, Number?> = path.readAsShort()

@JvmName("readStrAsShort")
fun <S> KMutableProperty1<S, String>.readAsShort(): ReadWritePath<S, Short, String> = path.readAsShort()

fun <S> KMutableProperty1<S, String>.readAsShortOrNull(): ReadWritePath<S, Short?, String> = path.readAsShortOrNull()

@JvmName("readStrAsNullableShort")
fun <S> KMutableProperty1<S, String?>.readAsShort(): ReadWritePath<S, Short?, String?> = path.readAsShort()

@JvmName("readStrAsNullableShortOrNull")
fun <S> KMutableProperty1<S, String?>.readAsShortOrNull(): ReadWritePath<S, Short?, String?> = path.readAsShortOrNull()

//Function

@JvmName("readNumAsShort")
fun <S> ((S) -> Number).readAsShort(): ReadPath<S, Short> = path.readAsShort()

@JvmName("readNumAsNullableShort")
fun <S> ((S) -> Number?).readAsShort(): ReadPath<S, Short?> = path.readAsShort()

@JvmName("readStrAsShort")
fun <S> ((S) -> String).readAsShort(): ReadPath<S, Short> = path.readAsShort()

fun <S> ((S) -> String).readAsShortOrNull(): ReadPath<S, Short?> = path.readAsShortOrNull()

@JvmName("readStrAsNullableShort")
fun <S> ((S) -> String?).readAsShort(): ReadPath<S, Short?> = path.readAsShort()

@JvmName("readStrAsNullableShortOrNull")
fun <S> ((S) -> String?).readAsShortOrNull(): ReadPath<S, Short?> = path.readAsShortOrNull()

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
fun <S> KMutableProperty1<S, Number>.readAsByte(): ReadWritePath<S, Byte, Number> = path.readAsByte()

@JvmName("readNumAsNullableByte")
fun <S> KMutableProperty1<S, Number?>.readAsByte(): ReadWritePath<S, Byte?, Number?> = path.readAsByte()

@JvmName("readStrAsByte")
fun <S> KMutableProperty1<S, String>.readAsByte(): ReadWritePath<S, Byte, String> = path.readAsByte()

fun <S> KMutableProperty1<S, String>.readAsByteOrNull(): ReadWritePath<S, Byte?, String> = path.readAsByteOrNull()

@JvmName("readStrAsNullableByte")
fun <S> KMutableProperty1<S, String?>.readAsByte(): ReadWritePath<S, Byte?, String?> = path.readAsByte()

@JvmName("readStrAsNullableByteOrNull")
fun <S> KMutableProperty1<S, String?>.readAsByteOrNull(): ReadWritePath<S, Byte?, String?> = path.readAsByteOrNull()

//Function

@JvmName("readNumAsByte")
fun <S> ((S) -> Number).readAsByte(): ReadPath<S, Byte> = path.readAsByte()

@JvmName("readNumAsNullableByte")
fun <S> ((S) -> Number?).readAsByte(): ReadPath<S, Byte?> = path.readAsByte()

@JvmName("readStrAsByte")
fun <S> ((S) -> String).readAsByte(): ReadPath<S, Byte> = path.readAsByte()

fun <S> ((S) -> String).readAsByteOrNull(): ReadPath<S, Byte?> = path.readAsByteOrNull()

@JvmName("readStrAsNullableByte")
fun <S> ((S) -> String?).readAsByte(): ReadPath<S, Byte?> = path.readAsByte()

@JvmName("readStrAsNullableByteOrNull")
fun <S> ((S) -> String?).readAsByteOrNull(): ReadPath<S, Byte?> = path.readAsByteOrNull()