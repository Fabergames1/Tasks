package com.tasksproject.tasklist.business

import android.content.Context
import com.tasksproject.tasklist.R
import com.tasksproject.tasklist.constants.TaskConstants
import com.tasksproject.tasklist.entities.TaskEntity
import com.tasksproject.tasklist.repository.TaskRepository
import com.tasksproject.tasklist.util.SecurityPreferences
import com.tasksproject.tasklist.util.exception.ValidationException

class TaskBusiness(val context: Context) {

    private val mTaskRepository: TaskRepository = TaskRepository.getInstance(context)
    private val mSecurityPreferences: SecurityPreferences = SecurityPreferences(context)

    /**
     * Retorna entidade de Tarefa
     * */
    fun get(id: Int): TaskEntity? = mTaskRepository.get(id)

    /**
     * Retorna lista de tarefas
     * */
    fun getList(filter: Int): MutableList<TaskEntity> {

        // Obtém o Id do usuário
        val userId: Int = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_ID).toInt()

        // Faz a listagem de tarefas com filtros
        return mTaskRepository.getList(filter, userId)
    }

    /**
     * Faz a inserção da tarefa
     * */
    fun insert(task: TaskEntity) {

        try {
            // Faz a validação dos campos
            if (task.description == "" || task.dueDate == "" || task.priorityId == 0) {
                throw ValidationException(context.getString(R.string.preencha_todos_campos))
            }

            // Faz a inserção da tarefa
            mTaskRepository.insert(task)
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * Faz a atualização da tarefa
     * */
    fun update(task: TaskEntity) {

        try {
            // Faz a validação dos campos
            if (task.description == "" || task.dueDate == "" || task.priorityId == 0) {
                throw ValidationException(context.getString(R.string.preencha_todos_campos))
            }

            // Faz a atualização da tarefa
            mTaskRepository.update(task)
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * Faz a remoção da tarefa
     * */
    fun delete(id: Int) = mTaskRepository.delete(id)

    /**
     * Faz a atualização da tarefa como completa ou pendente
     * */
    fun complete(id: Int, complete: Boolean) = mTaskRepository.complete(id, complete)

}