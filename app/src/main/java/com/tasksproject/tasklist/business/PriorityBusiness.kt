package com.tasksproject.tasklist.business

import android.content.Context
import com.tasksproject.tasklist.entities.PriorityEntity
import com.tasksproject.tasklist.repository.PriorityRepository

class PriorityBusiness (context: Context) {

    private val mPriorityRepository: PriorityRepository = PriorityRepository.getInstance(context)

    /**
     * Retorna lista de prioridades
     * */
    fun getList(): MutableList<PriorityEntity> = mPriorityRepository.getList()

}