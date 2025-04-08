package id.herdroid.ecommercemandiri.view.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.herdroid.ecommercemandiri.data.db.CartEntity
import id.herdroid.ecommercemandiri.databinding.ItemCartBinding

class CartAdapter(
    val onIncrease: (CartEntity) -> Unit,
    val onDecrease: (CartEntity) -> Unit,
    val onDelete: (CartEntity) -> Unit
) : ListAdapter<CartEntity, CartAdapter.CartViewHolder>(DIFF) {

    inner class CartViewHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CartEntity) {
            binding.tvTitle.text = item.title
            binding.tvPrice.text = "$${item.price}"
            binding.tvQuantity.text = item.quantity.toString()

            Glide.with(binding.imgProduct.context)
                .load(item.image)
                .into(binding.imgProduct)

            binding.btnIncrease.setOnClickListener { onIncrease(item) }
            binding.btnDecrease.setOnClickListener { onDecrease(item) }
            binding.btnDelete.setOnClickListener { onDelete(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<CartEntity>() {
            override fun areItemsTheSame(oldItem: CartEntity, newItem: CartEntity) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: CartEntity, newItem: CartEntity) = oldItem == newItem
        }
    }
}
