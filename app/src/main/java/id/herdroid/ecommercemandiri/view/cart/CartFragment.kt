package id.herdroid.ecommercemandiri.view.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.herdroid.ecommercemandiri.R
import id.herdroid.ecommercemandiri.databinding.FragmentCartBinding
import id.herdroid.ecommercemandiri.view.adapter.CartAdapter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartFragment : Fragment() {
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

        binding.topAppBar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }


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
                    cartAdapter.submitList(list.toList())
                    val total = list.sumOf { it.price * it.quantity }
                    binding.tvTotal.text = "Total Shopping: $ ${String.format("%.2f", total)}"
                }
            }
        }

        binding.btnCheckout.setOnClickListener {
            val intent = Intent(requireContext(), CheckoutSummaryActivity::class.java)
            startActivity(intent)

        }

        binding.btnHapusSemua.setOnClickListener {
            viewModel.clearCart()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
