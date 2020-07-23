package id.web.adit.starter.ui.dashboard

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import id.web.adit.core.utils.ext.value
import id.web.adit.starter.R
import id.web.adit.starter.databinding.ItemReposBinding
import id.web.adit.starter.datasource.model.ReposPojoItem


class HomeAdapter(
    layout: Int = R.layout.item_repos
) : BaseQuickAdapter<ReposPojoItem, BaseViewHolder>(layout) {

    override fun convert(holder: BaseViewHolder, item: ReposPojoItem) {
        val binding = ItemReposBinding.bind(holder.itemView)
        with(binding) {
            tvRepoName.value = "# ${item.name} "
            item.description?.let { tvDescription.value = it }
            item.language?.let { tvLanguage.value = it }
            tvTotalStar.value = "${item.stargazers_count}"
            tvTotalFork.value = "${item.forks_count}"
        }
    }
}
