package id.herdroid.ecommercemandiri.view.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.herdroid.ecommercemandiri.databinding.FragmentCategoryBinding
import id.herdroid.ecommercemandiri.view.adapter.CategoryAdapter
import id.herdroid.ecommercemandiri.view.home.HomeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment : Fragment() {
    private lateinit var binding: FragmentCategoryBinding
    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        categoryAdapter = CategoryAdapter { category ->
            viewModel.loadProductsByCategory(category)
        }

        binding.rvCategories.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCategories.adapter = categoryAdapter

        lifecycleScope.launch {
            viewModel.categories.collectLatest { categoryAdapter.submitList(it) }
        }
    }
}