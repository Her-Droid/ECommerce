package id.herdroid.ecommercemandiri.view.profile

import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Base64
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import id.herdroid.ecommercemandiri.R
import id.herdroid.ecommercemandiri.databinding.BottomsheetProfileBinding
import id.herdroid.ecommercemandiri.view.auth.LoginActivity

class ProfileBottomSheet : BottomSheetDialogFragment() {
    private var _binding: BottomsheetProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomsheetProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireContext().getSharedPreferences("user_session", android.content.Context.MODE_PRIVATE)
        val username = prefs.getString("username", null)
        if (username != null) {
            val dbRef = FirebaseDatabase.getInstance().getReference("users").child(username)

            binding.progressBar.visibility = View.VISIBLE

            dbRef.get().addOnSuccessListener { snapshot ->
                _binding?.let { binding ->
                    val name = snapshot.child("name").value.toString()
                    val imageBase64 = snapshot.child("imageBase64").value?.toString()
                    val username = snapshot.child("username").value.toString()

                    binding.tvName.text = "Name : $name"
                    binding.tvUsername.text = "Username: $username"

                    if (!imageBase64.isNullOrEmpty()) {
                        val imageBytes = Base64.decode(imageBase64, Base64.DEFAULT)
                        val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                        Glide.with(requireContext())
                            .asBitmap()
                            .load(bitmap)
                            .circleCrop()
                            .into(binding.imgProfile)
                    } else {
                        binding.imgProfile.setImageResource(R.drawable.ic_profile_blue)
                    }

                    binding.root.alpha = 0f
                    binding.root.animate()
                        .alpha(1f)
                        .setDuration(300)
                        .start()

                    binding.progressBar.visibility = View.GONE
                }
            }.addOnFailureListener {
                _binding?.progressBar?.visibility = View.GONE
            }
        }


        binding.btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val prefs = requireContext().getSharedPreferences("user_session", android.content.Context.MODE_PRIVATE)
            prefs.edit().clear().apply()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            activity?.finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
