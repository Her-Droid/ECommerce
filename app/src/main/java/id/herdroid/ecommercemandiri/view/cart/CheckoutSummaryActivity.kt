package id.herdroid.ecommercemandiri.view.cart

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.herdroid.ecommercemandiri.data.db.CartEntity
import id.herdroid.ecommercemandiri.databinding.ActivityCheckoutSummaryBinding
import id.herdroid.ecommercemandiri.view.adapter.SummaryAdapter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CheckoutSummaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckoutSummaryBinding
    private val viewModel: CartViewModel by viewModels()
    private lateinit var adapter: SummaryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutSummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        adapter = SummaryAdapter()
        binding.rvSummary.adapter = adapter
        binding.rvSummary.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            viewModel.cart.collect { list ->
                adapter.submitList(list)
                val total = list.sumOf { it.price * it.quantity }
                binding.tvTotalSummary.text = "Total Shopping: $ ${String.format("%.2f", total)}"

                binding.btnOrderWhatsapp.setOnClickListener {
                    val message = buildWhatsAppMessage(list, total)
                    sendToWhatsApp(message)
                }
            }
        }
    }

    private fun buildWhatsAppMessage(cart: List<CartEntity>, total: Double): String {
        val builder = StringBuilder()
        builder.append("Hello I would like to place an order:\n")
        cart.forEach {
            builder.append("- ${it.title} (${it.quantity}x) = ${String.format("%.2f", it.price * it.quantity)}\n")
        }
        builder.append("\nTotal Shopping: ${String.format("%.2f", total)}")
        return builder.toString()
    }

    private fun sendToWhatsApp(message: String) {
        val number = "085172450762"
        val url = "https://wa.me/62${number.removePrefix("0")}?text=${Uri.encode(message)}"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}
