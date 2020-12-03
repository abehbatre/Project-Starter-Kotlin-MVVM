@file:Suppress("KDocUnresolvedReference")

package id.web.adit.core.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

// —————————————————————————————————————————————————————————————————
// @formatter:off ==> don't delete me
/** ————————————————————————————————————————————————————————————————
 * @param layout  LayoutRes (optional since gradle 4.0 release : viewBinding)
 *
 * @author : [AdityaPratama]
 * @create : Mei 2019
 *
 * Base class for fragments that enables composition of higher level components.
 * Rather than all functionality being built directly into this class, only the minimal set of
 * lower level building blocks are included. Higher level components can then be used as needed
 * without enforcing a deep Activity class hierarchy or strong coupling between components.
 * ------------------------------------------------------------------------------------
 */
abstract class ExFragment(
    @LayoutRes val layout: Int = 0
) : Fragment(layout) {

    abstract fun onCreated()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCreated()
    }
}
