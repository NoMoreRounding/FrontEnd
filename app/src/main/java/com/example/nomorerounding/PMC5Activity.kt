package com.example.nomorerounding

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.nomorerounding.User.server
import com.example.nomorerounding.databinding.Pmc5Binding
import com.example.nomorerounding.model.LotResponseDTO
import com.example.nomorerounding.model.UserResponseDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PMC5Activity : AppCompatActivity() {
    private var binding: Pmc5Binding? = null
    private var user: UserResponseDTO? = null
    private var parkLocation: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = Pmc5Binding.inflate(layoutInflater)  // 뷰바인딩
        val view: View = binding!!.root
        setContentView(view)

        Toast.makeText(this, "로그인에 성공했습니다!", Toast.LENGTH_LONG).show()

        val intent: Intent = intent
        user = intent.getParcelableExtra("user") // 로그인 유저 정보 받아오기

        if (user != null) {
            setUserDock()
            setParkingLot() // search 후 색깔 칠하는것까지 다 해주는거
         // 유저정보 있으면 출력
        }






    }

    private fun setUserDock() {
        binding?.userInfo?.text = user?.name.plus(" ".plus(user?.carResponse?.carNumber))

        if(user?.carResponse?.compactCar == true) {
            binding?.iconCompactCar?.setImageResource(R.drawable.ic_round_eco_25)
        }
        if(user?.carResponse?.electric == true) {
            binding?.iconElectric?.setImageResource(R.drawable.ic_round_electric_car_25)
        }
        if(user?.carResponse?.disabled == true) {
            binding?.iconDisabled?.setImageResource(R.drawable.ic_round_accessible_25)
        }
        if(user?.carResponse?.pregnant == true) {
            binding?.iconPregnant?.setImageResource(R.drawable.ic_round_pregnant_woman_25)
        }

        binding?.userParkingTime?.text = "-- : --"
        binding?.userParkingLocation?.text = "--"
    }

    private fun setParkingLot() {
        val callGetSearch = server.getParkingLot("1", PMC2Activity.prefs.token!!)
        callGetSearch.enqueue(object : Callback<LotResponseDTO> {
            override fun onResponse(
                call: Call<LotResponseDTO>,
                response: Response<LotResponseDTO>
            ) {
                if (response.isSuccessful) { // 성공하면
                    var map : LotResponseDTO? = response.body()

                    checkEmptySpace(map) // 여기서 map 분석해서 색깔 잘 칠해서 띄워주는 함수 실행

                } else {
                    when (response.code()) { // 미완벽 구현
                        400 -> onFailure(call, Throwable())
                        404 -> onFailure(call, Throwable())
                        500 -> onFailure(call, Throwable())
                    }
                }
            }

            override fun onFailure(call: Call<LotResponseDTO>, t: Throwable) {
                Log.d("lol", call.toString())
                Log.d("lol", t.toString())
            }
        })
    }


    private fun checkEmptySpace(map : LotResponseDTO?) {

        var relativeArray : ArrayList<RelativeLayout> = ArrayList()

        relativeArray.add(binding!!.a01)
        relativeArray.add(binding!!.a02)
        relativeArray.add(binding!!.a03)
        relativeArray.add(binding!!.a04)
        relativeArray.add(binding!!.a05)
        relativeArray.add(binding!!.a06)
        relativeArray.add(binding!!.b01)
        relativeArray.add(binding!!.b02)
        relativeArray.add(binding!!.b03)
        relativeArray.add(binding!!.b04)
        relativeArray.add(binding!!.b05)
        relativeArray.add(binding!!.b06)

        var viewArray : ArrayList<ImageView> = ArrayList()
        viewArray.add(binding!!.a01view)
        viewArray.add(binding!!.a02view)
        viewArray.add(binding!!.a03view)
        viewArray.add(binding!!.a04view)
        viewArray.add(binding!!.a05view)
        viewArray.add(binding!!.a06view)
        viewArray.add(binding!!.b01view)
        viewArray.add(binding!!.b02view)
        viewArray.add(binding!!.b03view)
        viewArray.add(binding!!.b04view)
        viewArray.add(binding!!.b05view)
        viewArray.add(binding!!.b06view)

        for (i in map?.spaceResponses!!){
            //Log.d("lol", i.toString())
                if (i.userId != user?.id) {
                    relativeArray[i.spaceColumn!!.times(6) + i.spaceRow!!].background =
                        ContextCompat.getDrawable(this, R.drawable.x);

                }
                else{ // 주차된 차량이랑 내 usdid가 같은 경우 == 내 차 주차됨
                    relativeArray[i.spaceColumn!!.times(6) + i.spaceRow!!].setBackgroundColor(Color.parseColor("#FEBE49"))

                    if(i.spaceColumn == 0){
                        parkLocation = "A-0"
                    }else{
                        parkLocation = "B-0"
                    }

                    binding?.userParkingLocation?.text = parkLocation.plus(i.spaceRow.toString())

                }
        }

        for (i in map?.specificSpaceResponses!!){
            Log.d("lol", i.spaceType.toString() + i.spaceColumn!!.times(6)+ (i.spaceRow)!!.toInt())
            when(i.spaceType.toString()){
                "PREGNANT" -> {
                    viewArray[i.spaceColumn!!.times(6)+ (i.spaceRow)!!.toInt()].background =
                        ContextCompat.getDrawable(this, R.drawable.ic_round_pregnant_woman_25);
                    if(user?.carResponse?.pregnant == false){
                    //여기서 마크 지워주고, x도 칠해줘야함
                    //viewArray[i.spaceColumn!!.times(6)+ (i.spaceRow)!!.toInt()].visibility = View.INVISIBLE
                    relativeArray[i.spaceColumn!!.times(6)+ (i.spaceRow)!!.toInt()].background =
                        ContextCompat.getDrawable(this, R.drawable.x);
                    }
                }
                "COMPACT" -> {
                    //Log.d("lol", (i.spaceColumn!!.times(6)+ i.spaceRow!!).toString())

                    viewArray[i.spaceColumn!!.times(6)+ (i.spaceRow)!!.toInt()].background =
                        ContextCompat.getDrawable(this, R.drawable.ic_round_eco_25);
                    if(user?.carResponse?.compactCar == false){
                        //여기서 마크 지워주고, x도 칠해줘야함
                        //viewArray[i.spaceColumn!!.times(6)+ (i.spaceRow)!!.toInt()].visibility = View.INVISIBLE
                        relativeArray[i.spaceColumn!!.times(6)+ (i.spaceRow)!!.toInt()].background =
                            ContextCompat.getDrawable(this, R.drawable.x);
                    }
                }
                "ELECTRIC" -> {
                    viewArray[i.spaceColumn!!.times(6)+ (i.spaceRow)!!.toInt()].background =
                        ContextCompat.getDrawable(this, R.drawable.ic_round_electric_car_25);
                    if(user?.carResponse?.electric == false){
                    //여기서 마크 지워주고, x도 칠해줘야함
                    //viewArray[i.spaceColumn!!.times(6)+ (i.spaceRow)!!.toInt()].visibility = View.INVISIBLE
                    relativeArray[i.spaceColumn!!.times(6)+ (i.spaceRow)!!.toInt()].background =
                        ContextCompat.getDrawable(this, R.drawable.x);
                    }

                }
                "DISABLED" -> {
                    viewArray[i.spaceColumn!!.times(6)+ i.spaceRow!!].background =
                        ContextCompat.getDrawable(this, R.drawable.ic_round_accessible_25);
                    if(user?.carResponse?.disabled == false){
                    //여기서 마크 지워주고, x도 칠해줘야함
                    //viewArray[i.spaceColumn!!.times(6)+ i.spaceRow!!].visibility = View.INVISIBLE
                    relativeArray[i.spaceColumn!!.times(6) + i.spaceRow!!].background =
                        ContextCompat.getDrawable(this, R.drawable.x);
                }}
            }
        }
    }
}