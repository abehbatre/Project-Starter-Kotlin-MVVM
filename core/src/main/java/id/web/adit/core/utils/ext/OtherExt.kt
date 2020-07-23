@file:Suppress("unused", "FunctionName")

package id.web.adit.core.utils.ext

import android.annotation.SuppressLint
import android.util.Log


fun <T : Any> T.TAG() = this::class.simpleName
fun Any.logD(msg:String) = Log.d(TAG(), msg)

fun String.randomIt(length: Int): String {
    return (1..length)
        .map { random() }
        .joinToString("")
}

@SuppressLint("DefaultLocale")
fun String.toTitleCase(): String {
    return split(" ").joinToString(" ") {
        it.capitalize()
    }
}

@SuppressLint("DefaultLocale")
fun String.toLowerCamelCase(): String {
    val z = split(" ").joinToString("") {
        it.capitalize()
    }
    return z.substring(0, 1).toLowerCase() + z.substring(1, z.length)
}

@SuppressLint("DefaultLocale")
fun String.toUpperCamelCase(): String {
    return split(" ").joinToString("") {
        it.capitalize()
    }
}

@SuppressLint("DefaultLocale")
fun String.toAlayCase(): String {
    val inFu7 = CharArray(length)
    for (i in 0 until length) {
        when {
            this[i] == 'A' -> inFu7[i] = 'д'
            this[i] == 'a' -> inFu7[i] = 'ä'
            this[i] == 'B' -> inFu7[i] = 'ß'
            this[i] == 'b' -> inFu7[i] = '8'
            this[i] == 'e' -> inFu7[i] = '3'
            this[i] == 'E' -> inFu7[i] = 'ξ'
            this[i] == 'g' -> inFu7[i] = 'ƍ'
            this[i] == 'G' -> inFu7[i] = '9'
            this[i] == 'i' -> inFu7[i] = '1'
            this[i] == 'I' -> inFu7[i] = 'ĩ'
            this[i] == 'j' -> inFu7[i] = 'ĵ'
            this[i] == 'J' -> inFu7[i] = '7'
            this[i] == 'n' -> inFu7[i] = 'Π'
            this[i] == 'k' -> inFu7[i] = 'κ'
            this[i] == 'o' -> inFu7[i] = 'Θ'
            this[i] == 'O' -> inFu7[i] = 'ο'
            this[i] == 'p' -> inFu7[i] = 'ρ'
            this[i] == 'P' -> inFu7[i] = 'ρ'
            this[i] == 's' -> inFu7[i] = '5'
            this[i] == 'S' -> inFu7[i] = '$'
            this[i] == 't' -> inFu7[i] = '7'
            this[i] == 'T' -> inFu7[i] = 'Ť'
            this[i] == 'w' -> inFu7[i] = 'ω'
            this[i] == 'x' -> inFu7[i] = 'х'
            this[i] == 'y' -> inFu7[i] = 'Ч'
            else -> inFu7[i] = this[i]
        }
    }
    return String(inFu7)
}

/** [Int] */
fun Int.twoDigitTime() = if (this < 10) "0" + toString() else toString()
fun Int.toMonthName(): String {
    val months = arrayOf("Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember")
    return months[this]
}

fun Int.second() = this.toLong() * 1000

/** [ArrayList] */
fun <T> List<T>.asArrayList() = this as ArrayList<T>
fun <T> arrayOf(list: List<T>) = list as ArrayList<T>
