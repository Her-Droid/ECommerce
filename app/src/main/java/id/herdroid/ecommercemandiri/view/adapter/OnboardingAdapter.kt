package id.herdroid.ecommercemandiri.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.herdroid.ecommercemandiri.R
import id.herdroid.ecommercemandiri.data.model.OnboardingItem

class OnboardingAdapter(private val items: List<OnboardingItem>) :
    RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {

    inner class OnboardingViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: OnboardingItem) {
            view.findViewById<ImageView>(R.id.imageOnboarding).setImageResource(item.imageResId)
            view.findViewById<TextView>(R.id.titleOnboarding).text = item.title
            view.findViewById<TextView>(R.id.descOnboarding).text = item.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_onboarding, parent, false)
        return OnboardingViewHolder(view)
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
