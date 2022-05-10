package com.match.statistics.ui.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
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
    val summonerNameState = MutableStateFlow<Resource<String>>(Resource.Loading)
    var curSummonerName:String = ""

    init {
        getUserId()
    }

    /**
     * 롤 계정 가져옴
     */
    fun getUserId() {
        viewModelScope.launch {
            summonerNameState.value = userInfoUseCase.getLolSummonerName()
        }
    }

    /**
     * 롤 프로필 조회
     */
    fun getUserProfile(userId:String) {
        summonerProfileState.value = Resource.Loading
        viewModelScope.launch {
            summonerProfileState.value = userInfoUseCase.getLoLSummonerInfo(userId)
        }
    }

    /**
     * 매치 조회
     */
    fun getMatches(userId:String, createDate:String? = null) = userInfoUseCase.getLoLMatches(userId, createDate).cachedIn(viewModelScope)

}