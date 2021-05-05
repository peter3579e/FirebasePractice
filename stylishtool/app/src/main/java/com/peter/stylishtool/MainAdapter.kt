package com.peter.stylishtool

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.peter.stylishtool.databinding.ItemCartBinding

class MainAdapter(val viewModel: MainViewModel):
    ListAdapter<Product, MainAdapter.MainViewHolder>(CartDiffCallback()){

    class MainViewHolder private constructor(val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.item = product
        }

        companion object {
            fun from(parent: ViewGroup): MainViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCartBinding.inflate(layoutInflater, parent, false)
                return MainViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(getItem(position))

        holder.itemView.setOnClickListener{
            val positionId= getItem(position) as Product
            viewModel.id.value = positionId.id.toString()
            Log.d("position","${positionId.id}")
            Log.d("position","id value in ViewModel = ${viewModel.id.value}")

            notifyDataSetChanged()

            return@setOnClickListener

        }

    }
}


class CartDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }

}