package com.example.nomorerounding

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.example.nomorerounding.User.server
import com.example.nomorerounding.databinding.Pmc4Binding
import retrofit2.*
import com.example.nomorerounding.model.UserResponseDTO
import com.example.nomorerounding.model.SignUpRequestDTO
import java.util.*
import java.util.regex.Pattern

class PMC4Activity : AppCompatActivity() {
    private var binding: Pmc4Binding? = null
    private var sex: String = "none"
    private var electric: Boolean = false
    private var compact: Boolean = false
    private var pregnant: Boolean = false
    private var disabled: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = Pmc4Binding.inflate(layoutInflater) // 뷰바인딩
        val view: View = binding!!.root
        setContentView(view)

        binding!!.radioGroup.setOnCheckedChangeListener { group, checkId -> // 성별 리스너
            when (checkId) {
                R.id.rg_btn1 -> sex = "MALE"
                R.id.rg_btn2 -> sex = "FEMALE"
            }
        }

        binding!!.birthEdittext.setOnClickListener { // 생년월일 리스너
            var calendar = Calendar.getInstance()
            var year = calendar.get(Calendar.YEAR)
            var month = calendar.get(Calendar.MONTH)
            var day = calendar.get(Calendar.DAY_OF_MONTH)

            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, day ->
                binding!!.birthEdittext.setText("%d-%02d-%02d".format(year, month + 1, day))
            }
            DatePickerDialog(this, dateSetListener, year, month, day).show()
        }

        binding!!.electricSwitch.setOnCheckedChangeListener { buttonView, isChecked -> // 전기차 스위치 리스너
            electric = isChecked
        }

        binding!!.compactSwitch.setOnCheckedChangeListener { buttonView, isChecked ->  // 경차 스위치 리스너
            compact = isChecked
        }

        binding!!.pregnantSwitch.setOnCheckedChangeListener { buttonView, isChecked -> // 임산부 스위치 리스너
            pregnant = isChecked
        }

        binding!!.disabledSwitch.setOnCheckedChangeListener { buttonView, isChecked -> // 장애인 스위치 리스너
            disabled = isChecked
        }

        binding!!.btnSignup.setOnClickListener { // 가입하기 눌렀을 때 로컬검증 -> 원격검증 -> 페이지 넘기기
            checkValidation()
        }
    }

    private fun checkValidation() {
        var userId = binding?.idEdittext?.text.toString()
        var userPwd = binding?.pwdEdittext?.text.toString()
        var userRePwd = binding?.repwdEdittext?.text.toString()
        var userName = binding?.nameEdittext?.text.toString()
        var userBirth = binding?.birthEdittext?.text.toString()
        var userEmail = binding?.emailEdittext?.text.toString()
        var userCarNum = binding?.carnumEdittext?.text.toString()

        var FailSignal = false

        if (TextUtils.isEmpty(userId)) { // id 비었을 때
            FailSignal = true
            binding?.idFail?.text = "아이디를 입력해주세요"
            binding?.idFail?.visibility = View.VISIBLE
        } else if (!Pattern.matches("(?=.*[0-9])(?=.*[a-zA-Z]).{3,15}", userId)) { // 정규표현식
            FailSignal = true
            binding?.idFail?.text = "아이디를 형식에 맞게 입력해주세요"
            binding?.idFail?.visibility = View.VISIBLE
        } else {
            binding?.idFail?.visibility = View.INVISIBLE
        }

        if (TextUtils.isEmpty(userPwd)) { // pwd 비었을 때
            FailSignal = true
            binding?.pwdFail?.text = "비밀번호를 입력해주세요"
            binding?.pwdFail?.visibility = View.VISIBLE
            binding?.repwdFail?.text = "비밀번호를 입력해주세요"
            binding?.repwdFail?.visibility = View.VISIBLE
        } else if (!TextUtils.equals(userPwd, userRePwd)) { // pwd - repwd 같지 않을 때
            FailSignal = true
            binding?.pwdFail?.text = "비밀번호가 서로 일치하지 않습니다"
            binding?.pwdFail?.visibility = View.VISIBLE
            binding?.repwdFail?.text = "비밀번호가 서로 일치하지 않습니다"
            binding?.repwdFail?.visibility = View.VISIBLE
        } else if (!Pattern.matches(
                "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
                userPwd
            )
        ) { // 정규표현식
            FailSignal = true
            binding?.pwdFail?.text = "비밀번호를 형식에 맞게 입력해주세요"
            binding?.pwdFail?.visibility = View.VISIBLE
            binding?.repwdFail?.text = "비밀번호를 형식에 맞게 입력해주세요"
            binding?.repwdFail?.visibility = View.VISIBLE
        } else {
            binding?.pwdFail?.visibility = View.INVISIBLE
            binding?.repwdFail?.visibility = View.INVISIBLE
        }

        if (TextUtils.isEmpty(userName)) { // 이름
            FailSignal = true
            binding?.nameFail?.text = "이름을 입력해주세요"
            binding?.nameFail?.visibility = View.VISIBLE
        } else {
            binding?.nameFail?.visibility = View.INVISIBLE
        }

        if (TextUtils.equals(userBirth, "클릭하여 생년월일 입력")) { // 생년월일
            FailSignal = true
            binding?.birthFail?.text = "생년월일을 입력해주세요"
            binding?.birthFail?.visibility = View.VISIBLE
        } else {
            binding?.birthFail?.visibility = View.INVISIBLE
        }

        if (TextUtils.equals(sex, "none")) { // 성별
            FailSignal = true
            binding?.sexFail?.text = "성별을 선택해주세요"
            binding?.sexFail?.visibility = View.VISIBLE
        } else {
            binding?.sexFail?.visibility = View.INVISIBLE
        }

        if (TextUtils.isEmpty(userEmail)) { // 이메일 비었을 때
            FailSignal = true
            binding?.emailFail?.text = "이메일을 입력해주세요"
            binding?.emailFail?.visibility = View.VISIBLE
        } else if (!Pattern.matches(
                "^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$",
                userEmail
            )
        ) { // 정규표현식
            FailSignal = true
            binding?.emailFail?.text = "이메일를 형식에 맞게 입력해주세요"
            binding?.emailFail?.visibility = View.VISIBLE
        } else {
            binding?.emailFail?.visibility = View.INVISIBLE
        }

        if (TextUtils.isEmpty(userCarNum)) { // 차량번호 비었을 때
            FailSignal = true
            binding?.carnumFail?.text = "차량번호를 입력해주세요"
            binding?.carnumFail?.visibility = View.VISIBLE
        } else if (!Pattern.matches("^\\d{2,3}[가-힣]{1}\\d{4}\$", userCarNum)) { // 정규표현식
            // "/￦d{2,3}[가-힣]￦d{4}"
            FailSignal = true
            binding?.carnumFail?.text = "차량번호를 형식에 맞게 입력해주세요"
            binding?.carnumFail?.visibility = View.VISIBLE
        } else {
            binding?.carnumFail?.visibility = View.INVISIBLE
        }

        if (!FailSignal) { // 프론트 검증 완료되면 서버 전송 & 검증
            var msg = SignUpRequestDTO(
                userBirth,
                userCarNum,
                compact,
                disabled,
                electric,
                userEmail,
                sex,
                userId,
                userName,
                userPwd,
                pregnant
            )
            signUpPost(msg) // 서버 전송
        }
    } // 재입력 요구

    private fun signUpPost(msg: SignUpRequestDTO) { // 서버 요청
        val callPostSignup = server.postSignUp(msg)

        callPostSignup.enqueue(object : Callback<UserResponseDTO> {
            override fun onResponse(
                call: Call<UserResponseDTO>,
                response: Response<UserResponseDTO>
            ) {
                if (response.isSuccessful) { // 성공하면 페이지 넘기기
                    moveLoginPage()
                } else {
                    when (response.code()) { // 미완벽 구현
                        400 -> onFailure(call, Throwable())
                        404 -> onFailure(call, Throwable())
                        500 -> onFailure(call, Throwable())
                    }
                }
            }
            override fun onFailure(call: Call<UserResponseDTO>, t: Throwable) {
                binding?.signupFail?.text = "동일한 정보의 계정이 존재합니다!"
                binding?.signupFail?.visibility = View.VISIBLE
            }
        })
    }

    private fun moveLoginPage() { // 회원가입 성공, 로그인, 액티비티 종료
        Toast.makeText(this, "회원가입에 성공했습니다!", Toast.LENGTH_LONG).show()
        startActivity(Intent(this, PMC2Activity::class.java))
        finish()
    }

    override fun onBackPressed() { // 뒤로가기 눌렀을 때, 액티비티 종료
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}