package com.example.nomorerounding

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.nomorerounding.databinding.Pmc2Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.nomorerounding.User.server
import com.example.nomorerounding.model.SignInRequestDTO
import com.example.nomorerounding.model.UserResponseDTO

class PMC2Activity : AppCompatActivity() {
    private var _binding: Pmc2Binding? = null
    private val binding get() = _binding!!
    companion object {
        lateinit var prefs: Prefs
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        prefs=Prefs(applicationContext)
        super.onCreate(savedInstanceState)

        _binding = Pmc2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val ID: EditText = findViewById(R.id.textInputEditText_id)
        val PASSWORD: EditText = findViewById(R.id.textInputEditText_password)

        val intentPMC3 = Intent(this, PMC3Activity::class.java)
        val intentPMC5 = Intent(this, PMC5Activity::class.java)


        binding.buttonSignin.setOnClickListener {
            val loginId = ID.text.toString()
            val password = PASSWORD.text.toString()

            if(TextUtils.isEmpty(loginId) and TextUtils.isEmpty(password)){
                binding.loginFail.setText("아이디와 비밀번호를 입력해주세요.")
                binding.loginFail.visibility = View.VISIBLE
            } else if(TextUtils.isEmpty(loginId)){
                binding.loginFail.setText("아이디를 입력해주세요")
                binding.loginFail.visibility = View.VISIBLE
            } else if(TextUtils.isEmpty(password)){
                binding.loginFail.setText("비밀번호를 입력해주세요")
                binding.loginFail.visibility = View.VISIBLE
            }else{
            server.requestLogin(SignInRequestDTO(loginId, password)).enqueue(object : Callback<UserResponseDTO> {
                override fun onResponse(
                    call: Call<UserResponseDTO>,
                    response: Response<UserResponseDTO>
                ) {
                    if (response.isSuccessful) {
                        val user: UserResponseDTO? = response.body()
                        intentPMC5.putExtra("user", user)
                        prefs.token = user?.tokenResponse?.accessToken
                        startActivity(intentPMC5)
                        finish()
                    } else {
                        when (response.code()) {
                            400 -> onFailure(call, Throwable())
                            404 -> onFailure(call, Throwable())
                            500 -> onFailure(call, Throwable())
                        }
                    }
                }

                override fun onFailure(call: Call<UserResponseDTO>, t: Throwable) {
                    binding.loginFail.setText("아이디 또는 비밀번호가 일치하지 않습니다.")
                    binding.loginFail.visibility = View.VISIBLE
                }
            })
            }
        }

        binding.textViewFind.setOnClickListener {
            startActivity(intentPMC3)
        }
    }
}
