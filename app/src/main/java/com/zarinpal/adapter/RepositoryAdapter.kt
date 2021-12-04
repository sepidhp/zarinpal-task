package com.zarinpal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zarinpal.databinding.ItemRepositoryBinding
import com.zarinpal.fragment.RepositoryFragment

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
    ), View.OnClickListener {

        init {
            setupView()
        }

        private fun setupView() {}

        fun bindTo(repository: RepositoryFragment) {
            binding.divider.visibility =
                if (layoutPosition == list.lastIndex) View.GONE else View.VISIBLE
            binding.txtRepositoryName.text = repository.name
            binding.txtRepositoryDescription.text = repository.description
        }

        override fun onClick(v: View?) {}
    }
}