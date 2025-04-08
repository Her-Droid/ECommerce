package id.herdroid.ecommercemandiri.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.herdroid.ecommercemandiri.data.db.CartEntity
import id.herdroid.ecommercemandiri.databinding.ItemCartBinding

class SummaryAdapter : ListAdapter<CartEntity, SummaryAdapter.SummaryViewHolder>(DIFF) {

    inner class SummaryViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CartEntity) {
            binding.tvTitle.text = item.title
            binding.tvPrice.text = "$ ${item.price}"
            binding.tvQuantity.text = item.quantity.toString()
            Glide.with(binding.imgProduct.context).load(item.image).into(binding.imgProduct)

            binding.btnIncrease.visibility = View.GONE
            binding.btnDecrease.visibility = View.GONE
            binding.btnDelete.visibility = View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SummaryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SummaryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<CartEntity>() {
            override fun areItemsTheSame(oldItem: CartEntity, newItem: CartEntity) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: CartEntity, newItem: CartEntity) = oldItem == newItem
        }
    }
}
