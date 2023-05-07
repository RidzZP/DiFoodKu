package com.example.testinglks4.Fragment

import MortyAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testinglks4.Api
import com.example.testinglks4.R
import com.example.testinglks4.ResponseMorty
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {
    private lateinit var rvCharacters: RecyclerView
    private lateinit var adapter: MortyAdapter

    private lateinit var btnTotal: Button

    private var quantity = 0
    private var totalHarga = 0


    private lateinit var api: Api

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        rvCharacters = view.findViewById(R.id.rvMenu)
        rvCharacters.layoutManager = LinearLayoutManager(context)
        adapter = MortyAdapter(emptyList())
        rvCharacters.adapter = adapter

        btnTotal = view.findViewById(R.id.btnJumlah)
        updateTotalHarga()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Get instance of API interface
        val api = retrofit.create(Api::class.java)

        // Send GET request to server to get list of characters
        api.getMort().enqueue(object : Callback<ResponseMorty> {
            override fun onResponse(call: Call<ResponseMorty>, response: Response<ResponseMorty>) {
                if (response.isSuccessful) {
                    val characters = response.body()?.results?.filterNotNull() ?: emptyList()

                    adapter = MortyAdapter(characters)
                    rvCharacters.adapter = adapter

                    updateTotalHarga()
                }
            }

            override fun onFailure(call: Call<ResponseMorty>, t: Throwable) {
                Toast.makeText(context, "Network error: " + t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

     fun updateTotalHarga() {
        totalHarga = 0
        for (i in 0 until adapter.itemCount) {
            val viewHolder = rvCharacters.findViewHolderForAdapterPosition(i)
                    as? MortyAdapter.MortyViewHolder
            val harga = viewHolder?.txtHarga?.text?.toString()?.toInt() ?: 0
            val qty = viewHolder?.txtQuantity?.text?.toString()?.toInt() ?: 0
            totalHarga += harga * qty
        }

        btnTotal.text = "Total: Rp. $totalHarga"
    }
}