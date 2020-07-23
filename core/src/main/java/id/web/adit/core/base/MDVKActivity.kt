@file:Suppress("KDocUnresolvedReference", "ConstantConditionIf")
package id.web.adit.core.base

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import id.web.adit.core.R
import id.web.adit.core.utils.ext.changeSystemBarColor

// —————————————————————————————————————————————————————————————————
// @formatter:off ==> don't delete me
/** ————————————————————————————————————————————————————————————————
 *
 * @param layout  LayoutRes (optional since gradle 4.0 release : viewBinding)
 *
 * @author : [AdityaPratama]
 * @create : Mei 2019
 *
 * Base class for activities that enables composition of higher level components.
 * Rather than all functionality being built directly into this class, only the minimal set of
 * lower level building blocks are included. Higher level components can then be used as needed
 * without enforcing a deep Activity class hierarchy or strong coupling between components.
 * ------------------------------------------------------------------------------------
 */
abstract class MDVKActivity(
     @LayoutRes private val layout: Int = 0
) : AppCompatActivity() {

    abstract fun onCreated()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (layout != 0) setContentView(layout)

        changeSystemBarColor(R.color.colorPrimary)
        onCreated()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}
