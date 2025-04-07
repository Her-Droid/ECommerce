package id.herdroid.ecommercemandiri.view.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productAdapter = ProductAdapter { product ->
            val intent = Intent(this, ProductDetailActivity::class.java)
            intent.putExtra("product_id", product.id)
            startActivity(intent)
        }

        categoryAdapter = CategoryAdapter { category ->
            viewModel.loadProductsByCategory(category)
        }

        binding.rvProducts.layoutManager = LinearLayoutManager(this)
        binding.rvProducts.adapter = productAdapter

        binding.rvCategories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvCategories.adapter = categoryAdapter

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_home -> {
                    viewModel.loadProducts()
                    true
                }
                R.id.menu_cart -> {
                    CartFragment().show(supportFragmentManager, "CartFragment")
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
                categoryAdapter.submitList(categories)
            }
        }

        viewModel.loadProducts()
    }

}
