package com.example.nomorerounding

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.nomorerounding.model.StringResponseDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialog
import com.example.nomorerounding.model.ResetRequestDTO
import kotlinx.android.synthetic.main.pmc3.*


class PMC4Activity : AppCompatActivity() {

    private lateinit var progressDialog: AppCompatDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pmc4)

        val emailText = findViewById<TextView>(R.id.textInputEditText_Email1)

        val emailBtn = findViewById<Button>(R.id.button_singin2)

        emailBtn.setOnClickListener {
            findLoginId(emailText.text.toString())
        }

        val resetIdTextView = findViewById<TextView>(R.id.textInputEditText_id)
        val resetEmailTextView = findViewById<TextView>(R.id.textInputEditText_Email12)
        val resetPasswordButton = findViewById<Button>(R.id.button_singin3)

        resetPasswordButton.setOnClickListener {
            progressON()
            resetPassword(ResetRequestDTO(resetEmailTextView.text.toString(), resetIdTextView.text.toString()))
        }
    }

    private fun findLoginId(emailText : String) {
        val callGetSearch = User.server.findLoginId(emailText)
        callGetSearch.enqueue(object : Callback<StringResponseDTO> {
            override fun onResponse(
                call: Call<StringResponseDTO>,
                response: Response<StringResponseDTO>
            ) {
                if (response.isSuccessful) { // 성공하면
                    var loginId : String? = response.body()?.message

                    val dlg: AlertDialog.Builder = AlertDialog.Builder(this@PMC4Activity)
                    dlg.setTitle("로그인 아이디") //제목
                    dlg.setMessage("로그인 아이디는 " + loginId + " 입니다.") // 메시지
                    dlg.setPositiveButton("확인", null)
                    dlg.show()

                } else {
                    when (response.code()) { // 미완벽 구현
                        400 -> onFailure(call, Throwable())
                        404 -> onFailure(call, Throwable())
                        500 -> onFailure(call, Throwable())
                    }
                }
            }

            override fun onFailure(call: Call<StringResponseDTO>, t: Throwable) {
                Log.d("lol", call.toString())
                Log.d("lol", t.toString())
            }
        })
    }

    private fun resetPassword(msg: ResetRequestDTO) {
        val callGetSearch = User.server.resetPassword(msg)
        callGetSearch.enqueue(object : Callback<ResetRequestDTO> {
            override fun onResponse(
                call: Call<ResetRequestDTO>,
                response: Response<ResetRequestDTO>
            ) {
                if (response.isSuccessful) { // 성공하면

                    progressOFF()

                    val dlg: AlertDialog.Builder = AlertDialog.Builder(this@PMC4Activity)
                    dlg.setTitle("비밀번호 재설정") //제목
                    dlg.setMessage("임시 비밀번호가 포함된 메일이 발송되었습니다.") // 메시지
                    dlg.setPositiveButton("확인", null)
                    dlg.show()

                } else {
                    when (response.code()) { // 미완벽 구현
                        400 -> onFailure(call, Throwable())
                        404 -> onFailure(call, Throwable())
                        500 -> onFailure(call, Throwable())
                    }
                }
            }

            override fun onFailure(call: Call<ResetRequestDTO>, t: Throwable) {
                Log.d("lol", call.toString())
                Log.d("lol", t.toString())
            }
        })
    }

    fun progressON(){
        progressDialog = AppCompatDialog(this@PMC4Activity)
        progressDialog.setCancelable(false)
        progressDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        progressDialog.setContentView(R.layout.progress_loading)
        progressDialog.show()
        var img_loading_framge = progressDialog.findViewById<ImageView>(R.id.iv_frame_loading)
        var frameAnimation = img_loading_framge?.getBackground() as AnimationDrawable
        img_loading_framge?.post(object : Runnable{
            override fun run() {
                frameAnimation.start()
            }

        })
    }
    fun progressOFF(){
        if(progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss()
        }
    }
}