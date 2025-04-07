package id.herdroid.ecommercemandiri.view.auth

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import id.herdroid.ecommercemandiri.databinding.ActivityRegisterBinding
import java.io.ByteArrayOutputStream

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val database = FirebaseDatabase.getInstance().reference
    private var selectedImageUri: Uri? = null
    private val IMAGE_REQUEST_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgProfile.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_REQUEST_CODE)
        }

        binding.btnRegister.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val name = binding.etNama.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (username.isNotEmpty() && name.isNotEmpty() && password.isNotEmpty()) {
                val imageBase64 = selectedImageUri?.let { uri ->
                    val bitmap = getCorrectlyOrientedBitmap(uri)
                    encodeImageToBase64(bitmap)
                }

                val userData = mapOf(
                    "username" to username,
                    "name" to name,
                    "password" to password,
                    "imageBase64" to (imageBase64 ?: "")
                )

                database.child("users").child(username).get().addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        showError("Username sudah digunakan")
                    } else {
                        database.child("users").child(username).setValue(userData)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(this, "Pendaftaran berhasil", Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this, LoginActivity::class.java))
                                    finish()
                                } else {
                                    showError("Gagal menyimpan data: ${task.exception?.message}")
                                }
                            }
                    }
                }.addOnFailureListener {
                    showError("Terjadi kesalahan koneksi: ${it.message}")
                }

            } else {
                showError("Semua field wajib diisi")
            }
        }

    }

    private fun encodeImageToBase64(bitmap: Bitmap): String {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
        val byteArray = outputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    private fun getCorrectlyOrientedBitmap(uri: Uri): Bitmap {
        val inputStream = contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream?.close()

        // Ambil orientasi
        val filePathColumn = arrayOf(MediaStore.Images.Media.ORIENTATION)
        val cursor = contentResolver.query(uri, filePathColumn, null, null, null)
        var rotation = 0
        if (cursor != null && cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
            rotation = cursor.getInt(columnIndex)
            cursor.close()
        }

        val matrix = android.graphics.Matrix()
        matrix.postRotate(rotation.toFloat())
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }


    private fun showError(message: String) {
        binding.tvError.text = message
        binding.tvError.visibility = android.view.View.VISIBLE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data
            binding.imgProfile.setImageURI(selectedImageUri)
        }
    }
}
