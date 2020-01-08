package com.fta.ext

sealed class BooleanExt<out T>

object Otherwise : BooleanExt<Nothing>()
class WithData<T>(val data: T) : BooleanExt<T>()

//inline 内联函数
inline fun <T> Boolean.yes(block: () -> T) = when {
    this -> WithData(block())
    else -> Otherwise
}

inline fun <T> Boolean.no(block: () -> T) = when {
    this -> Otherwise
    else -> WithData(block())
}

inline fun <T> BooleanExt<T>.otherwise(block: () -> T): T =
    when (this) {
        is Otherwise -> block()
        is WithData -> this.data
    }

