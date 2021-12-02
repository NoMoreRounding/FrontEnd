package com.example.nomorerounding

import com.example.nomorerounding.model.LotResponseDTO
import com.example.nomorerounding.model.SignInRequestDTO
import com.example.nomorerounding.model.UserResponseDTO
import com.example.nomorerounding.model.SignUpRequestDTO
import retrofit2.Call
import retrofit2.http.*


interface API {
    @POST("api/user/login/")
    fun requestLogin(@Body loginRequest: SignInRequestDTO): Call<UserResponseDTO>

    @Headers("Content-Type: application/json")
    @POST("/api/user/signup")
    fun postSignUp(@Body JsonObject: SignUpRequestDTO): Call<UserResponseDTO>


    @GET("/api/parking/search/{floor}")
    fun getParkingLot(@Path("floor") floor: String , @Header("Authorization") auth: String): Call<LotResponseDTO>
}

