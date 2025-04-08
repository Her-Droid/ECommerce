package id.herdroid.ecommercemandiri.view.landing

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.herdroid.ecommercemandiri.R
import id.herdroid.ecommercemandiri.data.model.OnboardingItem
import id.herdroid.ecommercemandiri.databinding.ActivityOnboardingBinding
import id.herdroid.ecommercemandiri.view.adapter.OnboardingAdapter
import id.herdroid.ecommercemandiri.view.auth.LoginActivity
import id.herdroid.ecommercemandiri.view.auth.RegisterActivity

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val onboardingItems = listOf(
            OnboardingItem(R.drawable.ic_purchase_online, "Purchase Online !!", "Fast, easy, and secure shopping — anytime, anywhere."),
            OnboardingItem(R.drawable.ic_track_order, "Track order !!", "Easily follow your order status in real-time."),
            OnboardingItem(R.drawable.ic_get_your_order, "Get your order !!", "Receive your items quickly and hassle-free.")
        )

        val adapter = OnboardingAdapter(onboardingItems)
        binding.onboardingViewPager.adapter = adapter
        binding.indicator.setViewPager2(binding.onboardingViewPager)
        binding.btnRegister.setOnClickListener {
            setOnboardingSeen()
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            setOnboardingSeen()
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun setOnboardingSeen() {
        val prefs = getSharedPreferences("user_session", MODE_PRIVATE)
        prefs.edit().putBoolean("has_seen_onboarding", true).apply()
    }
}
