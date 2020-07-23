@file:Suppress("unused", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package id.web.adit.core.utils.ext


import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.*


/*
* ! author              : Shintaro Katafuchi
* ! original sourcecode : https://github.com/hotchemi/khronos
* ! modify              : Aditya Pratama
* */


internal val calendar: Calendar by lazy { Calendar.getInstance() }


// common
val now = 0.day.ago
val today = 0.day.ago
val tomorrow = 1.day.since
val yesterday = 1.day.ago
val nextWeek = now + 1.week
val dayBeforeYesterday = now - 2.days



operator fun Date.plus(duration: Duration): Date {
    calendar.time = this
    calendar.add(duration.unit, duration.value)
    return calendar.time
}

operator fun Date.minus(duration: Duration): Date {
    calendar.time = this
    calendar.add(duration.unit, -duration.value)
    return calendar.time
}

operator fun Date.rangeTo(other: Date) =
    DateRange(this, other)

fun Date.with(year: Int = -1, month: Int = -1, day: Int = -1, hour: Int = -1, minute: Int = -1, second: Int = -1, millisecond: Int = -1): Date {
    calendar.time = this
    if (year > -1) calendar.set(Calendar.YEAR, year)
    if (month > 0) calendar.set(Calendar.MONTH, month - 1)
    if (day > 0) calendar.set(Calendar.DATE, day)
    if (hour > -1) calendar.set(Calendar.HOUR_OF_DAY, hour)
    if (minute > -1) calendar.set(Calendar.MINUTE, minute)
    if (second > -1) calendar.set(Calendar.SECOND, second)
    if (millisecond > -1) calendar.set(Calendar.MILLISECOND, millisecond)
    return calendar.time
}

fun Date.with(weekday: Int = -1): Date {
    calendar.time = this
    if (weekday > -1) calendar.set(Calendar.WEEK_OF_MONTH, weekday)
    return calendar.time
}

val Date.beginningOfYear: Date
    get() = with(month = 1, day = 1, hour = 0, minute = 0, second = 0, millisecond = 0)

val Date.endOfYear: Date
    get() = with(month = 12, day = 31, hour = 23, minute = 59, second = 59, millisecond = 999)

val Date.beginningOfMonth: Date
    get() = with(day = 1, hour = 0, minute = 0, second = 0, millisecond = 0)

val Date.endOfMonth: Date
    get() = endOfMonth()

fun Date.endOfMonth(): Date {
    calendar.time = this
    val lastDay = calendar.getActualMaximum(Calendar.DATE)
    return with(day = lastDay, hour = 23, minute = 59, second = 59, millisecond = 999)
}

val Date.beginningOfDay: Date
    get() = with(hour = 0, minute = 0, second = 0, millisecond = 0)

val Date.endOfDay: Date
    get() = with(hour = 23, minute = 59, second = 59, millisecond = 999)

val Date.beginningOfHour: Date
    get() = with(minute = 0, second = 0, millisecond = 0)

val Date.endOfHour: Date
    get() = with(minute = 59, second = 59, millisecond = 999)

val Date.beginningOfMinute: Date
    get() = with(second = 0, millisecond = 0)

val Date.endOfMinute: Date
    get() = with(second = 59, millisecond = 999)

@SuppressLint("SimpleDateFormat")
fun Date.toString(format: String): String = SimpleDateFormat(format).format(this)

fun Date.isSunday(): Boolean {
    calendar.time = this
    return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
}

fun Date.isMonday(): Boolean {
    calendar.time = this
    return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY
}

fun Date.isTuesday(): Boolean {
    calendar.time = this
    return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY
}

fun Date.isWednesday(): Boolean {
    calendar.time = this
    return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY
}

fun Date.isThursday(): Boolean {
    calendar.time = this
    return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY
}

fun Date.isFriday(): Boolean {
    calendar.time = this
    return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY
}

fun Date.isSaturday(): Boolean {
    calendar.time = this
    return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
}

/**Creates a Duration from this Date to the passed in one, precise to a second.*/
fun Date.to(other: Date): Duration {
    val difference = other.time - time
    return Duration(
        Calendar.SECOND,
        (difference / 1000).toInt()
    )
}

// -----------------------------------------------------------------
/** [String]
// ---------------------------------------------------------------*/
@SuppressLint("SimpleDateFormat")
fun String.toDate(format: String): Date = SimpleDateFormat(format).parse(this)
@SuppressLint("SimpleDateFormat")
private fun Long.toDateToString(format: String): String = SimpleDateFormat(format).format(Date(this))

// -----------------------------------------------------------------
/** [Int]
// ---------------------------------------------------------------*/
val Int.year: Duration
    get() = Duration(
        unit = Calendar.YEAR,
        value = this
    )

val Int.years: Duration
    get() = year

val Int.month: Duration
    get() = Duration(
        unit = Calendar.MONTH,
        value = this
    )

val Int.months: Duration
    get() = month

val Int.week: Duration
    get() = Duration(
        unit = Calendar.WEEK_OF_MONTH,
        value = this
    )

val Int.weeks: Duration
    get() = week

val Int.day: Duration
    get() = Duration(
        unit = Calendar.DATE,
        value = this
    )

val Int.days: Duration
    get() = day

val Int.hour: Duration
    get() = Duration(
        unit = Calendar.HOUR_OF_DAY,
        value = this
    )

val Int.hours: Duration
    get() = hour

val Int.minute: Duration
    get() = Duration(
        unit = Calendar.MINUTE,
        value = this
    )

val Int.minutes: Duration
    get() = minute

val Int.second: Duration
    get() = Duration(
        unit = Calendar.SECOND,
        value = this
    )

val Int.seconds: Duration
    get() = second

val Int.millisecond: Duration
    get() = Duration(
        unit = Calendar.MILLISECOND,
        value = this
    )

val Int.milliseconds: Duration
    get() = millisecond



// -----------------------------------------------------------------
/** [Duration]
// ---------------------------------------------------------------*/
class Duration(internal val unit: Int, internal val value: Int) {
    val ago = calculate(from = Date(), value = -value)

    val since = calculate(from = Date(), value = value)

    private fun calculate(from: Date, value: Int): Date {
        calendar.time = from
        calendar.add(unit, value)
        return calendar.time
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun hashCode() = Objects.hashCode(unit) * Objects.hashCode(value)

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Duration) {
            return false
        }
        return unit == other.unit && value == other.value
    }
}


/**
 * A range of values of type [Date].
 */
class DateRange(override val start: Date, override val endInclusive: Date): ClosedRange<Date> {
    override fun contains(value: Date) = start < value && value < endInclusive
}
