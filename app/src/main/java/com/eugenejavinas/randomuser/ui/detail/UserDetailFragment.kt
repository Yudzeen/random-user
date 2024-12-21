package com.eugenejavinas.randomuser.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.eugenejavinas.randomuser.R
import com.eugenejavinas.randomuser.databinding.FragmentUserDetailBinding

class UserDetailFragment : Fragment() {

    private val args: UserDetailFragmentArgs by navArgs()

    private lateinit var binding: FragmentUserDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindArgs()
        setupToolbarNavigation()
        loadUserImage()
    }

    private fun bindArgs() {
        with(binding) {
            user = args.user
            lifecycleOwner = viewLifecycleOwner
            executePendingBindings()
        }
    }

    private fun setupToolbarNavigation() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun loadUserImage() {
        Glide.with(requireContext())
            .load(args.user.picture.large)
            .apply(RequestOptions()
                .placeholder(R.drawable.picture_placeholder)
                .error(R.drawable.picture_error))
            .into(binding.userImage)
    }
}