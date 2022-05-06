package com.match.statistics.ui.statistics

import android.util.Log
import androidx.lifecycle.ViewModel
import com.match.statistics.domain.model.League
import com.match.statistics.domain.model.Summoner
import com.match.statistics.domain.model.TierRank
import com.match.statistics.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(

):ViewModel() {

    val userProfileState = MutableStateFlow<Resource<Summoner>>(Resource.Loading)

    init {
        getUserProfile()
    }

    fun getUserProfile() {
        userProfileState.value = Resource.Success(Summoner(
            name = "genetory",
            level = 102,
            profileImageUrl = "https://opgg-static.akamaized.net/images/profile_icons/profileIcon1625.jpg",
            profileBorderImageUrl = "https://opgg-static.akamaized.net/images/borders2/challenger.png",
            url = "https://www.op.gg/summoner/userName=genetory",
            leagues = listOf(
                League(
                    wins = 169,
                    losses = 54,
                    winRate = ((169f / (169f + 54f)) * 100).toInt(),
                    tierRank = TierRank(
                        name = "솔랭",
                        tier = "Gold",
                        lp = 1698,
                        imageUrl = "https://opgg-static.akamaized.net/images/medals/gold_1.png"
                    )
                ),
                League(
                    wins = 793,
                    losses = 522,
                    winRate = (793f / (793f + 522f) * 100).toInt(),
                    tierRank = TierRank(
                        name = "자유 5:5 랭크",
                        tier = "Platinum",
                        lp = 901,
                        imageUrl = "https://opgg-static.akamaized.net/images/medals/platinum_1.png"
                    )
                )
            ),
            profileBackgroundImageUrl = "http://ddragon.leagueoflegends.com/cdn/img/champion/splash/Taliyah_0.jpg"
        ))

    }
}