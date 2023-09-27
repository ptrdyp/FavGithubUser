package com.dicoding.favgithubuser.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.favgithubuser.data.remote.response.ItemsItem
import com.dicoding.favgithubuser.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {

    private val detailViewModel: DetailViewModel by viewModels<DetailViewModel>()
    private lateinit var binding: FragmentFollowBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val position = it.getInt(ARG_POSITION)
            val username = it.getString(ARG_USERNAME)
            detailViewModel.getFollowers(username.toString())
            detailViewModel.getFollowings(username.toString())
            detailViewModel.isLoading.observe(viewLifecycleOwner) {showLoading(it)}
            if (position == 1){
                detailViewModel.followers.observe(viewLifecycleOwner) {setList(it)}
            } else{
                detailViewModel.followings.observe(viewLifecycleOwner) {setList(it)}
            }
        }
    }

    fun setList(item: List<ItemsItem>){
        val listUser = FollowAdapter(item)
        binding.rvFollow.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollow.adapter = listUser
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility =
            if (state) View.VISIBLE else View.GONE
    }

    companion object{
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }

}