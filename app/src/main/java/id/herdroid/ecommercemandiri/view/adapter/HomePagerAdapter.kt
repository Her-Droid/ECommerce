package id.herdroid.ecommercemandiri.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.herdroid.ecommercemandiri.view.category.CategoryFragment
import id.herdroid.ecommercemandiri.view.product.ProductFragment

class HomePagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> CategoryFragment()
        else -> ProductFragment()
    }
}
