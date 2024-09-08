package com.sherrif.churchapp.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.sherrif.churchapp.MainActivity
import com.sherrif.churchapp.helpers.PrefsHelper
import com.sherrif.churchapp.R
import com.sherrif.churchapp.databinding.FragmentProfileBinding
import org.json.JSONObject

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val notificationsViewModel =
//            ViewModelProvider(this).get(DependantsFragment::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //        fetch the textviews
        val surname : MaterialTextView = binding.surname
        val others : MaterialTextView = binding.others
//        val email : MaterialTextView = binding.email
        val phone : MaterialTextView = binding.phone
        val gender : MaterialTextView = binding.gender
        val dob : MaterialTextView = binding.dob


        //get member from shared preferences using member userObject key
        val member = PrefsHelper.getPrefs(requireContext(),"userObject")
        //convert to json object
        val user = JSONObject(member)
        //get surname
        surname.text = user.getString("surname")
        others.text = user.getString("other")
        phone.text = user.getString("phone")
        gender.text = user.getString("gender")
        dob.text = user.getString("dob")


//        //        my buttons
//        val logout : MaterialButton =binding.logout
//        val Back : MaterialButton = binding.backtomain
//        logout.setOnClickListener {
//       PrefsHelper.clearPrefs(requireContext())
//                startActivity(Intent(requireContext(), MainActivity::class.java))
//                requireActivity().finishAffinity()
//        }
//        Back.setOnClickListener{
////            intent back to main
//            startActivity(Intent(requireContext(), MainActivity::class.java))
//        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}