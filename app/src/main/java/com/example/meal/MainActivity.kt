package com.example.meal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.meal.Client.Builder
import com.example.meal.data.Meal
import com.example.meal.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.nio.BufferOverflowException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    val now: Long = System.currentTimeMillis() // 현재시간을 msec 으로 구한다.
    val date = Date(now) // 현재시간을 Date에 저장한다
    val dateFormat = SimpleDateFormat("yyyyMMdd", Locale("ko", "KR")).format(date) //출력 예시(20210324)
    val dateFormat2 = SimpleDateFormat("yyyy년 MM월 dd일 \nEE요일", Locale("ko", "KR")).format(date)// 출력 예시(2021년3월24일 수요일)
    val stringTime = dateFormat2.format(date)
    var time: Int = 0 //식사코드 1 = 아침, 2 = 점심 , 3 = 저녘
    val mbinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    val TAG: String = "로그"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mbinding.root)
        mbinding.mCalender.text = stringTime
        mbinding.breakFast.setOnClickListener {
            time = 1
            Build()
        }
        mbinding.lunch.setOnClickListener {
            time = 2
            Build()
        }
        mbinding.dinner.setOnClickListener {
            time = 3
            Build()
        }


    }

    fun Build() {
        Builder.api.ApiService("${dateFormat}", time).enqueue(object : Callback<Meal> {
            override fun onResponse(call: Call<Meal>, response: Response<Meal>) {
                val res = response.body()!!.mealServiceDietInfo.get(1).row
                if (res != null) {
                    for (i in 0 until res.size) {
                        val obj = res.get(i)
                        val row = obj.DDISH_NM.replace("<br/>", "\n").replace("/", "")

                        when (time) {
                            1 -> {
                                mbinding.mBreak.text = row
                                Log.d(TAG, "onResponse: ${row}")
                            }
                            2 -> {
                                mbinding.mLunch.text = row
                            }
                            3 -> {
                                mbinding.mDinner.text = row
                            }
                        }

                    }
                }
            }

            override fun onFailure(call: Call<Meal>, t: Throwable) {
                Log.d(TAG, "MainActivity - onFailure() - ${t} ")
            }

        })
    }


}