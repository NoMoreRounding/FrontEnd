package com.example.nomorerounding

import com.example.nomorerounding.model.*
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

    @GET("/api/user/findId/{email}")
    fun findLoginId(@Path("email") email: String): Call<StringResponseDTO>

    @POST("/api/user/reset")
    fun resetPassword(@Body resetRequest : ResetRequestDTO): Call<ResetRequestDTO>
}

