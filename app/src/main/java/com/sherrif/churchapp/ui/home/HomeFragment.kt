package com.sherrif.churchapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.google.gson.GsonBuilder
import com.sherrif.churchapp.LoginActivity
import com.sherrif.churchapp.MainActivity
import com.sherrif.churchapp.R
import com.sherrif.churchapp.adapters.DeskAdapter
import com.sherrif.churchapp.constants.Constants
import com.sherrif.churchapp.databinding.FragmentHomeBinding
import com.sherrif.churchapp.helpers.ApiHelper
import com.sherrif.churchapp.helpers.PrefsHelper
import com.sherrif.churchapp.models.Desk

import com.sherrif.churchapp.ui.profile.ProfileFragment
import org.json.JSONArray
import org.json.JSONObject

class HomeFragment : Fragment() {
    private lateinit var recyclerview: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var handler: Handler
    private lateinit var swiper: SwipeRefreshLayout
    private lateinit var adapter: DeskAdapter
    private lateinit var itemList: List<Desk>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize views
        recyclerview = root.findViewById(R.id.recyclerview)
        progressBar = root.findViewById(R.id.progress)
        swiper = root.findViewById(R.id.swiperefresh)
        handler = Handler(Looper.getMainLooper())

//        setupView(root)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateViews(view)

        val layoutManager = LinearLayoutManager(requireContext())
        recyclerview.layoutManager = layoutManager
        recyclerview.setHasFixedSize(true)

        adapter = DeskAdapter(requireContext())
        recyclerview.adapter = adapter

        fetchData()
        swiper.setOnRefreshListener {
            fetchData()
        }
    }

    private fun updateViews(root: View) {
        val user = root.findViewById<MaterialTextView>(R.id.user)
        val signin = root.findViewById<MaterialButton>(R.id.signin)


        signin.visibility = View.GONE


        val token = PrefsHelper.getPrefs(requireContext(), "access_token")
        if (token.isEmpty()) {
            user.text = "Not Logged In"
            signin.visibility = View.VISIBLE
            signin.setOnClickListener {
                startActivity(Intent(requireContext(), LoginActivity::class.java))
            }
        } else {
            val surname = PrefsHelper.getPrefs(requireContext(), "surname")
            user.text = "Welcome $surname"
        }
    }
    private fun fetchData() {
        val api = Constants.BASE_URL + "/deskpost"
        val helper = ApiHelper(requireContext())
        helper.get(api, object : ApiHelper.CallBack {
            override fun onSuccess(result: JSONArray?) {
                val deskjson = GsonBuilder().create()
                itemList = deskjson.fromJson(result.toString(), Array<Desk>::class.java).toList()
                adapter.setListItems(itemList)
                progressBar.visibility = View.GONE
                swiper.isRefreshing = false

                // Ensure recyclerview is updated with the adapter
                recyclerview.adapter = adapter

//                val desksearch = requireView().findViewById<EditText>(R.id.search)
//                desksearch.addTextChangedListener(object : TextWatcher {
//                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
//
//                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                        filter(p0.toString())
//                    }
//
//                    override fun afterTextChanged(p0: Editable?) {}
//                })
            }

            override fun onSuccess(result: JSONObject?) {
                // Handle JSONObject response if needed
            }

            override fun onFailure(result: String?) {
                Toast.makeText(requireContext(), "Error: $result", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE
            }
        })
    }


    private fun filter(text: String) {
        val filteredList: ArrayList<Desk> = ArrayList()
        for (item in itemList) {
            if (item.desk_title.lowercase().contains(text.lowercase())) {
                filteredList.add(item)
            }
        }
        adapter.filterList(filteredList)
    }
}
