package com.example.meal.Client

import com.example.meal.data.Meal
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface API {

    @GET("hub/mealServiceDietInfo")
    fun ApiService(
       @Query("MLSV_YMD") day : String,
       @Query("MMEAL_SC_CODE") sc :Int,
       @Query("KEY") key : String = "bde3d1d967c544409a90eab8b28d4aec",
       @Query("ATPT_OFCDC_SC_CODE") region : String = "F10",
       @Query("SD_SCHUL_CODE") school : Int = 7380292,
       @Query("Type") type : String = "json",
       @Query("pIndex") index : Int = 1,
       @Query("pSize") size : Int = 100

    ): Call<Meal>


}