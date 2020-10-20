package com.tasksproject.tasklist.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.tasksproject.tasklist.viewholder.TaskViewHolder
import com.tasksproject.tasklist.entities.TaskEntity
import com.tasksproject.tasklist.entities.listener.OnTaskListFragmentInteractionListener
import android.view.LayoutInflater
import com.tasksproject.tasklist.R

class TaskListAdapter (taskList: List<TaskEntity>, listener: OnTaskListFragmentInteractionListener) : RecyclerView.Adapter<TaskViewHolder>() {

    private val mListTaskEntities: List<TaskEntity> = taskList
    private val listener: OnTaskListFragmentInteractionListener = listener

    override fun getItemCount(): Int {
        if (!mListTaskEntities.equals(true)) {
            return mListTaskEntities.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        // Obtém item da lista
        val task: TaskEntity = mListTaskEntities[position]
        holder.bindData(task, listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val context = parent.context

        // Infla o layout da linha e faz uso na listagem
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.row_task_list, parent, false)

        return TaskViewHolder(view, context)
    }
}