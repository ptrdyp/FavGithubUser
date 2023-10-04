package com.dicoding.favgithubuser.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.favgithubuser.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var binding: FragmentFollowBinding
//    Hindari mendeklarasi kelas Binding dengan lateinit karena hanya akan menyebabkan memory leaks.

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()

        detailViewModel = ViewModelProvider(
            requireActivity(), ViewModelProvider.NewInstanceFactory()
        )[DetailViewModel::class.java]

        arguments?.let {
            val position = it.getInt(ARG_POSITION)
            val username = it.getString(ARG_USERNAME)
            detailViewModel.getFollowers(username.toString())
            detailViewModel.getFollowings(username.toString())
            detailViewModel.isLoading.observe(viewLifecycleOwner) {showLoading(it)}
            if (position == 1){
                detailViewModel.followers.observe(viewLifecycleOwner) {
                    adapter.setList(it)
                }
            } else{
                detailViewModel.followings.observe(viewLifecycleOwner) {
                    adapter.setList(it)
                }
            }
        }
    }

    private fun setAdapter(){
        adapter = UserAdapter()
        binding.rvFollow.adapter = adapter
        binding.rvFollow.layoutManager = LinearLayoutManager(requireActivity())
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