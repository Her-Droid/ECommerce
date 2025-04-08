package id.herdroid.ecommercemandiri.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.herdroid.ecommercemandiri.R
import id.herdroid.ecommercemandiri.databinding.ItemCategoryBinding

class CategoryAdapter(private val onClick: (String) -> Unit) :
    ListAdapter<String, CategoryAdapter.CategoryViewHolder>(DIFF_CALLBACK) {

    private var selectedCategory: String = "All"

    fun setSelectedCategory(category: String) {
        selectedCategory = category
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
        fun bind(category: String) {
            tvCategory.text = category
            tvCategory.setOnClickListener {
                if (category != selectedCategory) {
                    selectedCategory = category
                    notifyDataSetChanged()
                }
                onClick(category)
            }


            val isSelected = category == selectedCategory
            if (isSelected) {
                tvCategory.setBackgroundResource(R.drawable.bg_category_selected)
                tvCategory.setTextColor(ContextCompat.getColor(tvCategory.context, R.color.white))
            } else {
                tvCategory.setBackgroundResource(R.drawable.bg_category_unselected)
                tvCategory.setTextColor(ContextCompat.getColor(tvCategory.context, R.color.blue))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
        }
    }
}
