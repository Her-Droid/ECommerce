package id.herdroid.ecommercemandiri.view.product

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.herdroid.ecommercemandiri.databinding.FragmentProductBinding
import id.herdroid.ecommercemandiri.view.adapter.ProductAdapter
import id.herdroid.ecommercemandiri.view.detail.ProductDetailActivity
import id.herdroid.ecommercemandiri.view.home.HomeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductFragment : Fragment() {
    private lateinit var binding: FragmentProductBinding
    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        productAdapter = ProductAdapter { product ->
            val intent = Intent(requireContext(), ProductDetailActivity::class.java)
            intent.putExtra("product_id", product.id)
            startActivity(intent)
        }

        binding.rvProducts.layoutManager = LinearLayoutManager(requireContext())
        binding.rvProducts.adapter = productAdapter

        lifecycleScope.launch {
            viewModel.products.collectLatest { productAdapter.submitList(it) }
        }
    }
}