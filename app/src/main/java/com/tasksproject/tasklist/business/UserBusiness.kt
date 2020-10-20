package com.tasksproject.tasklist.business

import android.content.Context
import com.tasksproject.tasklist.R
import com.tasksproject.tasklist.constants.TaskConstants
import com.tasksproject.tasklist.entities.UserEntity
import com.tasksproject.tasklist.repository.UserRepository
import com.tasksproject.tasklist.util.SecurityPreferences
import com.tasksproject.tasklist.util.exception.ValidationException



class UserBusiness(val context: Context) {

    private val mUserRepository: UserRepository = UserRepository.getInstance(context)
    private val mSecurityPreferences: SecurityPreferences = SecurityPreferences(context)

    /**
     * Responsável por verificar se usuário existe e se os dados estão corretos
     * Caso exista, salva dos dados no SharedPreferences para uso posterior
     * */
    fun login(email: String, password: String): Boolean {

        val user: UserEntity? = mUserRepository.get(email, password)
        return if (user != null) {

            // Salva os dados no SharedPreferences
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_ID, user.id.toString())
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_NAME, user.name)
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_EMAIL, user.email)

            // Retorna usuário válido
            true

        } else {
            false
        }

    }

    /**
     * Salva o usuário no banco de dados e verifica se já existe
     * Caso já exista, lança um erro de email já existente
     * */
    @Throws(ValidationException::class)
    fun save(name: String, email: String, password: String, cpassword: String) = try {
        //Caso os campos estejam vazios,ele pede pra ser preenchido
        if (name == "" || email == "" || password == "") {
            throw ValidationException(context.getString(R.string.preencha_todos_campos))
        }

        //Confirma a senha
        if (password != cpassword){
            throw ValidationException(context.getString(R.string.Senhas_nao_correspondem))
        }

        //Verificar Email
        if ("@" !in email){
            throw ValidationException(context.getString(R.string.email_nao_e_valido))
        }

        // Verifica se email já existe no banco de dados
        if (mUserRepository.isEmailExistent(email)) {
            throw ValidationException(context.getString(R.string.email_ja_em_uso))
        }

        // Salva novo usuário, retornando o ID inserido
        val userId = mUserRepository.insert(name, email, password)

        // Salva os dados no SharedPreferences
        mSecurityPreferences.storeString(TaskConstants.KEY.USER_ID, userId.toString())
        mSecurityPreferences.storeString(TaskConstants.KEY.USER_NAME, name)
        mSecurityPreferences.storeString(TaskConstants.KEY.USER_EMAIL, email)
    } catch (e: Exception) {
        throw e
    }

}