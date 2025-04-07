package id.herdroid.ecommercemandiri.view.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import id.herdroid.ecommercemandiri.databinding.ActivityLoginBinding
import id.herdroid.ecommercemandiri.view.home.HomeActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val database = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("user_session", MODE_PRIVATE)
        val savedUsername = prefs.getString("username", null)
        if (savedInstanceState != null) {
            binding.etUsername.setText(savedInstanceState.getString("username"))
            binding.etPassword.setText(savedInstanceState.getString("password"))
        }
        if (savedUsername != null) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
        binding.btnLogin.setOnClickListener {
            val inputUsername = binding.etUsername.text.toString().trim()
            val inputPassword = binding.etPassword.text.toString().trim()

            if (inputUsername.isNotEmpty() && inputPassword.isNotEmpty()) {
                // Cek langsung di node users/{username}
                database.child("users").child(inputUsername)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()) {
                                val password = snapshot.child("password").getValue(String::class.java)

                                if (inputPassword == password) {
                                    Toast.makeText(this@LoginActivity, "Login sukses", Toast.LENGTH_SHORT).show()

                                    val prefs = getSharedPreferences("user_session", MODE_PRIVATE)
                                    prefs.edit().putString("username", inputUsername).apply()

                                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                                    finish()
                                } else {
                                    showError("Password salah")
                                }
                            } else {
                                showError("Username tidak ditemukan")
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            showError("Terjadi kesalahan: ${error.message}")
                        }
                    })
            } else {
                showError("Field tidak boleh kosong")
            }

        }


        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun showError(message: String) {
        binding.tvError.text = message
        binding.tvError.visibility = View.VISIBLE
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("username", binding.etUsername.text.toString())
        outState.putString("password", binding.etPassword.text.toString())
    }

}
