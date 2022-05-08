package com.match.statistics.ui.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.match.statistics.domain.model.lol.Match
import com.match.statistics.domain.model.lol.SummonerInfo
import com.match.statistics.domain.usecase.UserInfoUseCase
import com.match.statistics.util.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val userInfoUseCase: UserInfoUseCase
):ViewModel() {

    val summonerProfileState = MutableStateFlow<Resource<SummonerInfo>>(Resource.Loading)
    val summonerMatchesState = MutableStateFlow<Resource<List<Match>>>(Resource.Loading)
    val summonerNameState = MutableStateFlow<Resource<String>>(Resource.Loading)
    var curSummonerName:String = ""

    init {
        getUserId()
    }

    fun getUserId() {
        viewModelScope.launch {
            summonerNameState.value = userInfoUseCase.getLolSummonerName()
        }
    }

    fun getUserProfile(userId:String) {
        viewModelScope.launch {
            summonerProfileState.value = userInfoUseCase.getLoLSummonerInfo(userId)
        }
    }

    fun getMatches(userId:String, createDate:String? = null) {
        viewModelScope.launch {
            summonerMatchesState.value = userInfoUseCase.getLoLMatches(userId, createDate)
        }
    }

}