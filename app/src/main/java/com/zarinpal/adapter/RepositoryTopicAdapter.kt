package com.zarinpal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zarinpal.databinding.ItemRepositoryBinding
import com.zarinpal.databinding.ItemRepositoryTopicBinding
import com.zarinpal.fragment.RepositoryFragment
import com.zarinpal.fragment.TopicFragment

class RepositoryTopicAdapter() :
    RecyclerView.Adapter<RepositoryTopicAdapter.Holder>() {

    var list: MutableList<TopicFragment> = arrayListOf()

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            ItemRepositoryTopicBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindTo(list[position])
    }

    inner class Holder(private var binding: ItemRepositoryTopicBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {

        fun bindTo(topic: TopicFragment) {
            binding.txtTopic.text = topic.topic.name
        }
    }
}