package de.kotlinBerlin.kMapper.extensions

import de.kotlinBerlin.kMapper.BidirectionalMapping
import de.kotlinBerlin.kMapper.Mapping

//Iterable

fun <S, T> Mapping<S, T>.map(collection: Iterable<S>): List<T> = collection.map { map(it) }

fun <S, T, C : MutableCollection<T>> Mapping<S, T>.mapTo(destination: C, collection: Iterable<S>): C = collection.mapTo(destination) { map(it) }

fun <S, T> BidirectionalMapping<S, T>.mapReverse(collection: Iterable<T>): List<S> = collection.map { mapReverse(it) }

fun <S, T, C : MutableCollection<S>> BidirectionalMapping<S, T>.mapReverseTo(destination: C, collection: Iterable<T>) =
    collection.mapTo(destination) { mapReverse(it) }

//Array

fun <S, T> Mapping<S, T>.map(array: Array<S>): List<T> = array.map { map(it) }

fun <S, T, C : MutableCollection<T>> Mapping<S, T>.mapTo(destination: C, array: Array<S>): C = array.mapTo(destination) { map(it) }

fun <S, T> BidirectionalMapping<S, T>.mapReverse(array: Array<T>): List<S> = array.map { mapReverse(it) }

fun <S, T, C : MutableCollection<S>> BidirectionalMapping<S, T>.mapReverseTo(destination: C, array: Array<T>) =
    array.mapTo(destination) { mapReverse(it) }