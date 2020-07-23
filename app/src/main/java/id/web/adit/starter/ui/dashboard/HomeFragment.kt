package id.web.adit.starter.ui.dashboard

import androidx.lifecycle.observe
import com.chad.library.adapter.base.BaseQuickAdapter
import id.web.adit.core.scope.View
import id.web.adit.core.utils.ext.click
import id.web.adit.core.utils.ext.customDialog
import id.web.adit.core.utils.ext.withDividerDecoration
import id.web.adit.starter.R
import id.web.adit.starter.databinding.FragmentMainBinding
import id.web.adit.starter.networking.Status
import id.web.adit.starter.ui.__base.BaseFragment
import id.web.adit.starter.utils.viewBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

@View
class HomeFragment : BaseFragment(R.layout.fragment_main) {

    private val adapter by lazy { HomeAdapter() }
    private val binding by viewBinding(FragmentMainBinding::bind)
    private val viewModel by sharedViewModel<HomeViewModel>()

    companion object {
        val instance = HomeFragment()
    }

    override fun onCreated() {
        with(binding) {

            pullRefresh.setOnRefreshListener { loadData() }
            tvTitle.click { loadData() }
            rvList.adapter = adapter
            rvList.withDividerDecoration()
            adapter.animationEnable = true

            // adapter click
            adapter.setOnItemClickListener { _: BaseQuickAdapter<*, *>, _: android.view.View, position: Int ->
                val datas = adapter.getItem(position)
                baseActicity().customDialog(
                    cornerRadius = 20f,
                    title = "Detail",
                    message = " ${datas.description} ",
                    lottieFile = R.raw.lottie_process_question,
                    positiveButtonText = "OK"
                )
            }

            loadData()
        }
    }

    private fun loadData() {
        viewModel.getListCustomersCurrent().ldata().observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    binding.rvList.showShimmerAdapter()
                    binding.pullRefresh.isRefreshing = true
                }

                Status.SUCCESS -> {
                    binding.rvList.hideShimmerAdapter()
                    binding.pullRefresh.isRefreshing = false
                    it.data?.let { adapter.setList(it) }
                }

                Status.ERROR -> {
                    binding.rvList.hideShimmerAdapter()
                    binding.pullRefresh.isRefreshing = false
                    baseActicity().customDialog(
                        title = "UPS",
                        message = it.message!!,
                        positiveButtonText = "OK",
                        lottieFile = R.raw.lottie_error
                    )
                }
            }
        }
    }

}
