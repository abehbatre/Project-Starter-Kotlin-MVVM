package id.web.adit.starter.ui

import id.web.adit.core.scope.View
import id.web.adit.core.utils.MaterialColor
import id.web.adit.core.utils.ext.changeSystemBarColor
import id.web.adit.core.utils.ext.color
import id.web.adit.core.utils.ext.runWithDelay
import id.web.adit.core.utils.ext.snackbar
import id.web.adit.starter.R
import id.web.adit.starter.ui.__base.BaseActivity
import id.web.adit.starter.ui.dashboard.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

@View
class MainActivity : BaseActivity(R.layout.activity_main) {

    override fun onCreated() {
        changeSystemBarColor(R.color.systemBar)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, HomeFragment.instance)
            .commitNow()
    }

    override fun onBackPressed() {
        when {
            back2Exit -> { super.onBackPressed(); return }
        }
        this.back2Exit = true
        lyt_root.snackbar(
            msg = "‼ Tab once more to exit",
            bgColor = MaterialColor.BLACK,
            textColor = MaterialColor.WHITE
        )
        runWithDelay(2000) { back2Exit = false }
    }
}
