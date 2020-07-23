@file:Suppress("KDocUnresolvedReference", "unused", "UNCHECKED_CAST")

package id.web.adit.core.utils.ext

import android.R
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.RippleDrawable
import android.os.Build
import android.os.Handler
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.*
import androidx.customview.widget.ViewDragHelper
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Fade
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.androidadvance.topsnackbar.TSnackbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import id.web.adit.core.utils.MaterialColor
import smartdevelop.ir.eram.showcaseviewlib.GuideView
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType


/** [copyright]
 *
 * @author  Aditya Pratama
 * @since   2019
 *
 * @receiver (.*?)View
 */


fun View.runWithDelay(delayMilis: Long = 100, block: () -> Unit) {
    Handler().postDelayed({ block.invoke() }, delayMilis)
}

/** Set an onclick listener */
fun <T : View> T.click(block: (T) -> Unit) = setOnClickListener { block(it as T) }
fun <T : View> T.longClick(block: (T) -> Boolean) = setOnLongClickListener { block(it as T) }



fun View.gone() { visibility = GONE }
fun View.visible() { visibility = VISIBLE }
fun View.invisible() { visibility = INVISIBLE }
fun View.toggle()  {
    visibility = when (visibility) {
        VISIBLE -> GONE
        else -> GONE
    }
}

fun View.toggleWithAnimation()  {
    val transition: Transition = Fade()
    transition.duration = 600
    transition.addTarget(this)
    TransitionManager.beginDelayedTransition(parent as ViewGroup, transition)
    visibility = when (visibility) {
        VISIBLE -> GONE
        else -> GONE
    }
}




/** [TSnackbar]
 * 
 * @param msg           isi pesan
 * @param bgColor       warna background
 * @param textColor     warna text
 * @param duration      durasi snackbar
 */
fun View.snackbar(
    msg: String,
    bgColor: Int = MaterialColor.BLACK,
    textColor : Int = MaterialColor.WHITE,
    duration: Int = TSnackbar.LENGTH_SHORT
) {
    val snackbar = TSnackbar.make(this, msg, duration)
    val snackbarView = snackbar.view
    val textView = snackbarView.findViewById<View>(com.androidadvance.topsnackbar.R.id.snackbar_text) as TextView
    snackbar.setActionTextColor(bgColor)
    snackbarView.setBackgroundColor(bgColor)
    textView.setTextColor(textColor)

    snackbar.show()
}
fun View.snackbar(text: String, duration: Int = Snackbar.LENGTH_LONG, builder: Snackbar.() -> Unit = {}): Snackbar {
    val snackbar = Snackbar.make(this, text, duration)
    snackbar.builder()
    snackbar.show()
    return snackbar
}
fun View.snackbar(@StringRes textId: Int, duration: Int = Snackbar.LENGTH_LONG, builder: Snackbar.() -> Unit = {}) = snackbar(context.string(textId), duration, builder)


// -----------------------------------------------------------------
/** [FloatingActionButton]
// ---------------------------------------------------------------*/
inline val FloatingActionButton.isHidden get() = !isShown
fun FloatingActionButton.showIf(show: Boolean) = if (show) show() else hide()
fun FloatingActionButton.hideIf(hide: Boolean) = if (hide) hide() else show()


// -----------------------------------------------------------------
/** [Padding]
// ---------------------------------------------------------------*/
fun View.setPaddingLeft(value: Int) = setPadding(value, paddingTop, paddingRight, paddingBottom)
fun View.setPaddingRight(value: Int) = setPadding(paddingLeft, paddingTop, value, paddingBottom)
fun View.setPaddingTop(value: Int) = setPaddingRelative(paddingStart, value, paddingEnd, paddingBottom)
fun View.setPaddingBottom(value: Int) = setPaddingRelative(paddingStart, paddingTop, paddingEnd, value)
fun View.setPaddingStart(value: Int) = setPaddingRelative(value, paddingTop, paddingEnd, paddingBottom)
fun View.setPaddingEnd(value: Int) = setPaddingRelative(paddingStart, paddingTop, value, paddingBottom)
fun View.setPaddingHorizontal(value: Int) = setPaddingRelative(value, paddingTop, value, paddingBottom)
fun View.setPaddingVertical(value: Int) = setPaddingRelative(paddingStart, value, paddingEnd, value)

// -----------------------------------------------------------------
/** [Margin]
// ---------------------------------------------------------------*/
fun View.setMarginLeft(margin: Int) = setMargins(margin, ViewDragHelper.EDGE_LEFT)
fun View.setMarginTop(margin: Int) = setMargins(margin, ViewDragHelper.EDGE_TOP)
fun View.setMarginRight(margin: Int) = setMargins(margin, ViewDragHelper.EDGE_RIGHT)
fun View.setMarginBottom(margin: Int) = setMargins(margin, ViewDragHelper.EDGE_BOTTOM)
fun View.setMarginHorizontal(margin: Int) = setMargins(margin, ViewDragHelper.DIRECTION_HORIZONTAL)
fun View.setMarginVertical(margin: Int) = setMargins(margin, ViewDragHelper.DIRECTION_VERTICAL)
fun View.setMargin(margin: Int) = setMargins(margin, ViewDragHelper.DIRECTION_ALL)
private fun View.setMargins(margin: Int, flag: Int): Boolean {
    val p = (layoutParams as? ViewGroup.MarginLayoutParams) ?: return false
    p.setMargins(
        if (flag and ViewDragHelper.EDGE_LEFT > 0) margin else p.leftMargin,
        if (flag and ViewDragHelper.EDGE_TOP > 0) margin else p.topMargin,
        if (flag and ViewDragHelper.EDGE_RIGHT > 0) margin else p.rightMargin,
        if (flag and ViewDragHelper.EDGE_BOTTOM > 0) margin else p.bottomMargin
    )
    return true
}


// -----------------------------------------------------------------
/** [Keyboard]
// ---------------------------------------------------------------*/
fun View.hideKeyboard() {
    clearFocus()
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow(windowToken, 0)
}

fun View.showKeyboard() {
    requestFocus()
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        .showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}


// -----------------------------------------------------------------
/** [RippleDrawable]
// ---------------------------------------------------------------*/
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun View.setRippleEffect(@ColorInt foregroundColor: Int, @ColorInt backgroundColor: Int) {
    background = createSimpleRippleDrawable(
        foregroundColor,
        backgroundColor
    )
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
private fun createSimpleRippleDrawable(@ColorInt foregroundColor: Int, @ColorInt backgroundColor: Int): RippleDrawable {
    val states = ColorStateList(arrayOf(intArrayOf()), intArrayOf(foregroundColor))
    val content = ColorDrawable(backgroundColor)
    val mask = ColorDrawable(foregroundColor.adjustAlpha(0.16f))
    return RippleDrawable(states, content, mask)
}



// -----------------------------------------------------------------
/** [TextView]
// ---------------------------------------------------------------*/
@Suppress("DEPRECATION")
fun TextView.htmlFormat(text: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        this.text = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
    } else {
        this.text = Html.fromHtml(text)
    }
}

var TextView.value: String
    get() = text.toString()
    set(value) { text = value }


fun TextView.underLine() {
    paint.flags = paint.flags or Paint.UNDERLINE_TEXT_FLAG
    paint.isAntiAlias = true
}

fun TextView.bold() {
    paint.isFakeBoldText = true
    paint.isAntiAlias = true
}


fun TextView.setDrawableStart(drawable: Int) {
    this.setCompoundDrawablesWithIntrinsicBounds(drawable, 0, 0, 0)
}
fun TextView.setDrawableEnd(drawable: Int) {
    this.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable, 0)
}



// -----------------------------------------------------------------
/** [Button]
// ---------------------------------------------------------------*/
var Button.value: String
    get() = text.toString()
    set(value) { text = value }
    

// -----------------------------------------------------------------
/** [EditText]
// ---------------------------------------------------------------*/
var EditText.value: String
    get() = text.toString()
    set(value) = setText(value)


fun EditText.textChange(
    delayMilis: Long = 100,
    afterTextChanged: (String) -> Unit
) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(editable: Editable?) {
            runWithDelay(delayMilis) {
                afterTextChanged.invoke(editable.toString())
            }
        }
    })
}


fun  <T : EditText> T.enterClick(
    block: (T) -> Unit
) = setOnEditorActionListener { _, actionId, _ ->
    when (actionId) {
        EditorInfo.IME_ACTION_DONE -> {
            block(this)
            true
        }
        EditorInfo.IME_ACTION_GO -> {
            block(this)
            true
        }
        EditorInfo.IME_ACTION_SEARCH -> {
            block(this)
            true
        }
        else -> {
            false
        }
    }
}

// -----------------------------------------------------------------
/** [RecyclerView]
// ---------------------------------------------------------------*/
fun RecyclerView.withDividerDecoration() {
    layoutManager = LinearLayoutManager(context)
    setHasFixedSize(true)
    DividerItemDecoration(context, DividerItemDecoration.VERTICAL).also {
        addItemDecoration(it)
    }
}

fun RecyclerView.withMarginDecoration(sizeDp: Int, edgeFlags: Int) {
    addItemDecoration(
        MarginItemDecoration(
            sizeDp,
            edgeFlags
        )
    )
}

class MarginItemDecoration(sizeDp: Int, private val edgeFlags: Int) : RecyclerView.ItemDecoration() {

    private inline val Float.dpToPx: Float get() = this * Resources.getSystem().displayMetrics.density
    private inline val Int.dpToPx: Int get() = toFloat().dpToPx.toInt()
    private val sizePx = sizeDp.dpToPx

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        if (edgeFlags and ViewDragHelper.EDGE_LEFT > 0) outRect.left += sizePx
        if (edgeFlags and ViewDragHelper.EDGE_TOP > 0) outRect.top += sizePx
        if (edgeFlags and ViewDragHelper.EDGE_RIGHT > 0) outRect.right += sizePx
        if (edgeFlags and ViewDragHelper.EDGE_BOTTOM > 0) outRect.bottom += sizePx
    }
}

fun RecyclerView.onScroll(
    callbackOnScrolled: (Int, Int) -> Unit
) {
    object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            callbackOnScrolled.invoke(dx,dy)
        }
    }
}




/** [GuideView]
 *
 * @param view      targetView
 * @param title     judul pesan
 * @param desc      isi pesan
 *
 * */
fun View.guide(
    title: String,
    desc: String = "",
    block : () -> Unit = { }
) {
    GuideView.Builder(context)
        .setTitle(title)
        .setContentText(desc)
        .setDismissType(DismissType.outside)
        .setTargetView(this)
        .setGuideListener { block.invoke() }
        .build()
        .show()
}


// -----------------------------------------------------------------
/** [ImageView]
// ---------------------------------------------------------------*/
fun ImageView.imageDrawable(@DrawableRes id : Int) = setImageDrawable(context.drawable(id))



inline fun <reified T : View> View.find(@IdRes id: Int) : T = findViewById(id)
inline fun <reified T : View> View.findOptional(@IdRes id: Int) : T? = findViewById(id) as? T



// —————————————————————————————————————————————————————————————————
/** [Spinner]
// —————————————————————————————————————————————————————————————————*/
fun Spinner.onItemSelected(
    onSelected : (Int) -> Unit,
    onNothingSelected : () -> Unit = { }
) {
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

        override fun onNothingSelected(parent: AdapterView<*>?) {
            onNothingSelected.invoke()
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            onSelected.invoke(position)
        }

    }
}
