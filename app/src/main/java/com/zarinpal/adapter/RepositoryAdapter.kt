package com.zarinpal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zarinpal.databinding.ItemRepositoryBinding
import com.zarinpal.extension.toPx
import com.zarinpal.fragment.RepositoryFragment
import com.zarinpal.utils.LinearDividerDecoration

class RepositoryAdapter() :
    RecyclerView.Adapter<RepositoryAdapter.Holder>() {

    var list: MutableList<RepositoryFragment> = arrayListOf()

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            ItemRepositoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindTo(list[position])
    }

    inner class Holder(private var binding: ItemRepositoryBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {

        val topicAdapter by lazy { RepositoryTopicAdapter() }

        init {
            setupView()
        }

        private fun setupView() {

            // setup topics
            binding.rcvTopics.apply {

                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(
                    LinearDividerDecoration(
                        LinearDividerDecoration.HORIZONTAL,
                        16.toPx(),
                        false
                    )
                )
                adapter = topicAdapter
            }
        }

        fun bindTo(repository: RepositoryFragment) {
            binding.divider.visibility =
                if (layoutPosition == list.lastIndex) View.GONE else View.VISIBLE
            binding.txtRepositoryName.text = repository.name
            binding.txtRepositoryDescription.text = repository.description

            // set topics
            if (getTopics(repository).isNotEmpty()) {
                binding.rcvTopics.visibility = View.VISIBLE
                topicAdapter.list = getTopics(repository).toMutableList()
                topicAdapter.notifyDataSetChanged()
            } else
                binding.rcvTopics.visibility = View.GONE
        }
    }

    private fun getTopics(repository: RepositoryFragment) =
        repository.repositoryTopics.nodes?.mapNotNull { it?.fragments?.topicFragment }
            ?: emptyList()
}