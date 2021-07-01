package com.epam.awards.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.epam.awards.R
import com.epam.awards.databinding.ItemAwardBinding
import com.epam.awards.presentation.awards.AwardView

class AwardsAdapter : ListAdapter<AwardView, AwardsAdapter.AwardViewHolder>(AwardDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AwardViewHolder =
        AwardViewHolder(
            ItemAwardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: AwardViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class AwardViewHolder(private val binding: ItemAwardBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(award: AwardView) {
            val nomineeText = itemView.resources.getString(R.string.nominee_text)
            val nominatorText = itemView.resources.getString(R.string.nominator_text)

            binding.title.text = award.title
            binding.nominee.text = String.format(nomineeText, award.nomineeFullName)
            binding.nominator.text = String.format(nominatorText, award.nominatorFullName)
        }
    }
}

class AwardDiffCallBack : DiffUtil.ItemCallback<AwardView>() {
    override fun areItemsTheSame(oldItem: AwardView, newItem: AwardView): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: AwardView, newItem: AwardView): Boolean =
        oldItem.nomineeId == newItem.nomineeId
}
