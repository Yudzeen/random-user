package com.eugenejavinas.randomuser.ui.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.eugenejavinas.randomuser.R
import com.eugenejavinas.randomuser.data.model.User
import com.eugenejavinas.randomuser.databinding.ListItemUserBinding

class UserListAdapter(
    private val onItemClickListener: ((User) -> Unit)
): RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    private val userList: MutableList<User> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemUserBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(userList[position], onItemClickListener)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(users: List<User>) {
        userList.clear()
        userList.addAll(users)
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val binding: ListItemUserBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bindItem(user: User, onClickListener: ((User) -> Unit)) {
            Glide.with(binding.root.context)
                .load(user.picture.thumbnail)
                .apply(RequestOptions()
                    .circleCrop()
                    .placeholder(R.drawable.picture_placeholder)
                    .error(R.drawable.picture_error))
                .into(binding.userImage)
            binding.user = user
            binding.setOnClickListener { onClickListener.invoke(user) }
            binding.executePendingBindings()
        }
    }
}