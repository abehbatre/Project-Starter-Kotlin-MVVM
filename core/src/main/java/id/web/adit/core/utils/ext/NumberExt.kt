package id.web.adit.core.utils.ext

import android.animation.ValueAnimator
import android.widget.TextView
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

fun formatCurrency(s: Any): String {
    val formatter: NumberFormat = DecimalFormat("#,###")
    val output = formatter.format(s)
    return "Rp. " + output.replace(",", ".")
}

fun Any.asCurrency(): String {
    val formatter: NumberFormat = DecimalFormat("#,###")
    val output = formatter.format(this)
    return "Rp. " + output.replace(",", ".")
}

fun formatPercentage(q: Double): String {
    val NUMBER = q.toString()
    return if (NUMBER.contains(".0")) {
        NUMBER.replace(".?0*$".toRegex(), "")
    } else NUMBER
}


fun TextView.startNumberChangeAnimator(finalValue: Int?) {
    val initialValue = extractDigit(this.value)
    val valueAnimator = ValueAnimator.ofInt(initialValue, finalValue ?: 0)
    valueAnimator.duration = 1500L
    valueAnimator.addUpdateListener { value ->
        this.text = numberFormat(value.animatedValue.toString().toInt())
    }
    valueAnimator.start()
}

private fun numberFormat(number: Int?) = number?.let {
    NumberFormat.getNumberInstance(Locale.getDefault()).format(number)
} ?: "-"

private fun extractDigit(number: String) = Regex("[^0-9]").replace(number, "").toInt()
