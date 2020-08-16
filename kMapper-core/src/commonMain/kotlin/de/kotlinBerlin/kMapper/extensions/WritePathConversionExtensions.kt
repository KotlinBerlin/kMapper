@file:Suppress("unused")
@file:JvmName("WritePathConversionUtil")

package de.kotlinBerlin.kMapper.extensions

import de.kotlinBerlin.kMapper.*
import de.kotlinBerlin.kMapper.internal.IgnoreMappingException
import de.kotlinBerlin.kMapper.toReadWritePath
import kotlin.jvm.JvmName
import kotlin.reflect.KMutableProperty1

//Non Null

//Path

fun <S, V, V1 : Any> ReadWritePath<S, V, V1>.writeNotNull(): ReadWritePath<S, V, V1?> = writeConverted { it!! }

fun <S, V, V1 : Any> ReadWritePath<S, V, V1>.writeNotNull(exceptionBlock: () -> Nothing): ReadWritePath<S, V, V1?> = writeConverted { it ?: exceptionBlock() }

fun <S, V : Any> WritePath<S, V>.writeNotNull(): WritePath<S, V?> = writeConverted { it!! }

fun <S, V : Any> WritePath<S, V>.writeNotNull(exceptionBlock: () -> Nothing): WritePath<S, V?> = writeConverted { it ?: exceptionBlock() }

//Property

fun <S, V : Any> KMutableProperty1<S, V>.writeNotNull(): ReadWritePath<S, V, V?> = toReadWritePath().writeNotNull()

fun <S, V : Any> KMutableProperty1<S, V>.writeNotNull(exceptionBlock: () -> Nothing): ReadWritePath<S, V, V?> = toReadWritePath().writeNotNull(exceptionBlock)

//Function

fun <S, V : Any> ((S, V) -> Unit).writeNotNull(): WritePath<S, V?> = toWritePath().writeNotNull()

fun <S, V : Any> ((S, V) -> Unit).readNotNull(exceptionBlock: () -> Nothing): WritePath<S, V?> = toWritePath().writeNotNull(exceptionBlock)

//Map when not null

//Path

fun <S, V, V1 : Any> ReadWritePath<S, V, V1>.writeIfPresent(): ReadWritePath<S, V, V1?> = writeNotNull { throw IgnoreMappingException() }

fun <S, V : Any> WritePath<S, V>.writeIfPresent(): WritePath<S, V?> = writeNotNull { throw IgnoreMappingException() }

//Property

fun <S, V : Any> KMutableProperty1<S, V>.writeIfPresent(): ReadWritePath<S, V, V?> = toReadWritePath().writeIfPresent()

//Function

fun <S, V : Any> ((S, V) -> Unit).writeIfPresent(): WritePath<S, V?> = toWritePath().writeIfPresent()

//Default value

//Path

fun <S, V, V1 : Any> ReadWritePath<S, V, V1>.writeWithDefault(aDefault: V1): ReadWritePath<S, V, V1?> = writeConverted { it ?: aDefault }

fun <S, V, V1 : Any> ReadWritePath<S, V, V1>.writeWithDefault(aDefaultBlock: () -> V1): ReadWritePath<S, V, V1?> = writeConverted { it ?: aDefaultBlock() }

fun <S, V : Any> WritePath<S, V>.writeWithDefault(aDefault: V): WritePath<S, V?> = writeConverted { it ?: aDefault }

fun <S, V : Any> WritePath<S, V>.writeWithDefault(aDefaultBlock: () -> V): WritePath<S, V?> = writeConverted { it ?: aDefaultBlock() }

//Property

fun <S, V : Any> KMutableProperty1<S, V>.writeWithDefault(aDefault: V): ReadWritePath<S, V, V?> = toReadWritePath().writeWithDefault(aDefault)

fun <S, V : Any> KMutableProperty1<S, V>.writeWithDefault(aDefaultBlock: () -> V): ReadWritePath<S, V, V?> = toReadWritePath().writeWithDefault(aDefaultBlock)

//Function

fun <S, V : Any> ((S, V) -> Unit).writeWithDefault(aDefault: V): WritePath<S, V?> = toWritePath().writeWithDefault(aDefault)

fun <S, V : Any> ((S, V) -> Unit).writeWithDefault(aDefaultBlock: () -> V): WritePath<S, V?> = toWritePath().writeWithDefault(aDefaultBlock)

//String

fun <S, V> ReadWritePath<S, V, String>.writeFromAny(): ReadWritePath<S, V, Any> = writeConverted { it.toString() }

@JvmName("writeFromNullableAny")
fun <S, V> ReadWritePath<S, V, String?>.writeFromAny(): ReadWritePath<S, V, Any?> = writeConverted { it?.toString() }

fun <S> WritePath<S, String>.writeFromAny(): WritePath<S, Any> = writeConverted { it.toString() }

@JvmName("writeFromNullableAny")
fun <S> WritePath<S, String?>.writeFromAny(): WritePath<S, Any?> = writeConverted { it?.toString() }

//Property

fun <S> KMutableProperty1<S, String>.writeFromAny(): ReadWritePath<S, String, Any> = toReadWritePath().writeFromAny()

@JvmName("writeFromNullableAny")
fun <S> KMutableProperty1<S, String?>.writeFromAny(): ReadWritePath<S, String?, Any?> = toReadWritePath().writeFromAny()

//Function

fun <S> ((S, String) -> Unit).writeFromAny(): WritePath<S, Any> = toWritePath().writeFromAny()

@JvmName("writeFromNullableAny")
fun <S> ((S, String?) -> Unit).writeFromAny(): WritePath<S, Any?> = toWritePath().writeFromAny()

//Double

//Path

@JvmName("writeDoubleFromNum")
fun <S, V> ReadWritePath<S, V, Double>.writeFromNum(): ReadWritePath<S, V, Number> = writeConverted { it.toDouble() }

@JvmName("writeDoubleFromNullableNum")
fun <S, V> ReadWritePath<S, V, Double?>.writeFromNum(): ReadWritePath<S, V, Number?> = writeConverted { it?.toDouble() }

@JvmName("writeDoubleFromStr")
fun <S, V> ReadWritePath<S, V, Double>.writeFromStr(): ReadWritePath<S, V, String> = writeConverted { it.toDouble() }

@JvmName("writeDoubleFromStrOrNull")
fun <S, V> ReadWritePath<S, V, Double?>.writeFromStrOrNull(): ReadWritePath<S, V, String> = writeConverted { it.toDoubleOrNull() }

@JvmName("writeDoubleFromNullableStr")
fun <S, V> ReadWritePath<S, V, Double?>.writeFromStr(): ReadWritePath<S, V, String?> = writeConverted { it?.toDouble() }

@JvmName("writeDoubleFromNullableStrOrNull")
fun <S, V> ReadWritePath<S, V, Double?>.writeFromNullableStrOrNull(): ReadWritePath<S, V, String?> = writeConverted { it?.toDoubleOrNull() }

@JvmName("writeDoubleFromNum")
fun <S> WritePath<S, Double>.writeFromNum(): WritePath<S, Number> = writeConverted { it.toDouble() }

@JvmName("writeDoubleFromNullableNum")
fun <S> WritePath<S, Double?>.writeFromNum(): WritePath<S, Number?> = writeConverted { it?.toDouble() }

@JvmName("writeDoubleFromStr")
fun <S> WritePath<S, Double>.writeFromStr(): WritePath<S, String> = writeConverted { it.toDouble() }

@JvmName("writeDoubleFromStrOrNull")
fun <S> WritePath<S, Double?>.writeFromStrOrNull(): WritePath<S, String> = writeConverted { it.toDoubleOrNull() }

@JvmName("writeDoubleFromNullableStr")
fun <S> WritePath<S, Double?>.writeFromStr(): WritePath<S, String?> = writeConverted { it?.toDouble() }

@JvmName("writeDoubleFromNullableStrOrNull")
fun <S> WritePath<S, Double?>.writeFromNullableStrOrNull(): WritePath<S, String?> = writeConverted { it?.toDoubleOrNull() }

//Property

@JvmName("writeDoubleFromNum")
fun <S> KMutableProperty1<S, Double>.writeFromNum(): ReadWritePath<S, Double, Number> = toReadWritePath().writeFromNum()

@JvmName("writeDoubleFromNullableNum")
fun <S> KMutableProperty1<S, Double?>.writeFromNum(): ReadWritePath<S, Double?, Number?> = toReadWritePath().writeFromNum()

@JvmName("writeDoubleFromStr")
fun <S> KMutableProperty1<S, Double>.writeFromStr(): ReadWritePath<S, Double, String> = toReadWritePath().writeFromStr()

@JvmName("writeDoubleFromStrOrNull")
fun <S> KMutableProperty1<S, Double?>.writeFromStrOrNull(): ReadWritePath<S, Double?, String> = toReadWritePath().writeFromStrOrNull()

@JvmName("writeDoubleFromNullableStr")
fun <S> KMutableProperty1<S, Double?>.writeFromStr(): ReadWritePath<S, Double?, String?> = toReadWritePath().writeFromStr()

@JvmName("writeDoubleFromNullableStrOrNull")
fun <S> KMutableProperty1<S, Double?>.writeFromNullableStrOrNull(): ReadWritePath<S, Double?, String?> = toReadWritePath().writeFromNullableStrOrNull()

//Function

@JvmName("writeDoubleFromNum")
fun <S> ((S, Double) -> Unit).writeFromNum(): WritePath<S, Number> = toWritePath().writeFromNum()

@JvmName("writeDoubleFromNullableNum")
fun <S> ((S, Double?) -> Unit).writeFromNum(): WritePath<S, Number?> = toWritePath().writeFromNum()

@JvmName("writeDoubleFromStr")
fun <S> ((S, Double) -> Unit).writeFromStr(): WritePath<S, String> = toWritePath().writeFromStr()

@JvmName("writeDoubleFromStrOrNull")
fun <S> ((S, Double?) -> Unit).writeFromStrOrNull(): WritePath<S, String> = toWritePath().writeFromStrOrNull()

@JvmName("writeDoubleFromNullableStr")
fun <S> ((S, Double?) -> Unit).writeFromStr(): WritePath<S, String?> = toWritePath().writeFromStr()

@JvmName("writeDoubleFromNullableStrOrNull")
fun <S> ((S, Double?) -> Unit).writeFromNullableStrOrNull(): WritePath<S, String?> = toWritePath().writeFromNullableStrOrNull()

//Float

//Path

@JvmName("writeFloatFromNum")
fun <S, V> ReadWritePath<S, V, Float>.writeFromNum(): ReadWritePath<S, V, Number> = writeConverted { it.toFloat() }

@JvmName("writeFloatFromNullableNum")
fun <S, V> ReadWritePath<S, V, Float?>.writeFromNum(): ReadWritePath<S, V, Number?> = writeConverted { it?.toFloat() }

@JvmName("writeFloatFromStr")
fun <S, V> ReadWritePath<S, V, Float>.writeFromStr(): ReadWritePath<S, V, String> = writeConverted { it.toFloat() }

@JvmName("writeFloatFromStrOrNull")
fun <S, V> ReadWritePath<S, V, Float?>.writeFromStrOrNull(): ReadWritePath<S, V, String> = writeConverted { it.toFloatOrNull() }

@JvmName("writeFloatFromNullableStr")
fun <S, V> ReadWritePath<S, V, Float?>.writeFromStr(): ReadWritePath<S, V, String?> = writeConverted { it?.toFloat() }

@JvmName("writeFloatFromNullableStrOrNull")
fun <S, V> ReadWritePath<S, V, Float?>.writeFromNullableStrOrNull(): ReadWritePath<S, V, String?> = writeConverted { it?.toFloatOrNull() }

@JvmName("writeFloatFromNum")
fun <S> WritePath<S, Float>.writeFromNum(): WritePath<S, Number> = writeConverted { it.toFloat() }

@JvmName("writeFloatFromNullableNum")
fun <S> WritePath<S, Float?>.writeFromNum(): WritePath<S, Number?> = writeConverted { it?.toFloat() }

@JvmName("writeFloatFromStr")
fun <S> WritePath<S, Float>.writeFromStr(): WritePath<S, String> = writeConverted { it.toFloat() }

@JvmName("writeFloatFromStrOrNull")
fun <S> WritePath<S, Float?>.writeFromStrOrNull(): WritePath<S, String> = writeConverted { it.toFloatOrNull() }

@JvmName("writeFloatFromNullableStr")
fun <S> WritePath<S, Float?>.writeFromStr(): WritePath<S, String?> = writeConverted { it?.toFloat() }

@JvmName("writeFloatFromNullableStrOrNull")
fun <S> WritePath<S, Float?>.writeFromNullableStrOrNull(): WritePath<S, String?> = writeConverted { it?.toFloatOrNull() }

//Property

@JvmName("writeFloatFromNum")
fun <S> KMutableProperty1<S, Float>.writeFromNum(): ReadWritePath<S, Float, Number> = toReadWritePath().writeFromNum()

@JvmName("writeFloatFromNullableNum")
fun <S> KMutableProperty1<S, Float?>.writeFromNum(): ReadWritePath<S, Float?, Number?> = toReadWritePath().writeFromNum()

@JvmName("writeFloatFromStr")
fun <S> KMutableProperty1<S, Float>.writeFromStr(): ReadWritePath<S, Float, String> = toReadWritePath().writeFromStr()

@JvmName("writeFloatFromStrOrNull")
fun <S> KMutableProperty1<S, Float?>.writeFromStrOrNull(): ReadWritePath<S, Float?, String> = toReadWritePath().writeFromStrOrNull()

@JvmName("writeFloatFromNullableStr")
fun <S> KMutableProperty1<S, Float?>.writeFromStr(): ReadWritePath<S, Float?, String?> = toReadWritePath().writeFromStr()

@JvmName("writeFloatFromNullableStrOrNull")
fun <S> KMutableProperty1<S, Float?>.writeFromNullableStrOrNull(): ReadWritePath<S, Float?, String?> = toReadWritePath().writeFromNullableStrOrNull()

//Function

@JvmName("writeFloatFromNum")
fun <S> ((S, Float) -> Unit).writeFromNum(): WritePath<S, Number> = toWritePath().writeFromNum()

@JvmName("writeFloatFromNullableNum")
fun <S> ((S, Float?) -> Unit).writeFromNum(): WritePath<S, Number?> = toWritePath().writeFromNum()

@JvmName("writeFloatFromStr")
fun <S> ((S, Float) -> Unit).writeFromStr(): WritePath<S, String> = toWritePath().writeFromStr()

@JvmName("writeFloatFromStrOrNull")
fun <S> ((S, Float?) -> Unit).writeFromStrOrNull(): WritePath<S, String> = toWritePath().writeFromStrOrNull()

@JvmName("writeFloatFromNullableStr")
fun <S> ((S, Float?) -> Unit).writeFromStr(): WritePath<S, String?> = toWritePath().writeFromStr()

@JvmName("writeFloatFromNullableStrOrNull")
fun <S> ((S, Float?) -> Unit).writeFromNullableStrOrNull(): WritePath<S, String?> = toWritePath().writeFromNullableStrOrNull()

//Long

//Path

@JvmName("writeLongFromNum")
fun <S, V> ReadWritePath<S, V, Long>.writeFromNum(): ReadWritePath<S, V, Number> = writeConverted { it.toLong() }

@JvmName("writeLongFromNullableNum")
fun <S, V> ReadWritePath<S, V, Long?>.writeFromNum(): ReadWritePath<S, V, Number?> = writeConverted { it?.toLong() }

@JvmName("writeLongFromStr")
fun <S, V> ReadWritePath<S, V, Long>.writeFromStr(): ReadWritePath<S, V, String> = writeConverted { it.toLong() }

@JvmName("writeLongFromStrOrNull")
fun <S, V> ReadWritePath<S, V, Long?>.writeFromStrOrNull(): ReadWritePath<S, V, String> = writeConverted { it.toLongOrNull() }

@JvmName("writeLongFromNullableStr")
fun <S, V> ReadWritePath<S, V, Long?>.writeFromStr(): ReadWritePath<S, V, String?> = writeConverted { it?.toLong() }

@JvmName("writeLongFromNullableStrOrNull")
fun <S, V> ReadWritePath<S, V, Long?>.writeFromNullableStrOrNull(): ReadWritePath<S, V, String?> = writeConverted { it?.toLongOrNull() }

@JvmName("writeLongFromNum")
fun <S> WritePath<S, Long>.writeFromNum(): WritePath<S, Number> = writeConverted { it.toLong() }

@JvmName("writeLongFromNullableNum")
fun <S> WritePath<S, Long?>.writeFromNum(): WritePath<S, Number?> = writeConverted { it?.toLong() }

@JvmName("writeLongFromStr")
fun <S> WritePath<S, Long>.writeFromStr(): WritePath<S, String> = writeConverted { it.toLong() }

@JvmName("writeLongFromStrOrNull")
fun <S> WritePath<S, Long?>.writeFromStrOrNull(): WritePath<S, String> = writeConverted { it.toLongOrNull() }

@JvmName("writeLongFromNullableStr")
fun <S> WritePath<S, Long?>.writeFromStr(): WritePath<S, String?> = writeConverted { it?.toLong() }

@JvmName("writeLongFromNullableStrOrNull")
fun <S> WritePath<S, Long?>.writeFromNullableStrOrNull(): WritePath<S, String?> = writeConverted { it?.toLongOrNull() }

//Property

@JvmName("writeLongFromNum")
fun <S> KMutableProperty1<S, Long>.writeFromNum(): ReadWritePath<S, Long, Number> = toReadWritePath().writeFromNum()

@JvmName("writeLongFromNullableNum")
fun <S> KMutableProperty1<S, Long?>.writeFromNum(): ReadWritePath<S, Long?, Number?> = toReadWritePath().writeFromNum()

@JvmName("writeLongFromStr")
fun <S> KMutableProperty1<S, Long>.writeFromStr(): ReadWritePath<S, Long, String> = toReadWritePath().writeFromStr()

@JvmName("writeLongFromStrOrNull")
fun <S> KMutableProperty1<S, Long?>.writeFromStrOrNull(): ReadWritePath<S, Long?, String> = toReadWritePath().writeFromStrOrNull()

@JvmName("writeLongFromNullableStr")
fun <S> KMutableProperty1<S, Long?>.writeFromStr(): ReadWritePath<S, Long?, String?> = toReadWritePath().writeFromStr()

@JvmName("writeLongFromNullableStrOrNull")
fun <S> KMutableProperty1<S, Long?>.writeFromNullableStrOrNull(): ReadWritePath<S, Long?, String?> = toReadWritePath().writeFromNullableStrOrNull()

//Function

@JvmName("writeLongFromNum")
fun <S> ((S, Long) -> Unit).writeFromNum(): WritePath<S, Number> = toWritePath().writeFromNum()

@JvmName("writeLongFromNullableNum")
fun <S> ((S, Long?) -> Unit).writeFromNum(): WritePath<S, Number?> = toWritePath().writeFromNum()

@JvmName("writeLongFromStr")
fun <S> ((S, Long) -> Unit).writeFromStr(): WritePath<S, String> = toWritePath().writeFromStr()

@JvmName("writeLongFromStrOrNull")
fun <S> ((S, Long?) -> Unit).writeFromStrOrNull(): WritePath<S, String> = toWritePath().writeFromStrOrNull()

@JvmName("writeLongFromNullableStr")
fun <S> ((S, Long?) -> Unit).writeFromStr(): WritePath<S, String?> = toWritePath().writeFromStr()

@JvmName("writeLongFromNullableStrOrNull")
fun <S> ((S, Long?) -> Unit).writeFromNullableStrOrNull(): WritePath<S, String?> = toWritePath().writeFromNullableStrOrNull()

//Int

//Path

@JvmName("writeIntFromNum")
fun <S, V> ReadWritePath<S, V, Int>.writeFromNum(): ReadWritePath<S, V, Number> = writeConverted { it.toInt() }

@JvmName("writeIntFromNullableNum")
fun <S, V> ReadWritePath<S, V, Int?>.writeFromNum(): ReadWritePath<S, V, Number?> = writeConverted { it?.toInt() }

@JvmName("writeIntFromStr")
fun <S, V> ReadWritePath<S, V, Int>.writeFromStr(): ReadWritePath<S, V, String> = writeConverted { it.toInt() }

@JvmName("writeIntFromStrOrNull")
fun <S, V> ReadWritePath<S, V, Int?>.writeFromStrOrNull(): ReadWritePath<S, V, String> = writeConverted { it.toIntOrNull() }

@JvmName("writeIntFromNullableStr")
fun <S, V> ReadWritePath<S, V, Int?>.writeFromStr(): ReadWritePath<S, V, String?> = writeConverted { it?.toInt() }

@JvmName("writeIntFromNullableStrOrNull")
fun <S, V> ReadWritePath<S, V, Int?>.writeFromNullableStrOrNull(): ReadWritePath<S, V, String?> = writeConverted { it?.toIntOrNull() }

@JvmName("writeIntFromNum")
fun <S> WritePath<S, Int>.writeFromNum(): WritePath<S, Number> = writeConverted { it.toInt() }

@JvmName("writeIntFromNullableNum")
fun <S> WritePath<S, Int?>.writeFromNum(): WritePath<S, Number?> = writeConverted { it?.toInt() }

@JvmName("writeIntFromStr")
fun <S> WritePath<S, Int>.writeFromStr(): WritePath<S, String> = writeConverted { it.toInt() }

@JvmName("writeIntFromStrOrNull")
fun <S> WritePath<S, Int?>.writeFromStrOrNull(): WritePath<S, String> = writeConverted { it.toIntOrNull() }

@JvmName("writeIntFromNullableStr")
fun <S> WritePath<S, Int?>.writeFromStr(): WritePath<S, String?> = writeConverted { it?.toInt() }

@JvmName("writeIntFromNullableStrOrNull")
fun <S> WritePath<S, Int?>.writeFromNullableStrOrNull(): WritePath<S, String?> = writeConverted { it?.toIntOrNull() }

//Property

@JvmName("writeIntFromNum")
fun <S> KMutableProperty1<S, Int>.writeFromNum(): ReadWritePath<S, Int, Number> = toReadWritePath().writeFromNum()

@JvmName("writeIntFromNullableNum")
fun <S> KMutableProperty1<S, Int?>.writeFromNum(): ReadWritePath<S, Int?, Number?> = toReadWritePath().writeFromNum()

@JvmName("writeIntFromStr")
fun <S> KMutableProperty1<S, Int>.writeFromStr(): ReadWritePath<S, Int, String> = toReadWritePath().writeFromStr()

@JvmName("writeIntFromStrOrNull")
fun <S> KMutableProperty1<S, Int?>.writeFromStrOrNull(): ReadWritePath<S, Int?, String> = toReadWritePath().writeFromStrOrNull()

@JvmName("writeIntFromNullableStr")
fun <S> KMutableProperty1<S, Int?>.writeFromStr(): ReadWritePath<S, Int?, String?> = toReadWritePath().writeFromStr()

@JvmName("writeIntFromNullableStrOrNull")
fun <S> KMutableProperty1<S, Int?>.writeFromNullableStrOrNull(): ReadWritePath<S, Int?, String?> = toReadWritePath().writeFromNullableStrOrNull()

//Function

@JvmName("writeIntFromNum")
fun <S> ((S, Int) -> Unit).writeFromNum(): WritePath<S, Number> = toWritePath().writeFromNum()

@JvmName("writeIntFromNullableNum")
fun <S> ((S, Int?) -> Unit).writeFromNum(): WritePath<S, Number?> = toWritePath().writeFromNum()

@JvmName("writeIntFromStr")
fun <S> ((S, Int) -> Unit).writeFromStr(): WritePath<S, String> = toWritePath().writeFromStr()

@JvmName("writeIntFromStrOrNull")
fun <S> ((S, Int?) -> Unit).writeFromStrOrNull(): WritePath<S, String> = toWritePath().writeFromStrOrNull()

@JvmName("writeIntFromNullableStr")
fun <S> ((S, Int?) -> Unit).writeFromStr(): WritePath<S, String?> = toWritePath().writeFromStr()

@JvmName("writeIntFromNullableStrOrNull")
fun <S> ((S, Int?) -> Unit).writeFromNullableStrOrNull(): WritePath<S, String?> = toWritePath().writeFromNullableStrOrNull()

//Short

//Path

@JvmName("writeShortFromNum")
fun <S, V> ReadWritePath<S, V, Short>.writeFromNum(): ReadWritePath<S, V, Number> = writeConverted { it.toShort() }

@JvmName("writeShortFromNullableNum")
fun <S, V> ReadWritePath<S, V, Short?>.writeFromNum(): ReadWritePath<S, V, Number?> = writeConverted { it?.toShort() }

@JvmName("writeShortFromStr")
fun <S, V> ReadWritePath<S, V, Short>.writeFromStr(): ReadWritePath<S, V, String> = writeConverted { it.toShort() }

@JvmName("writeShortFromStrOrNull")
fun <S, V> ReadWritePath<S, V, Short?>.writeFromStrOrNull(): ReadWritePath<S, V, String> = writeConverted { it.toShortOrNull() }

@JvmName("writeShortFromNullableStr")
fun <S, V> ReadWritePath<S, V, Short?>.writeFromStr(): ReadWritePath<S, V, String?> = writeConverted { it?.toShort() }

@JvmName("writeShortFromNullableStrOrNull")
fun <S, V> ReadWritePath<S, V, Short?>.writeFromNullableStrOrNull(): ReadWritePath<S, V, String?> = writeConverted { it?.toShortOrNull() }

@JvmName("writeShortFromNum")
fun <S> WritePath<S, Short>.writeFromNum(): WritePath<S, Number> = writeConverted { it.toShort() }

@JvmName("writeShortFromNullableNum")
fun <S> WritePath<S, Short?>.writeFromNum(): WritePath<S, Number?> = writeConverted { it?.toShort() }

@JvmName("writeShortFromStr")
fun <S> WritePath<S, Short>.writeFromStr(): WritePath<S, String> = writeConverted { it.toShort() }

@JvmName("writeShortFromStrOrNull")
fun <S> WritePath<S, Short?>.writeFromStrOrNull(): WritePath<S, String> = writeConverted { it.toShortOrNull() }

@JvmName("writeShortFromNullableStr")
fun <S> WritePath<S, Short?>.writeFromStr(): WritePath<S, String?> = writeConverted { it?.toShort() }

@JvmName("writeShortFromNullableStrOrNull")
fun <S> WritePath<S, Short?>.writeFromNullableStrOrNull(): WritePath<S, String?> = writeConverted { it?.toShortOrNull() }

//Property

@JvmName("writeShortFromNum")
fun <S> KMutableProperty1<S, Short>.writeFromNum(): ReadWritePath<S, Short, Number> = toReadWritePath().writeFromNum()

@JvmName("writeShortFromNullableNum")
fun <S> KMutableProperty1<S, Short?>.writeFromNum(): ReadWritePath<S, Short?, Number?> = toReadWritePath().writeFromNum()

@JvmName("writeShortFromStr")
fun <S> KMutableProperty1<S, Short>.writeFromStr(): ReadWritePath<S, Short, String> = toReadWritePath().writeFromStr()

@JvmName("writeShortFromStrOrNull")
fun <S> KMutableProperty1<S, Short?>.writeFromStrOrNull(): ReadWritePath<S, Short?, String> = toReadWritePath().writeFromStrOrNull()

@JvmName("writeShortFromNullableStr")
fun <S> KMutableProperty1<S, Short?>.writeFromStr(): ReadWritePath<S, Short?, String?> = toReadWritePath().writeFromStr()

@JvmName("writeShortFromNullableStrOrNull")
fun <S> KMutableProperty1<S, Short?>.writeFromNullableStrOrNull(): ReadWritePath<S, Short?, String?> = toReadWritePath().writeFromNullableStrOrNull()

//Function

@JvmName("writeShortFromNum")
fun <S> ((S, Short) -> Unit).writeFromNum(): WritePath<S, Number> = toWritePath().writeFromNum()

@JvmName("writeShortFromNullableNum")
fun <S> ((S, Short?) -> Unit).writeFromNum(): WritePath<S, Number?> = toWritePath().writeFromNum()

@JvmName("writeShortFromStr")
fun <S> ((S, Short) -> Unit).writeFromStr(): WritePath<S, String> = toWritePath().writeFromStr()

@JvmName("writeShortFromStrOrNull")
fun <S> ((S, Short?) -> Unit).writeFromStrOrNull(): WritePath<S, String> = toWritePath().writeFromStrOrNull()

@JvmName("writeShortFromNullableStr")
fun <S> ((S, Short?) -> Unit).writeFromStr(): WritePath<S, String?> = toWritePath().writeFromStr()

@JvmName("writeShortFromNullableStrOrNull")
fun <S> ((S, Short?) -> Unit).writeFromNullableStrOrNull(): WritePath<S, String?> = toWritePath().writeFromNullableStrOrNull()

//Byte

//Path

@JvmName("writeByteFromNum")
fun <S, V> ReadWritePath<S, V, Byte>.writeFromNum(): ReadWritePath<S, V, Number> = writeConverted { it.toByte() }

@JvmName("writeByteFromNullableNum")
fun <S, V> ReadWritePath<S, V, Byte?>.writeFromNum(): ReadWritePath<S, V, Number?> = writeConverted { it?.toByte() }

@JvmName("writeByteFromStr")
fun <S, V> ReadWritePath<S, V, Byte>.writeFromStr(): ReadWritePath<S, V, String> = writeConverted { it.toByte() }

@JvmName("writeByteFromStrOrNull")
fun <S, V> ReadWritePath<S, V, Byte?>.writeFromStrOrNull(): ReadWritePath<S, V, String> = writeConverted { it.toByteOrNull() }

@JvmName("writeByteFromNullableStr")
fun <S, V> ReadWritePath<S, V, Byte?>.writeFromStr(): ReadWritePath<S, V, String?> = writeConverted { it?.toByte() }

@JvmName("writeByteFromNullableStrOrNull")
fun <S, V> ReadWritePath<S, V, Byte?>.writeFromNullableStrOrNull(): ReadWritePath<S, V, String?> = writeConverted { it?.toByteOrNull() }

@JvmName("writeByteFromNum")
fun <S> WritePath<S, Byte>.writeFromNum(): WritePath<S, Number> = writeConverted { it.toByte() }

@JvmName("writeByteFromNullableNum")
fun <S> WritePath<S, Byte?>.writeFromNum(): WritePath<S, Number?> = writeConverted { it?.toByte() }

@JvmName("writeByteFromStr")
fun <S> WritePath<S, Byte>.writeFromStr(): WritePath<S, String> = writeConverted { it.toByte() }

@JvmName("writeByteFromStrOrNull")
fun <S> WritePath<S, Byte?>.writeFromStrOrNull(): WritePath<S, String> = writeConverted { it.toByteOrNull() }

@JvmName("writeByteFromNullableStr")
fun <S> WritePath<S, Byte?>.writeFromStr(): WritePath<S, String?> = writeConverted { it?.toByte() }

@JvmName("writeByteFromNullableStrOrNull")
fun <S> WritePath<S, Byte?>.writeFromNullableStrOrNull(): WritePath<S, String?> = writeConverted { it?.toByteOrNull() }

//Property

@JvmName("writeByteFromNum")
fun <S> KMutableProperty1<S, Byte>.writeFromNum(): ReadWritePath<S, Byte, Number> = toReadWritePath().writeFromNum()

@JvmName("writeByteFromNullableNum")
fun <S> KMutableProperty1<S, Byte?>.writeFromNum(): ReadWritePath<S, Byte?, Number?> = toReadWritePath().writeFromNum()

@JvmName("writeByteFromStr")
fun <S> KMutableProperty1<S, Byte>.writeFromStr(): ReadWritePath<S, Byte, String> = toReadWritePath().writeFromStr()

@JvmName("writeByteFromStrOrNull")
fun <S> KMutableProperty1<S, Byte?>.writeFromStrOrNull(): ReadWritePath<S, Byte?, String> = toReadWritePath().writeFromStrOrNull()

@JvmName("writeByteFromNullableStr")
fun <S> KMutableProperty1<S, Byte?>.writeFromStr(): ReadWritePath<S, Byte?, String?> = toReadWritePath().writeFromStr()

@JvmName("writeByteFromNullableStrOrNull")
fun <S> KMutableProperty1<S, Byte?>.writeFromNullableStrOrNull(): ReadWritePath<S, Byte?, String?> = toReadWritePath().writeFromNullableStrOrNull()

//Function

@JvmName("writeByteFromNum")
fun <S> ((S, Byte) -> Unit).writeFromNum(): WritePath<S, Number> = toWritePath().writeFromNum()

@JvmName("writeByteFromNullableNum")
fun <S> ((S, Byte?) -> Unit).writeFromNum(): WritePath<S, Number?> = toWritePath().writeFromNum()

@JvmName("writeByteFromStr")
fun <S> ((S, Byte) -> Unit).writeFromStr(): WritePath<S, String> = toWritePath().writeFromStr()

@JvmName("writeByteFromStrOrNull")
fun <S> ((S, Byte?) -> Unit).writeFromStrOrNull(): WritePath<S, String> = toWritePath().writeFromStrOrNull()

@JvmName("writeByteFromNullableStr")
fun <S> ((S, Byte?) -> Unit).writeFromStr(): WritePath<S, String?> = toWritePath().writeFromStr()

@JvmName("writeByteFromNullableStrOrNull")
fun <S> ((S, Byte?) -> Unit).writeFromNullableStrOrNull(): WritePath<S, String?> = toWritePath().writeFromNullableStrOrNull()