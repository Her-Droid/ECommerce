package id.herdroid.ecommercemandiri.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.herdroid.ecommercemandiri.data.model.ProductResponse
import id.herdroid.ecommercemandiri.databinding.ItemProductBinding
import id.herdroid.ecommercemandiri.domain.model.Product

class ProductAdapter(
    private val onClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private val productList = mutableListOf<Product>()

    fun submitList(products: List<Product>) {
        productList.clear()
        productList.addAll(products)
        notifyDataSetChanged()
    }

    inner class ProductViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.tvTitle.text = product.title
            binding.tvPrice.text = "$${product.price}"
            binding.ivProduct.load(product.image)
            binding.root.setOnClickListener { onClick(product) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount() = productList.size
}