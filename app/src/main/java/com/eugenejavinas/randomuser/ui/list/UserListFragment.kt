package com.eugenejavinas.randomuser.ui.list

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.eugenejavinas.randomuser.R
import com.eugenejavinas.randomuser.common.utils.MAX_USERS_PER_REQUEST
import com.eugenejavinas.randomuser.common.utils.Resource
import com.eugenejavinas.randomuser.databinding.DialogEnterUserCountContentBinding
import com.eugenejavinas.randomuser.databinding.FragmentUserListBinding
import com.google.android.material.snackbar.Snackbar
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
                    binding.loadingIndicator.isVisible = true
                    binding.fab.isEnabled = false
                }
                is Resource.Success -> {
                    Log.d(TAG, "onViewCreated. List: ${it.data}")
                    binding.loadingIndicator.isVisible = false
                    adapter.submitList(it.data)
                    binding.fab.isEnabled = true
                }
                is Resource.Error -> {
                    Log.e(TAG, "onViewCreated. Error: ${it.error.stackTraceToString()}", )
                    binding.loadingIndicator.isVisible = false
                    binding.fab.isEnabled = true
                    Snackbar.make(requireView(),
                        getString(R.string.generic_error_message), Snackbar.LENGTH_LONG).show()
                }
            }
        }
        adapter = UserListAdapter { user ->
            UserListFragmentDirections.actionUserListFragmentToUserDetailFragment(user).let {
                findNavController().navigate(it)
            }
        }
        binding.userRecyclerView.adapter = adapter
        binding.fab.setOnClickListener {
            showEnterUserCountDialog()
        }
    }

    private fun showEnterUserCountDialog() {
        val dialogContentBinding = DialogEnterUserCountContentBinding.inflate(layoutInflater)

        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.enter_user_count_dialog_title, MAX_USERS_PER_REQUEST))
            .setView(dialogContentBinding.root)
            .setPositiveButton(R.string.submit) { _, _ ->
                try {
                    val count = dialogContentBinding.userCountTextField.text.toString().toInt()
                    if (count > MAX_USERS_PER_REQUEST) {
                        showInvalidInputToast()
                    } else {
                        viewModel.fetchUsers(count)
                    }
                } catch (e: NumberFormatException) {
                    showInvalidInputToast()
                }
            }
            .setNeutralButton(R.string.cancel, { dialog, _ -> dialog.dismiss() })
            .show()
    }

    private fun showInvalidInputToast() {
        Toast.makeText(requireContext(),
            getString(R.string.invalid_input), Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val TAG = "UserListFragment"
    }
}