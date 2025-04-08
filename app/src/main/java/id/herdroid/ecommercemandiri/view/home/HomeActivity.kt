package id.herdroid.ecommercemandiri.view.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import id.herdroid.ecommercemandiri.R
import id.herdroid.ecommercemandiri.databinding.ActivityHomeBinding
import id.herdroid.ecommercemandiri.view.adapter.CategoryAdapter
import id.herdroid.ecommercemandiri.view.adapter.ProductAdapter
import id.herdroid.ecommercemandiri.view.cart.CartFragment
import id.herdroid.ecommercemandiri.view.detail.ProductDetailActivity
import id.herdroid.ecommercemandiri.view.profile.ProfileBottomSheet
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private var selectedCategory: String = "All"
    private var cartBadgeText: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val prefs = getSharedPreferences("user_session", MODE_PRIVATE)
        if (!prefs.getBoolean("has_seen_onboarding", false)) {
            prefs.edit().putBoolean("has_seen_onboarding", true).apply()
        }

        setSupportActionBar(binding.topAppBar)

        productAdapter = ProductAdapter { product ->
            val intent = Intent(this, ProductDetailActivity::class.java)
            intent.putExtra("product_id", product.id)
            startActivity(intent)
        }

        categoryAdapter = CategoryAdapter { category ->
            selectedCategory = category
            categoryAdapter.setSelectedCategory(category)
            if (category == "All") {
                viewModel.loadProducts()
            } else {
                viewModel.loadProductsByCategory(category)
            }
        }


        binding.rvProducts.layoutManager = GridLayoutManager(this, 2)
        binding.rvProducts.adapter = productAdapter

        binding.rvCategories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvCategories.adapter = categoryAdapter


        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_home -> {
                    viewModel.loadProducts()
                    true
                }
                R.id.menu_profile -> {
                    ProfileBottomSheet().show(supportFragmentManager, "ProfileBottomSheet")
                    true
                }
                else -> false
            }
        }

        lifecycleScope.launch {
            viewModel.products.collectLatest { products ->
                productAdapter.submitList(products)
            }
        }

        lifecycleScope.launch {
            viewModel.categories.collectLatest { categories ->
                val allCategories = mutableListOf("All")
                allCategories.addAll(categories)
                categoryAdapter.submitList(allCategories)

                viewModel.loadProducts()
                categoryAdapter.setSelectedCategory("All")
            }
        }

        viewModel.loadProducts()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_bar_menu, menu)
        val menuItem = menu?.findItem(R.id.menu_cart)
        val actionView = menuItem?.actionView ?: layoutInflater.inflate(R.layout.cart_action_view, null)
        menuItem?.actionView = actionView

        if (menuItem != null) {
            actionView.setOnClickListener {
                onOptionsItemSelected(menuItem)
            }
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_cart -> {
                supportFragmentManager.beginTransaction()
                    .replace(android.R.id.content, CartFragment())
                    .addToBackStack(null)
                    .commit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



}
