package id.herdroid.ecommercemandiri.view.landing

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import id.herdroid.ecommercemandiri.R
import id.herdroid.ecommercemandiri.view.auth.LoginActivity
import id.herdroid.ecommercemandiri.view.home.HomeActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            val prefs = getSharedPreferences("user_session", MODE_PRIVATE)
            val hasSeenOnboarding = prefs.getBoolean("has_seen_onboarding", false)
            val savedUsername = prefs.getString("username", null)

            when {
                savedUsername != null -> {
                    // Sudah login -> langsung ke Home
                    startActivity(Intent(this, HomeActivity::class.java))
                }
                hasSeenOnboarding -> {
                    // Sudah lihat onboarding, belum login -> ke Login
                    startActivity(Intent(this, LoginActivity::class.java))
                }
                else -> {
                    // Belum lihat onboarding
                    startActivity(Intent(this, OnboardingActivity::class.java))
                }
            }
            finish()
        }, 2000)
    }
}
