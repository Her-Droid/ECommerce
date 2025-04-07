package id.herdroid.ecommercemandiri.view.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import id.herdroid.ecommercemandiri.databinding.FragmentCartBinding
import id.herdroid.ecommercemandiri.view.adapter.CartAdapter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CartViewModel by viewModels()
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartAdapter = CartAdapter(
            onIncrease = { item -> viewModel.increaseQuantity(item) },
            onDecrease = { item -> viewModel.decreaseQuantity(item) },
            onDelete = { item -> viewModel.removeFromCart(item) }
        )

        binding.rvCart.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCart.adapter = cartAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
                viewModel.cart.collect { list ->
                    cartAdapter.submitList(list)
                    val total = list.sumOf { it.price * it.quantity }
                    binding.tvTotal.text = "Total: $${String.format("%.2f", total)}"
                }
            }
        }

        binding.btnCheckout.setOnClickListener {
            viewModel.checkout()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
