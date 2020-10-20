package com.tasksproject.tasklist.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.tasksproject.tasklist.R
import kotlinx.android.synthetic.main.activity_login.*
import com.tasksproject.tasklist.business.UserBusiness
import android.content.Intent
import android.widget.Toast
import com.tasksproject.tasklist.constants.TaskConstants
import com.tasksproject.tasklist.util.SecurityPreferences


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mUserBusiness: UserBusiness

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicializa variáveis
        mUserBusiness = UserBusiness(this)

        // Adicona eventos aos elementos
        setListeners()

        // Verifica se usuário está logado
        verifyLoggedUser()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.buttonLogin -> {
                handleLogin()
            }
            R.id.textRegister -> {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        }
    }

    /**
     * Tenta fazer o login do usuário
     */
    private fun handleLogin() {
        val email = this.editEmail.text.toString()
        val password = this.editPassword.text.toString()

        if (mUserBusiness.login(email, password)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, getString(R.string.usuario_senha_invalidos), Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Verifica se usuário está logado
     */
    private fun verifyLoggedUser() {
        val securityPreferences = SecurityPreferences(this)
        val userId = securityPreferences.getStoredString(TaskConstants.KEY.USER_ID)
        val userEmail = securityPreferences.getStoredString(TaskConstants.KEY.USER_EMAIL)

        if ("" != userId && "" != userEmail) {
            startActivity(Intent(this, MainActivity::class.java))

            // Impede que o usuário volte a essa tela
            finish()
        }
    }

    /**
     * Adicona eventos aos elementos
     * */
    private fun setListeners() {
        textRegister.setOnClickListener(this)
        buttonLogin.setOnClickListener(this)
    }

}
