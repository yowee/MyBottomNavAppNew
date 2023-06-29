package com.example.mybottomnavapp.ui.character

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mybottomnavapp.R
import com.example.mybottomnavapp.data.model.rickmorty.RickModel
import com.example.mybottomnavapp.data.remote.ApiDetails
import com.example.mybottomnavapp.databinding.FragmentListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CharacterFragment : Fragment() {

    lateinit var binding: FragmentListBinding
    var characterCount = 1
    lateinit var result: RickModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(inflater, container, false)

        loadData()

        binding.btnNextCharacter.setOnClickListener {
            characterCount++
            loadData()
        }


        return binding.root
    }

    private fun loadData() {
        CoroutineScope(Dispatchers.Main).launch {

            result = ApiDetails.rickClient.getRickCharacter(characterCount)

            binding.apply {
                tvList.text = result.name
                tvSpecies.text = result.species
                tvOrigin.text = result.origin.name
                tvlocation.text = result.location.name
                tvGender.text = result.gender
            }

            changeStatus(result.status)

            binding.rvEpisodes.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = EpisodesAdapter(result.episode){ position -> onListItemClick(position) }
            }

            Glide
                .with(requireContext())
                .load(result.image)
    //                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.imageView)
        }
    }

    fun changeStatus(s : String){
        if(s=="Alive") binding.ivstatus.setImageResource(R.drawable.active)
        else binding.ivstatus.setImageResource(R.drawable.inactive)
    }
    private fun onListItemClick(position: Int) {
        Toast.makeText(context, result.episode[position].toString(), Toast.LENGTH_SHORT).show()

        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(result.episode[position].toString())))
    }
}