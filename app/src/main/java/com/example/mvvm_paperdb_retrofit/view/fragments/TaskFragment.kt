package com.example.mvvm_paperdb_retrofit.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm_paperdb_retrofit.databinding.FragmentTaskListBinding
import com.example.mvvm_paperdb_retrofit.viewModel.TaskViewModel
import java.util.Calendar


class TaskFragment : Fragment() {

    private lateinit var binding:FragmentTaskListBinding
    private lateinit var taskViewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskListBinding.inflate(layoutInflater, container, false)
        taskViewModel = ViewModelProvider(requireActivity())[TaskViewModel::class.java]

        taskViewModel.tasks.observe(viewLifecycleOwner){ listTasks ->
            if (!listTasks.isNullOrEmpty()){
                with(binding.list) {
                    layoutManager = LinearLayoutManager(context)
                    adapter = TaskAdapter(listTasks)
                }
            }
        }
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1 // Месяц начинается с 0, поэтому добавляем 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)
        val millisecond = calendar.get(Calendar.MILLISECOND)
        val dateTimeString = "$year-$month-$day $hour:$minute:$second.$millisecond"
        taskViewModel.deleteTask(2)

        return binding.root
    }
}