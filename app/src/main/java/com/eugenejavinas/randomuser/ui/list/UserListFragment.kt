package com.eugenejavinas.randomuser.ui.list

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eugenejavinas.randomuser.R
import com.eugenejavinas.randomuser.common.utils.Resource
import com.eugenejavinas.randomuser.databinding.FragmentUserListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListFragment : Fragment() {

    private val viewModel: UserListViewModel by viewModels()

    private lateinit var binding: FragmentUserListBinding
    private lateinit var adapter: UserListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUsersLiveData().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    Log.i(TAG, "onViewCreated. Loading... ")
                    binding.fab.isEnabled = false
                }
                is Resource.Success -> {
                    Log.d(TAG, "onViewCreated. List: ${it.data}")
                    adapter.submitList(it.data)
                    binding.fab.isEnabled = true
                }
                is Resource.Error -> {
                    Log.e(TAG, "onViewCreated. Error: ${it.error.stackTraceToString()}", )
                    binding.fab.isEnabled = true
                }
            }
        }
        adapter = UserListAdapter {
            // navigate to UserDetail
        }
        binding.userRecyclerView.adapter = adapter
        binding.fab.setOnClickListener {
            viewModel.fetchUsers(10)
            // TODO: change to dialog
        }

    }

    companion object {
        private const val TAG = "UserListFragment"
    }
}