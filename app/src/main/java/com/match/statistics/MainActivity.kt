package com.match.statistics

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.match.statistics.databinding.ActivityMainBinding
import com.match.statistics.util.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * 테스트용 롤 계정 sharedPreference에 저장
         */
        val pref = this.getSharedPreferences(Constants.SHARED_PREFERENCE_USER_INFO, Context.MODE_PRIVATE)
        pref.edit().run {
            putString(Constants.SHARED_PREFERENCE_USER_ID, "genetory")
            commit()
        }
    }
}