package id.herdroid.ecommercemandiri.view.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.herdroid.ecommercemandiri.databinding.ActivityLoginBinding
import id.herdroid.ecommercemandiri.view.home.HomeActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            if (username.isNotEmpty() && password.isNotEmpty()) {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            } else {
                binding.tvError.text = "Username atau Password kosong"
            }
        }
    }
}
