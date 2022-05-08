package com.match.statistics.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.match.statistics.domain.model.lol.League
import com.match.statistics.domain.model.lol.Summoner

data class SummonerResponse(
    @SerializedName("summoner")
    val summoner: SummonerModel
)

data class SummonerModel(
    @SerializedName("name")
    val name:String,
    @SerializedName("level")
    val level:Int,
    @SerializedName("profileImageUrl")
    val profileImageUrl:String,
    @SerializedName("leagues")
    val leagues: List<LeagueModel>
)

data class LeagueModel(
    @SerializedName("wins")
    val wins:Int,
    @SerializedName("losses")
    val losses:Int,
    @Expose
    var winRate:Int,
    @SerializedName("tierRank")
    val tierRank:TierRankModel
)

data class TierRankModel(
    @SerializedName("name")
    val name:String,
    @SerializedName("tier")
    val tier:String,
    @SerializedName("imageUrl")
    val imageUrl:String,
    @SerializedName("lp")
    val lp:Int
)

fun SummonerResponse.mapToDomain() = Summoner(
    name = this.summoner.name,
    profileImageUrl = this.summoner.profileImageUrl,
    level = this.summoner.level,
    leagues = this.summoner.leagues.map {
        League(
            wins = it.wins,
            losses = it.losses,
            winRate = it.winRate,
            rankType = it.tierRank.name,
            tier = it.tierRank.tier,
            tierImage = it.tierRank.imageUrl,
            lp = it.tierRank.lp
        )
    }
)