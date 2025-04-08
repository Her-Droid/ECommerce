package id.herdroid.ecommercemandiri.view.detail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import id.herdroid.ecommercemandiri.databinding.ActivityProductDetailBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding
    private val viewModel: ProductDetailViewModel by lazy {
        androidx.lifecycle.ViewModelProvider(this)[ProductDetailViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val productId = intent.getIntExtra("product_id", -1)
        if (productId != -1) {
            viewModel.loadProductDetail(productId)
        }

        observeProduct()
    }

    private fun observeProduct() {
        lifecycleScope.launch {
            viewModel.product.collectLatest { product ->
                product?.let {
                    binding.tvTitle.text = it.title
                    binding.tvPrice.text = "$${it.price}"
                    binding.tvDescription.text = it.description
                    Glide.with(this@ProductDetailActivity).load(it.image).into(binding.ivProduct)

                    binding.btnAddToCart.setOnClickListener {
                        binding.btnAddToCart.isEnabled = false
                        binding.btnAddToCart.text = "Loading..."

                        lifecycleScope.launch {
                            viewModel.addToCart(product)
                            Toast.makeText(this@ProductDetailActivity, "Product Successfully Added to Cart", Toast.LENGTH_SHORT).show()

                            // Reset button state
                            binding.btnAddToCart.isEnabled = true
                            binding.btnAddToCart.text = "Add To Cart"
                        }
                    }

                }
            }
        }
    }
}
