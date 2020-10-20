package com.tasksproject.tasklist.entities

data class UserEntity (val id: Int, var email: String, var name: String, var password: String = "", var cpassword: String  = "")