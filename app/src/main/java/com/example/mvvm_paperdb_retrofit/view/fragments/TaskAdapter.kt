package com.example.mvvm_paperdb_retrofit.view.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_paperdb_retrofit.databinding.FragmentTaskItemBinding
import com.example.mvvm_paperdb_retrofit.model.tasks.TaskModel


class TaskAdapter(
    private val values: List<TaskModel>
) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentTaskItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        with(holder){
            idView.text = item.id.toString()
            nameView.text = item.name
            descriptionView.text = item.description
            timeView.text = item.timeCreated
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentTaskItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val nameView: TextView = binding.content
        val descriptionView: TextView = binding.description
        val timeView:TextView = binding.time
    }

}